package hse.ru.vladch.controllers

import hse.ru.vladch.dao.MenuItemDao
import hse.ru.vladch.dao.OrderDao
import hse.ru.vladch.service.AdminServiceImpl
import java.text.SimpleDateFormat

class ConsoleControllerAdmin(
    ctx : ConsoleControllerStart,
    menuDao : MenuItemDao,
    orderD : OrderDao
) : Controller {
    private val menu = menuDao
    private val orderDao = orderD
    private val admin = AdminServiceImpl(menu, orderDao)
    private val context = ctx
    override fun launch() {
        printHelloTable()
    }

    // Напечатать главное меню администратора и принять ответ пользователя
    private fun printHelloTable() {
        println("Select the option:")
        println("1 - Edit menu")
        println("2 - Get statistics")
        println("3 - Print menu")
        println("4 - Exit to authorization menu")
        var ans = 0
        try {
            ans = readln().toInt()
        } catch (e : Exception) {
            println("Wrong input! Try again")
            printHelloTable()
        }
        if ((ans < 1) || (ans > 4)) {
            println("Incorrect input! Try again...")
            printHelloTable()
        }
        when (ans) {
            1 -> {
                printEditMenu()
            }
            2 -> {
                printStatisticsMenu()
            }
            3 -> {
                printAllMenu()
            }
            4 -> {
                finishProgram()
            }
        }
    }

    // Напечатать меню, связанное с редактированием меню ресторана
    private fun printEditMenu() {
        println("Select the edit menu option:")
        println("1 - Add new dish")
        println("2 - Delete dish")
        println("3 - Edit price of the dish")
        println("4 - Edit time of the dish")
        println("5 - Edit name of the dish")
        println("6 - Edit the amount of the dish")
        println("7 - Edit the dish properties")
        println("8 - Cancel")
        var ans = 0
        try {
            ans = readln().toInt()
        } catch (e : Exception) {
            println("Wrong input! Try again")
            printHelloTable()
        }
        if ((ans < 1) || (ans > 8)) {
            println("Incorrect input! Try again...")
            printHelloTable()
        }
        when (ans) {
            1 -> {
                addDish()
            }
            2 -> {
                deleteDish()
            }
            3 -> {
                editPrice()
            }
            4 -> {
                editTimeComplexity()
            }
            5 -> {
                editName()
            }
            6 -> {
                editAmount()
            }
            7 -> {
                editDishProperties()
            }
            8 -> {
                cancel()
            }
        }
    }

    // Напечатать меню, связанное с получением статистики о ресторане
    private fun printStatisticsMenu() {
        println("Select the statistic menu option:")
        println("1 - Get the best dish")
        println("2 - Get most popular dish")
        println("3 - Get the dish reviews")
        println("4 - Get all dish scores")
        println("5 - Get total number of orders")
        println("6 - Get number of orders in selected period")
        println("7 - Get total money received")
        println("8 - Cancel")
        var ans = 0
        try {
            ans = readln().toInt()
        } catch (e : Exception) {
            println("Wrong input! Try again")
            printHelloTable()
        }
        if ((ans < 1) || (ans > 7)) {
            println("Incorrect input! Try again...")
            printHelloTable()
        }
        when (ans) {
            1 -> {
                getTheBestDish()
            }
            2 -> {
                getMostPopularDish()
            }
            3 -> {
                getDishReviews()
            }
            4 -> {
                getAllDishScores()
            }
            5 -> {
                getTotalOrdersNum()
            }
            6 -> {
                getOrdersNumInPeriod()
            }
            7 -> {
                getTotalMoneyReceived()
            }
            8 -> {
                cancel()
            }
        }
    }

    // Напечатать меню ресторана
    private fun printAllMenu() {
        try {
            println(menu.getAllDishesString())
        } catch (e : Exception) {
            println(e.message)
        }
        printHelloTable()
    }

    // Добавить блюдо в меню ресторана
    private fun addDish() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter the dish price:")
            val price = readln().toInt()
            println("Enter the dish time complexity in seconds:")
            val time = readln().toLong()
            println("Enter the dish amount:")
            val amount = readln().toInt()
            menu.addDish(name, price, time, amount)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Incorrect input!")
            printHelloTable()
        }
        println("The dish was created!")
        printHelloTable()
    }

    // Удалить блюдо из меню ресторана
    private fun deleteDish() {
        try {
            println("Enter the dish name:")
            val name = readln()
            menu.deleteDish(name)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        println("The dish was deleted")
        printHelloTable()
    }

    // Изменить цену блюда
    private fun editPrice() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new price:")
            val price = readln().toInt()
            menu.setDishPrice(name, price)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        println("The price was changed")
        printHelloTable()
    }

    private fun editAmount() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new amount:")
            val amount = readln().toInt()
            menu.setDishAmount(name, amount)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        println("The amount was changed")
        printHelloTable()
    }

    // Изменить название блюда в меню ресторана
    private fun editName() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new name:")
            val newName = readln()
            menu.renameDish(name, newName)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        println("The price was changed")
        printHelloTable()
    }

    // Изменить временную сложность блюда
    private fun editTimeComplexity() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new time complexity in seconds:")
            val time = readln().toLong()
            menu.setDishTime(name, time)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        println("The time complexity was changed")
        printHelloTable()
    }

    // Изменить характеристики блюда по его имени
    private fun editDishProperties() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter the dish price:")
            val price = readln().toInt()
            println("Enter the dish time complexity in seconds:")
            val time = readln().toLong()
            println("Enter the dish amount:")
            val amount = readln().toInt()
            menu.setDishProperties(name, price, time, amount)
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        println("The price was changed")
        printHelloTable()
    }

    // Вернуться в окно авторизации
    private fun finishProgram() {
        context.launch()
    }

    // Вернуться в главное меню администратора
    private fun cancel() {
        printHelloTable()
    }

    // Вывести лучшее блюдо в ресторане
    private fun getTheBestDish() {
        try {
            println("The best dish is:")
            println(admin.getBestDish())
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    // Вывести самое популярное блюдо в ресторане
    private fun getMostPopularDish() {
        try {
            println("The most popular dish:")
            println(admin.getMostPopularDish())
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    // Получить отзывы о блюде по его имени
    private fun getDishReviews() {
        try {
            println("Enter the NAME of the dish:")
            val name = readln()
            println(menu.getDishReviews(name))
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        printHelloTable()
    }

    // Получить среднюю оценку для каждого блюда
    private fun getAllDishScores() {
        try {
            println(admin.getDishScores())
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    // Получить общее число заказов
    private fun getTotalOrdersNum() {
        try {
            println("The total number of orders:")
            println(admin.getOrdersNum())
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    // Получить коичество заказов за период
    private fun getOrdersNumInPeriod() {
        try {
            println("Enter the start date in format [dd-MM-yyyy]:")
            val start = readln()
            println("Enter the end date in format [dd-MM-yyyy]:")
            val end = readln()
            println("The number of orders in this period:")
            println(admin.getOrdersNumForPeriod(
                SimpleDateFormat("dd-MM-yyyy").parse(start).time,
                SimpleDateFormat("dd-MM-yyyy").parse(end).time))
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    // Получить общую выручку ресторана
    private fun getTotalMoneyReceived() {
        try {
            println("Total money received by the restaurant:")
            println(admin.getTotalMoneyReceived())
        } catch (e : Exception) {
            println(e.message)
        }
        printHelloTable()
    }
}