package hse.ru.vladch.controllers

import hse.ru.vladch.dao.InMemoryMenuItemDao
import hse.ru.vladch.service.AdminService
import hse.ru.vladch.service.AdminServiceImpl
import kotlin.contracts.contract

class ConsoleControllerAdmin(ctx : ConsoleController) : Controller {
    private val menu = InMemoryMenuItemDao()
    private val admin = AdminServiceImpl(menu)
    private val context = ctx
    override fun launch() {
        printHelloTable()
    }

    private fun printHelloTable() {
        println("Select the option:")
        println("1 - Edit menu")
        println("2 - Get statistics")
        print("3 - Exit to authorization menu")
        var ans = 0
        try {
            ans = readln().toInt()
        } catch (e : Exception) {
            println("Wrong input! Try again")
            printHelloTable()
        }
        if ((ans < 1) || (ans > 3)) {
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
                finishProgram()
            }
        }
    }

    fun printEditMenu() {
        println("Select the edit menu option:")
        println("1 - Add new dish")
        println("2 - Delete dish")
        println("3 - Edit price of the dish")
        println("4 - Edit time of the dish")
        println("5 - Edit name of the dish")
        println("6 - Edit the amount of the dish")
        println("7 - Edit the dish properties")
        print("8 - Cancel")
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
                finishProgram()
            }
        }
    }

    fun printStatisticsMenu() {
        println("Select the statistic menu option:")
        println("1 - Get the best dish")
        println("2 - Get most popular dish")
        println("3 - Get the dish reviews")
        println("4 - Get all dish scores")
        println("5 - Get total number of orders")
        println("6 - Get number of orders in selected period")
        print("7 - Cancel")
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
                finishProgram()
            }
        }
    }

    fun addDish() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter the dish price:")
            val price = readln().toInt()
            println("Enter the dish time complexity in seconds:")
            val time = readln().toLong()
            println("Enter the dish amount:")
            val amount = readln().toInt()
            admin.addDish(name, price, time, amount)
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

    fun deleteDish() {
        try {
            println("Enter the dish name:")
            val name = readln()
            admin.deleteDish(name)
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

    fun editPrice() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new price:")
            val price = readln().toInt()
            admin.setDishPrice(name, price)
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

    fun editAmount() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new amount:")
            val amount = readln().toInt()
            admin.setDishAmount(name, amount)
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

    fun editName() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new name:")
            val newName = readln()
            admin.renameDish(name, newName)
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

    fun editTimeComplexity() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter new price:")
            val time = readln().toLong()
            admin.setDishTime(name, time)
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

    fun editDishProperties() {
        try {
            println("Enter the dish name:")
            val name = readln()
            println("Enter the dish price:")
            val price = readln().toInt()
            println("Enter the dish time complexity in seconds:")
            val time = readln().toLong()
            println("Enter the dish amount:")
            val amount = readln().toInt()
            admin.setDishProperties(name, price, time, amount)
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

    fun finishProgram() {
        context.launch()
    }

    fun getTheBestDish() {
        try {
            println("The best dish is:")
            admin.getBestDish()
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    fun getMostPopularDish() {
        try {
            println("The most popular dish:")
            admin.getMostPopularDish()
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    fun getDishReviews() {
        try {
            println("Enter the name of the dish:")
            val name = readln()
            println(admin.getDishReviews(name))
        } catch (e : RuntimeException) {
            println(e.message)
            printHelloTable()
        } catch (e : Exception) {
            println("Something went wrong...")
            printHelloTable()
        }
        printHelloTable()
    }

    fun getAllDishScores() {
        try {
            println(admin.getDishScores())
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    fun getTotalOrdersNum() {
        try {
            println("The total number of orders:")
            println(admin.getOrdersNum())
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

    fun getOrdersNumInPeriod() {
        try {
            println("Enter the start date in seconds:")
            val start = readln().toLong()
            println("Enter the end date in seconds:")
            val end = readln().toLong()
            println("The number of orders in this period:")
            println(admin.getOrdersNumForPeriod(start, end))
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
        printHelloTable()
    }

}