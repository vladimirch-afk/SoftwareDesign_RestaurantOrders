package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity

interface AdminService {
    fun addDish(name : String, price : Int, time : Long, amount : Int = 0)
    fun deleteDish(name : String)
    fun setDishTime(name: String, time : Long)
    fun setDishPrice(name : String, price : Int)
    fun setDishProperties(name : String, price : Int, time : Long, amount : Int = 0)
    fun setDishAmount(name : String, amount : Int)
    fun renameDish(oldName: String, newName: String)
    fun getDishesList() : String
    fun getAverageDishScore() : Int
    fun getBestDish() : String
    fun getMostPopularDish() : String
    fun getDishScores() : String
    fun getOrdersNumForPeriod(start : Long, end : Long) : Int
    fun getOrdersNum() : Int
    fun getDishReviews(name : String) : String
}