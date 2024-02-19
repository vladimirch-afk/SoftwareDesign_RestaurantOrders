package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

interface OrderDao {
    fun addOrder(creationTime : Long, list : MutableList<DishEntity>) : Int
    fun deleteOrderFromMemory(id : Int)
    fun cancelOrder(id : Int)
    fun getAllOrders() : MutableList<OrderEntity>
    fun findOrder(id : Int) : OrderEntity?
}