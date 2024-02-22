package hse.ru.vladch.controllers

import hse.ru.vladch.dao.InMemoryMenuItemDao
import hse.ru.vladch.entities.VisitorEntity
import hse.ru.vladch.service.AdminServiceImpl
import hse.ru.vladch.service.KitchenService

class ConsoleControllerVisitor(
    ctx : ConsoleController,
    menuInit : InMemoryMenuItemDao,
    kitchenService: KitchenService,
    user : String
) : Controller {
    private val context = ctx
    private val menu = menuInit
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
        println("4 - View all previous orders")
        println("5 - View the last order")
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
        printMenu()
    }

    private fun createOrder() {
        println("Menu:")
        printMenuItems()
        println()
        print("Enter START to finish input of dishes")
        var input = readln()
        while (input.lowercase() != "start") {

        }
        TODO()
    }

    private fun addDishToLastOrder() {
        TODO()
    }

    private fun getAllOrders() {
        TODO()
    }

    private fun getLastOrder() {
        TODO()
    }

    private fun cancelLastOrder() {

    }

    private fun exitToAuthorizationMenu() {
        context.launch()
    }
}