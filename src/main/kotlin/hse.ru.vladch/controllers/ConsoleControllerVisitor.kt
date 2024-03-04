package hse.ru.vladch.controllers

import hse.ru.vladch.dao.InMemoryMenuItemDao
import hse.ru.vladch.dao.InMemoryOrderDao
import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.ReviewEntity
import hse.ru.vladch.service.BankService
import hse.ru.vladch.service.KitchenService

class ConsoleControllerVisitor(
    ctx : ConsoleController,
    menuInit : InMemoryMenuItemDao,
    orderInit : InMemoryOrderDao,
    kitchenService: KitchenService,
    bankService: BankService,
    user : String
) : Controller {
    private val context = ctx
    private val menu = menuInit
    private val orderDao = orderInit
    private val kitchen = kitchenService
    private val bank = bankService
    private val login = user
    private var action = printMenu()
    override fun launch() {
        action
    }

    // Вывести главное меню посетителя
    private fun printMenu() {
        println("Select the option:")
        println("1 - View menu")
        println("2 - Make an order")
        println("3 - Add dish to the last order")
        println("4 - View all previous orders (completed orders)")
        println("5 - View the not ready order")
        println("6 - Cancel last order")
        println("7 - Pay for the last order")
        println("8 - Leave a review for a dish")
        println("9 - Exit program")
        var ans = 0
        try {
            ans = readln().toInt()
        } catch (e : Exception) {
            println("Wrong input! Try again")
            printMenu()
        }
        if ((ans < 1) || (ans > 9)) {
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
                payForTheLastOrder()
            }
            8 -> {
                leaveReview()
            }
            9 -> {
                exitToAuthorizationMenu()
            }
        }
    }

    // Вывести меню ресторана
    private fun printMenuItems() {
        try {
            println(menu.getAllDishesString())
        } catch (e : Exception) {
            println(e.message)
        }
    }

    // Создать заказ и отправить на кухню
    private fun createOrder() {
        if (!bank.checkIfNoDebt(login)) {
            println("You have to pay the previous order!")
            printMenu()
        }
        val dishes = mutableListOf<DishEntity>()
        println("Menu:")
        printMenuItems()
        println()
        println("Enter START to finish input of dishes")
        println("Enter CANCEL to cancel input of dishes")
        println("Enter the dish ID:")
        try {
            var input = readln()
            while (input.lowercase() != "start" && input.lowercase() != "cancel") {
                val inp = input.toInt()
                val dish = menu.getDishById(inp - 1)
                dishes.add(dish!!)
                println("Enter the dish ID:")
                input = readln()
            }
            if (input.lowercase() == "cancel") {
                println("Order was cancelled")
                printMenu()
                return
            }
            kitchen.createOrder(login, dishes)
            println("Order was delivered to the kitchen")
        } catch (e : Exception) {
            println(e.message)
        }
        printMenu()
    }

    // Добавить блюдо к уже созданному заказу
    private fun addDishToLastOrder() {
        println("Menu:")
        printMenuItems()
        println()
        println("Print dish name:")
        val input = readln()
        val inp = input.toInt()
        try {
            val dish = menu.getDishById(inp)
            kitchen.addDishToOrder(login, dish!!)
        } catch (e : Exception) {
            println(e.message)
        }
        printMenu()
    }

    // Получить все завершенные заказы пользователя
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

    // Получить все еще не готовый заказ пользователя
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

    // Отменить все еще готовящийся заказ
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

    // Оставить отзыв на блюдо
    private fun leaveReview() {
        try {
            printMenuItems()
            println("Enter the NAME of the dish to evaluate:")
            val name = readln()
            println("Enter the STARS (mark 1-5) of the dish to evaluate:")
            val mark = readln().toInt()
            if (mark < 1 || mark > 5) {
                throw RuntimeException("The mark should be in 1-5 range!")
            }
            println("Enter the TEXT for your review:")
            val text = readln()
            val review = ReviewEntity(mark, text)
            menu.addDishReview(name, review)
        } catch (e : Exception) {
            println(e.message)
        } finally {
            printMenu()
        }
    }

    // Заплатить за последний заказ
    private fun payForTheLastOrder() {
        try {
            if (bank.checkIfNoDebt(login)) {
                println("No orders to pay for!")
                printMenu()
            }
            val bill = bank.getTheLastBill(login)
            println("Required amount of money: ${bill!!.value}")
            println("Enter the amount of money you want to give:")
            val money = readln().toInt()
            println(bank.payTheLastBill(login, money))
            println()
        } catch (e : Exception) {
            println(e.message)
        } finally {
            printMenu()
        }
    }

    // Выйти в окно авторизации
    private fun exitToAuthorizationMenu() {
        context.launch()
    }
}