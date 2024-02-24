package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.ReviewEntity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import hse.ru.vladch.entities.BillEntity
import java.io.File
import kotlin.io.path.Path

class InMemoryMenuItemDao : MenuItemDao {
    private var dishes = mutableListOf<DishEntity>()
    override fun addDish(name: String, price: Int, time: Long, amount : Int) {
        if (name.isEmpty()) {
            throw RuntimeException("The name cannot be empty!")
        }
        val dish = getDish(name)
        if (dish != null) {
            throw RuntimeException("The '$name' dish already exists!")
        }
        dishes.add(DishEntity(name, price, time, amount))
    }

    override fun deleteDish(name: String) {
        val dish = getDish(name) ?: throw RuntimeException("The '$name' dish does not exist!")
        dishes.remove(dish)
    }

    override fun setDishTime(name: String, time: Long) {
        val dish = getDish(name) ?: throw RuntimeException("The '$name' dish does not exist!")
        dish.timeRequirement = time
    }

    override fun setDishPrice(name: String, price: Int) {
        val dish = getDish(name) ?: throw RuntimeException("The '$name' dish does not exist!")
        dish.price = price
    }

    override fun setDishAmount(name: String, amount: Int) {
        val dish = getDish(name) ?: throw RuntimeException("The '$name' dish does not exist!")
        dish.amount = amount
    }

    override fun renameDish(oldName: String, newName: String) {
        val dish = getDish(oldName) ?: throw RuntimeException("The '$oldName' dish does not exist!")
        if (findDish(newName)) {
            throw RuntimeException("The dish with this new name already exists!")
        }
        dish.name = newName
    }

    override fun setDishProperties(name: String, price: Int, time: Long, amount : Int) {
        val dish = getDish(name) ?: throw RuntimeException("The '$name' dish does not exist!")
        dish.price = price
        dish.timeRequirement = time
        dish.amount = amount
    }

    override fun getDish(name: String): DishEntity? {
        return dishes.find { it.name == name }
    }

    override fun getDishById(id : Int): DishEntity? {
        try {
            return dishes[id]
        } catch (eString : Exception) {
            throw RuntimeException("Cannot find the dish in menu")
        }
    }

    override fun findDish(name: String): Boolean {
        return dishes.find { it.name == name } != null
    }

    override fun getAllDishesString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Menu list:\n")
        for (i in 0..<dishes.size) {
            val dish = dishes[i]
            stringBuilder.append("$i) Name: ${dish.name}, Price: ${dish.price}," +
                    " Time required: ${dish.timeRequirement}\n")
        }
        return stringBuilder.toString()
    }

    override fun getAllDishes(): MutableList<DishEntity> {
        val list = mutableListOf<DishEntity>()
        list.addAll(dishes)
        return list
    }

    override fun getDishReviews(name: String): String {
        val dish = getDish(name) ?: throw RuntimeException("The dish does not exist!")
        val dishReviews = dish.reviews
        val stringBuilder = StringBuilder()
        stringBuilder.append("Reviews:\n")
        for (i in 0..<dishReviews.size) {
            val review = dishReviews[i]
            stringBuilder.append("$i) Score: ${review.score}, Review: ${review.review}\n")
        }
        return stringBuilder.toString()
    }

    override fun addDishReview(dishName : String, reviewEntity: ReviewEntity) {
        for (item in dishes) {
            if (item.name == dishName) {
                item.reviews.add(reviewEntity)
            }
        }
    }

    override fun loadData() {
        val directory = "src/main/resources"
        val fileName = "dishes.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        dishes = objectMapper.readValue<MutableList<DishEntity>>(file.readText())
    }

    override fun saveData() {
        val directory = "src/main/resources"
        val fileName = "dishes.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        objectMapper.writeValue(file, dishes)
    }
}