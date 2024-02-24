package hse.ru.vladch.dao

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.ReviewEntity

interface MenuItemDao {
    // Создать новое блюдо в меню
    fun addDish(name : String, price : Int, time : Long, amount : Int = 0)
    // Удалить блюдо из меню
    fun deleteDish(name : String)
    // Установить временную сложность для блюда
    fun setDishTime(name: String, time : Long)
    // Установить ценую блюда
    fun setDishPrice(name : String, price : Int)
    // Установить количество блюда
    fun setDishAmount(name : String, amount : Int)
    // Установить характеристики блюда
    fun setDishProperties(name : String, price : Int, time : Long, amount : Int = 0)
    // Переименовать блюдо
    fun renameDish(oldName: String, newName: String)
    // Получить блюдо по имени
    fun getDish(name : String) : DishEntity?
    // Получить блюдо по id (индекс в массиве блюд)
    fun getDishById(id : Int) : DishEntity?
    // Найти блюдо по имени
    fun findDish(name : String) : Boolean
    // Получить строку с информацией о всех блюдах
    fun getAllDishesString() : String
    // Получить все блюда
    fun getAllDishes() : MutableList<DishEntity>
    // Получить отзывы о блюде
    fun getDishReviews(name : String) : String
    // Добавить отзыв о блюде
    fun addDishReview(dishName : String, reviewEntity: ReviewEntity)
    // Изменить доступное количество блюда
    fun changeDishAmount(dishEntity: DishEntity, change : Int)
    // Сохранить данные о меню в файл
    fun saveData()
    // Загрузить данные о меню из файла
    fun loadData()
}