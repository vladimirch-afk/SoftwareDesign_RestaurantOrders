package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import hse.ru.vladch.entities.BillEntity
import java.io.File
import kotlin.io.path.Path

class InMemoryOrderDao : OrderDao {
    private var orders = mutableListOf<OrderEntity>()
    override fun addOrder(order : OrderEntity) {
        orders.add(order)
    }

    override fun addDishToOrder(dish: DishEntity, order: OrderEntity) {
        TODO("Not yet implemented")
    }

    override fun deleteOrderFromMemory(id: Int) {
        val order = findOrder(id) ?: throw RuntimeException("The order does not exist")
        orders.remove(order)
    }

    override fun cancelOrder(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllOrders(): MutableList<OrderEntity> {
        return orders
    }

    override fun getAllUserOrders(user: String): MutableList<OrderEntity> {
        val tmp = mutableListOf<OrderEntity>()
        for (item in orders) {
            if (item.user == user) {
                tmp.add(item)
            }
        }
        return tmp
    }

    override fun findOrder(id : Int): OrderEntity? {
        return orders.find { it.id == id }
    }

    override fun getOrdersNumber(): Int {
        return orders.size
    }

    override fun loadData() {
        val directory = "src/main/resources"
        val fileName = "orders.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        orders = objectMapper.readValue<MutableList<OrderEntity>>(file.readText())
    }

    override fun saveData() {
        val directory = "src/main/resources"
        val fileName = "orders.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        objectMapper.writeValue(file, orders)
    }
}