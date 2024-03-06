package hse.ru.vladch.controllers

import hse.ru.vladch.dao.BillDaoImpl
import hse.ru.vladch.dao.AccountDaoImpl
import hse.ru.vladch.dao.MenuItemDaoImpl
import hse.ru.vladch.dao.OrderDaoImpl
import hse.ru.vladch.enums.AccountType
import hse.ru.vladch.service.BankServiceImpl
import hse.ru.vladch.service.KitchenServiceImpl
import kotlin.system.exitProcess

class ConsoleControllerStart : Controller {
    private val accountDao = AccountDaoImpl()
    private val menuDao = MenuItemDaoImpl()
    private val orderDao = OrderDaoImpl()
    private val billDao = BillDaoImpl()
    private val bankService = BankServiceImpl(billDao)
    private val kitchenService = KitchenServiceImpl(orderDao, bankService)
    private var isInitiated = true

    // Запустить окно авторизации
    override fun launch() {
        if (isInitiated) {
            loadData()
        }
        isInitiated = false
        printHelloTable()
    }

    // Загрузить все сохраненные данные из файлов
    private fun loadData() {
        try {
            accountDao.loadData()
            menuDao.loadData()
            orderDao.loadData()
            billDao.loadData()
        } catch (e : Exception) {
            println(e.message)
            println("Unable to load saved data")
        }
    }

    // Напечатать меню и принять ответ пользоввателя
    private fun printHelloTable() {
        println("Select the option:")
        println("1 - Sign in")
        println("2 - Create account")
        println("3 - Exit program")
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
                singIn()
            }
            2 -> {
                createAccount()
            }
            3 -> {
                finishProgram()
            }
        }
    }

    // Получить данные у пользователя и авторизовать его
    private fun singIn() {
        println("Enter login:")
        val login = readln()
        println("Enter password:")
        val password = readln()
        try {
            val user = accountDao.authorizeUser(login, password)
            if (user.type == AccountType.ADMINISTRATOR) {
                val adminController = ConsoleControllerAdmin(this, menuDao, orderDao)
                adminController.launch()
            }
            if (user.type == AccountType.CLIENT) {
                val visitorController = ConsoleControllerVisitor(this,
                    menuDao, orderDao, kitchenService, bankService, login)
                visitorController.launch()
            }
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
    }

    // Получить данные у пользователя и создать аккаунт
    private fun createAccount() {
        try {
            println("Enter login:")
            val login = readln()
            println("Enter password:")
            val password = readln()
            println("Specify type [ADMIN / CLIENT]:")
            val typeString = readln()
            val type = when(typeString.lowercase()) {
                "admin" -> AccountType.ADMINISTRATOR
                "client" -> AccountType.CLIENT
                else -> throw RuntimeException("No such account type!")
            }
            //val user = accountDao.createAccount(type, login, password)
            if (type == AccountType.ADMINISTRATOR) {
                val adminController = ConsoleControllerAdmin(this, menuDao, orderDao)
                adminController.launch()
            }
            if (type == AccountType.CLIENT) {
                val visitorController = ConsoleControllerVisitor(this,
                    menuDao, orderDao, kitchenService, bankService, login)
                visitorController.launch()
            }
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
    }

    // Сохранить данные в файлы и завершить программу
    private fun finishProgram() {
        try {
            accountDao.saveData()
            menuDao.saveData()
            orderDao.saveData()
            billDao.saveData()
        } catch (e : Exception) {
            println("Unable to save data")
        } finally {
            exitProcess(0)
        }
    }
}