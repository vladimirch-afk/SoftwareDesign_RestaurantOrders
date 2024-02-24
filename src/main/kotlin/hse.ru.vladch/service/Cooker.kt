package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

interface Cooker {
    // Начать готовку заказа поваром
    fun startCooking(order : OrderEntity, kitchen : KitchenService)
    // Добавить в заказ новое блюдо
    fun addNewDish(dish : DishEntity)
    // Отменить готовку
    fun cancelProcess()
    // Получить статус повара (занят ли он)
    fun getStatus() : Boolean
    // Получить имя клиента, чей заказ готовится
    fun getClientName() : String
    // Оповестить кухню о готовности заказа
    fun notifyKitchen()
    // Получить текущий заказ у повара
    fun getOrder() : OrderEntity?
}