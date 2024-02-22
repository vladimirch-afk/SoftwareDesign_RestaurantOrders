package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

interface OrderDao {
    fun addOrder(order : OrderEntity)
    fun addDishToOrder(dish : DishEntity, order : OrderEntity)
    fun deleteOrderFromMemory(id : Int)
    fun cancelOrder(id : Int)
    fun getAllOrders() : MutableList<OrderEntity>
    fun getAllUserOrders(user : String) : MutableList<OrderEntity>
    fun findOrder(id : Int) : OrderEntity?
    fun getOrdersNumber() : Int
}