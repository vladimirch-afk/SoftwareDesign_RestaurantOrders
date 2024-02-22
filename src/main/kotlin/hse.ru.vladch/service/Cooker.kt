package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

interface Cooker {
    fun startCooking(order : OrderEntity, kitchen : KitchenService)
    fun addNewDish(dish : DishEntity)
    fun cancelProcess()
    fun getStatus() : Boolean
    fun getClientName() : String
    fun notifyKitchen()
    fun getOrder() : OrderEntity?
}