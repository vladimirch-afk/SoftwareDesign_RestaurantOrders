package hse.ru.vladch.service

import hse.ru.vladch.dao.InMemoryMenuItemDao
import hse.ru.vladch.dao.MenuItemDao

class AdminServiceImpl(menu: MenuItemDao) : AdminService {
    private val menuItemDao = menu
    override fun addDish(name: String, price: Int, time: Long, amount : Int) {
        menuItemDao.addDish(name, price, time, amount)
    }

    override fun deleteDish(name: String) {
        menuItemDao.deleteDish(name)
    }

    override fun setDishTime(name: String, time: Long) {
        menuItemDao.setDishTime(name, time)
    }

    override fun setDishPrice(name: String, price: Int) {
        menuItemDao.setDishPrice(name, price)
    }

    override fun setDishProperties(name: String, price: Int, time: Long, amount : Int) {
        menuItemDao.setDishProperties(name, price, time, amount)
    }

    override fun setDishAmount(name: String, amount : Int) {
        menuItemDao.setDishAmount(name, amount)
    }

    override fun renameDish(oldName: String, newName: String) {
        menuItemDao.renameDish(oldName, newName)
    }

    override fun getDishesList(): String {
        return menuItemDao.getAllDishesString()
    }

    override fun getAverageDishScore(): Int {
        TODO("Not yet implemented")
    }

    override fun getBestDish(): String {
        val dishes = menuItemDao.getAllDishes()
        var bestDish = dishes[0]
        var bestScore = dishes[0].reviews.sumOf { it.score } / dishes[0].reviews.size
        for (dish in dishes) {
            val score = dish.reviews.sumOf { it.score } / dish.reviews.size
            if (score > bestScore) {
                bestDish = dish
                bestScore = score
            }
        }
        return "Name: ${bestDish.name}, Avg score: $bestScore"
    }

    override fun getMostPopularDish(): String {
        TODO()
    }

    override fun getDishScores(): String {
        val dishes = menuItemDao.getAllDishes()
        val stringBuilder = StringBuilder()
        var i = 1
        for (dish in dishes) {
            val scoreSum = dish.reviews.sumOf { it.score }
            stringBuilder.append("${i + 1}) Name: ${dish.name}, Avr score: ${scoreSum/dish.reviews.size}\n")
            i += 1
        }
        return stringBuilder.toString()
    }

    override fun getOrdersNumForPeriod(start: Long, end: Long): Int {
        TODO("Not yet implemented")
    }

    override fun getOrdersNum(): Int {
        TODO("Not yet implemented")
    }

    override fun getDishReviews(name: String): String {
        return menuItemDao.getDishReviews(name)
    }
}