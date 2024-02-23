package hse.ru.vladch.service

import hse.ru.vladch.entities.OrderEntity

interface BankService {
    fun createBill(order : OrderEntity)
    fun getTheLastBill(user : String)
    fun payTheLastBill(user : String)
}