package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity

interface MenuItemDao {
    fun addDish(name : String, price : Int, time : Long, amount : Int = 0)
    fun deleteDish(name : String)
    fun setDishTime(name: String, time : Long)
    fun setDishPrice(name : String, price : Int)
    fun setDishAmount(name : String, amount : Int)
    fun setDishProperties(name : String, price : Int, time : Long, amount : Int = 0)
    fun renameDish(oldName: String, newName: String)
    fun getDish(name : String) : DishEntity?
    fun findDish(name : String) : Boolean
    fun getAllDishesString() : String
    fun getAllDishes() : MutableList<DishEntity>
    fun getDishReviews(name : String) : String
}