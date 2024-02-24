package hse.ru.vladch

import hse.ru.vladch.controllers.ConsoleController
import hse.ru.vladch.controllers.Controller

fun main() {
    val app = ConsoleController()
    // Запустить консольное окно авторизации
    app.launch()
}