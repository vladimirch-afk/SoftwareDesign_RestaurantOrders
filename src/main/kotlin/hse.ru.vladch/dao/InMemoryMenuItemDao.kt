package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity

class InMemoryMenuItemDao : MenuItemDao {
    private val dishes = mutableListOf<DishEntity>()
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

    override fun getAllDishesString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("Menu list:\n")
        for (i in 0..dishes.size) {
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
}