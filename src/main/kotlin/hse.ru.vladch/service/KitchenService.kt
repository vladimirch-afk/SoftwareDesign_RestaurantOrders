package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.OrderStatus

interface KitchenService {
    fun createOrder(user : String, dishes : MutableList<DishEntity>)
    fun addDishToOrder(user : String, dish : DishEntity)
    fun cancelOrder(user : String)
    fun getOrderOfVisitor(user : String) : OrderEntity
    fun getOrderStatus(user : String) : OrderStatus
    fun changeOrderStatus(order : OrderEntity)
}