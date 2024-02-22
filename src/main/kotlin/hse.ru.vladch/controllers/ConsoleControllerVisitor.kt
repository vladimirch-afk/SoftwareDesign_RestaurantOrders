package hse.ru.vladch.controllers

import hse.ru.vladch.dao.InMemoryMenuItemDao
import hse.ru.vladch.dao.InMemoryOrderDao
import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.VisitorEntity
import hse.ru.vladch.service.AdminServiceImpl
import hse.ru.vladch.service.KitchenService

class ConsoleControllerVisitor(
    ctx : ConsoleController,
    menuInit : InMemoryMenuItemDao,
    orderInit : InMemoryOrderDao,
    kitchenService: KitchenService,
    user : String
) : Controller {
    private val context = ctx
    private val menu = menuInit
    private val orderDao = orderInit
    private val kitchen = kitchenService
    private val login = user
    override fun launch() {
        printMenu()
    }

    private fun printMenu() {
        println("Select the option:")
        println("1 - View menu")
        println("2 - Make an order")
        println("3 - Add dish to the last order")
        println("4 - View all previous orders (completed orders)")
        println("5 - View the not ready order")
        println("6 - Cancel last order")
        print("7 - Exit program")
        var ans = 0
        try {
            ans = readln().toInt()
        } catch (e : Exception) {
            println("Wrong input! Try again")
            printMenu()
        }
        if ((ans < 1) || (ans > 7)) {
            println("Incorrect input! Try again...")
            printMenu()
        }
        when (ans) {
            1 -> {
                printMenuItems()
                printMenu()
            }
            2 -> {
                createOrder()
            }
            3 -> {
                addDishToLastOrder()
            }
            4 -> {
                getAllOrders()
            }
            5 -> {
                getLastOrder()
            }
            6 -> {
                cancelLastOrder()
            }
            7 -> {
                exitToAuthorizationMenu()
            }
        }
    }

    private fun printMenuItems() {
        try {
            println(menu.getAllDishesString())
        } catch (e : Exception) {
            println(e.message)
        }
    }

    private fun createOrder() {
        val dishes = mutableListOf<DishEntity>()
        println("Menu:")
        printMenuItems()
        println()
        print("Enter START to finish input of dishes")
        print("Enter CANCEL to cancel input of dishes")
        print("Enter the dish ID:")
        var input = readln()
        while (input.lowercase() != "start" && input.lowercase() != "cancel") {
            val inp = input.toInt()
            try {
                val id = inp.toInt()
                val dish = menu.getDishById(id)
                dishes.add(dish!!)
            } catch (e : Exception) {
                println("Exception occurred!")
            }
            println("Enter the dish ID:")
            input = readln()
        }
        if (input.lowercase() == "cancel") {
            println("Order was cancelled")
            printMenu()
            return
        }
        try {
            kitchen.createOrder(login, dishes)
            println("Order was delivered to the kitchen")
        } catch (e : Exception) {
            println(e.message)
        }
        printMenu()
    }

    private fun addDishToLastOrder() {
        println("Menu:")
        printMenuItems()
        println()
        println("Print dish name:")
        val input = readln()
        val inp = input.toInt()
        try {
            val id = inp.toInt()
            val dish = menu.getDishById(id)
            kitchen.addDishToOrder(login, dish!!)
        } catch (e : Exception) {
            println(e.message)
        }
        printMenu()
    }

    private fun getAllOrders() {
        try {
            val orders = orderDao.getAllUserOrders(login)
            println("All previous orders for user $login:")
            for (order in orders) {
                println(order.toString())
            }
        } catch (e : Exception) {
            println(e.message)
        }
        println()
        printMenu()
    }

    private fun getLastOrder() {
        try {
            val order = kitchen.getOrderOfVisitor(login)
            println("Last order of the user $login:")
            println(order)
        } catch (e : Exception) {
            println(e.message)
        }
        printMenu()
    }

    private fun cancelLastOrder() {
        try {
            kitchen.cancelOrder(login)
            println("Order cancelled")
        } catch (e : Exception) {
            println(e.message)
        }
        println()
        printMenu()
    }

    private fun exitToAuthorizationMenu() {
        context.launch()
    }
}