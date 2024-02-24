package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity

interface AdminService {
    fun getAverageDishScore() : Int
    fun getBestDish() : String
    fun getMostPopularDish() : String
    fun getDishScores() : String
    fun getOrdersNumForPeriod(start : Long, end : Long) : Int
    fun getOrdersNum() : Int
    fun getTotalMoneyReceived() : Long
}