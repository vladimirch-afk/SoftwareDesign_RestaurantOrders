package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

interface OrderDao {
    // Добавить заказ в хранилице заказов
    fun addOrder(order : OrderEntity)
    // Добавить блюдо в заказ
    fun addDishToOrder(dish : DishEntity, order : OrderEntity)
    // Удалить заказ
    fun deleteOrderFromMemory(id : Int)
    // Отменить заказ
    fun cancelOrder(id : Int)
    // Получить все заказы
    fun getAllOrders() : MutableList<OrderEntity>
    // Получить все заказы пользователя
    fun getAllUserOrders(user : String) : MutableList<OrderEntity>
    // Найти заказ
    fun findOrder(id : Int) : OrderEntity?
    // Получить количество заказов
    fun getOrdersNumber() : Int
    // Сохранить данные о заказах в файл
    fun saveData()
    // Загрузить данные о заказах из файла
    fun loadData()
}