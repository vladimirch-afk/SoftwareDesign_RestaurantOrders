package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

interface KitchenService {
    fun createOrder(user : String, dishes : MutableList<DishEntity>)
    fun addDishToOrder(user : String, dish : DishEntity)
    fun cancelOrder(user : String)
    fun getOrdersOfVisitor(user : String)
    fun getOrderStatus(user : String)
    fun changeOrderStatus()
}