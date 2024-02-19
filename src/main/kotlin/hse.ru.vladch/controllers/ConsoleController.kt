package hse.ru.vladch.controllers

import hse.ru.vladch.dao.InMemoryAccountDao
import hse.ru.vladch.enums.AccountType
import kotlin.system.exitProcess

class ConsoleController : Controller {
    private val accountDao = InMemoryAccountDao()
    private val adminController = ConsoleControllerAdmin(this)
    private val visitorController = ConsoleControllerVisitor()
    override fun launch() {
        printHelloTable()
    }

    private fun printHelloTable() {
        println("Select the option:")
        println("1 - Sign in")
        println("2 - Create account")
        print("3 - Exit program")
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

    private fun singIn() {
        println("Enter login:")
        val login = readln()
        println("Enter password:")
        val password = readln()
        try {
            val user = accountDao.authorizeUser(login, password)
            if (user.type == AccountType.ADMINISTRATOR) {
                adminController.launch()
            }
            if (user.type == AccountType.CLIENT) {
                visitorController.launch()
            }
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
    }

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
            val user = accountDao.createAccount(type, login, password)
            if (type == AccountType.ADMINISTRATOR) {
                adminController.launch()
            }
            if (type == AccountType.CLIENT) {
                visitorController.launch()
            }
        } catch (e : Exception) {
            println(e.message)
            printHelloTable()
        }
    }

    private fun finishProgram() {
        exitProcess(0)
    }
}