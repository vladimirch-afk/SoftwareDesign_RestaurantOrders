package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity

interface AdminService {
    // Получить среднюю оценку блюд
    fun getAverageDishScore() : Int
    // Получить блюдо с самой высокой средней оценкой
    fun getBestDish() : String
    // Получить блюдо, которое заказывали чаще всего
    fun getMostPopularDish() : String
    // Получить среднюю оценку каждого блюда
    fun getDishScores() : String
    // Получить количество заказов за период
    fun getOrdersNumForPeriod(start : Long, end : Long) : Int
    // Получить общее количество заказов
    fun getOrdersNum() : Int
    // Получить количество денег, полученных рестораном
    fun getTotalMoneyReceived() : Long
}