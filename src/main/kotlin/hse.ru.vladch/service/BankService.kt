package hse.ru.vladch.service

import hse.ru.vladch.entities.BillEntity
import hse.ru.vladch.entities.OrderEntity

interface BankService {
    // Создать счет через заказ
    fun createBill(order : OrderEntity)
    // Получить последний счет пользователя
    fun getTheLastBill(user : String) : BillEntity?
    // Проверить, нет ли у пользователя неоплаченный счет
    fun checkIfNoDebt(user : String) : Boolean
    // Оплатить последний счет пользователя
    fun payTheLastBill(user : String, money : Int) : String
}