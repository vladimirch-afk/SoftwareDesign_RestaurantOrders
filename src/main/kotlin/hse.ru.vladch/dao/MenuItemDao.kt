package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity

interface MenuItemDao {
    fun addDish(name : String, price : Int, time : Long)
    fun deleteDish(name : String)
    fun setDishTime(name: String, time : Long)
    fun setDishPrice(name : String, price : Int)
    fun setDishProperties(name : String, price : Int, time : Long)
    fun renameDish(oldName: String, newName: String)
    fun getDish(name : String) : DishEntity?
    fun getAllDishes() : String
}