package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

class InMemoryOrderDao : OrderDao {
    private val orders = mutableListOf<OrderEntity>()
    override fun addOrder(creationTime: Long, list: MutableList<DishEntity>): Int {
        orders.add(OrderEntity(orders.size, creationTime, list))
        return orders.size - 1
    }

    override fun deleteOrderFromMemory(id: Int) {
        val order = findOrder(id) ?: throw RuntimeException("The order does not exist")
        orders.remove(order)
    }

    override fun cancelOrder(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllOrders(): MutableList<OrderEntity> {
        TODO("Not yet implemented")
    }

    override fun findOrder(id : Int): OrderEntity? {
        return orders.find { it.id == id }
    }
}