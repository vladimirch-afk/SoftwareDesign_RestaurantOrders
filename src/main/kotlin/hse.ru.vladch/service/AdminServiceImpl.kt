package hse.ru.vladch.service

import hse.ru.vladch.dao.InMemoryMenuItemDao
import hse.ru.vladch.dao.MenuItemDao
import hse.ru.vladch.dao.OrderDao
import hse.ru.vladch.entities.DishEntity

class AdminServiceImpl(menu: MenuItemDao, orderD : OrderDao) : AdminService {
    private val menuItemDao = menu
    private val orderDao = orderD

    override fun getAverageDishScore(): Int {
        val dishes = menuItemDao.getAllDishes()
        if (dishes.isEmpty()) {
            throw RuntimeException("No dishes in the menu!")
        }
        var scoreSum = 0
        for (dish in dishes) {
            scoreSum += dish.reviews.sumOf { it.score }
        }
        return scoreSum / dishes.size
    }

    override fun getBestDish(): String {
        val dishes = menuItemDao.getAllDishes()
        if (dishes.isEmpty()) {
            throw RuntimeException("No dishes in the menu!")
        }
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
        val orders = orderDao.getAllOrders()
        if (orders.isEmpty()) {
            throw RuntimeException("No orders were made yet to get popular dish!")
        }
        val orderTimes = mutableMapOf<String, Int>()
        for (order in orders) {
            for (dish in order.dishes) {
                if (!orderTimes.keys.contains(dish.name)) {
                    orderTimes[dish.name] = 1
                } else {
                    orderTimes[dish.name]!!.plus(1)
                }
            }
        }
        var dish = orderTimes.keys.first()
        var mxNum = 0
        for (name in orderTimes.keys) {
            if (orderTimes[name]!! > mxNum) {
                mxNum = orderTimes[name]!!
                dish = name
            }
        }
        return dish
    }

    override fun getDishScores(): String {
        val dishes = menuItemDao.getAllDishes()
        val stringBuilder = StringBuilder()
        var i = 1
        for (dish in dishes) {
            val scoreSum = dish.reviews.sumOf { it.score }
            stringBuilder.append("${i + 1}) Name: ${dish.name}, " +
                    "Avr score: ${if (scoreSum != 0) (scoreSum/dish.reviews.size) else 0}\n")
            i += 1
        }
        return stringBuilder.toString()
    }

    override fun getOrdersNumForPeriod(start: Long, end: Long): Int {
        val orders = orderDao.getAllOrders()
        var counter = 0
        for (item in orders) {
            if (item.creationTime in start..end) {
                ++counter
            }
        }
        return counter
    }

    override fun getOrdersNum(): Int {
        return orderDao.getAllOrders().size
    }

    override fun getTotalMoneyReceived(): Long {
        return orderDao.getAllOrders().sumOf { it -> it.dishes.sumOf { it.price } }.toLong()
    }
}