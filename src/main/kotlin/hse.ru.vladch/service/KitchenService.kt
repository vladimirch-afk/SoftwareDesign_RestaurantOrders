package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.OrderStatus

interface KitchenService {
    // Создать заказ
    fun createOrder(user : String, dishes : MutableList<DishEntity>)
    // Добавить блюдо в заказ
    fun addDishToOrder(user : String, dish : DishEntity)
    // Отменить заказ
    fun cancelOrder(user : String)
    // Получить заказ клиента
    fun getOrderOfVisitor(user : String) : OrderEntity
    // Получить статус заказа
    fun getOrderStatus(user : String) : OrderStatus
    // Изменить статус заказа на готовый
    fun changeOrderStatus(order : OrderEntity)
}