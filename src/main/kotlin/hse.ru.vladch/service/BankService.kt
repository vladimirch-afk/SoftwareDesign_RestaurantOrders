package hse.ru.vladch.service

import hse.ru.vladch.entities.BillEntity
import hse.ru.vladch.entities.OrderEntity

interface BankService {
    fun createBill(order : OrderEntity)
    fun getTheLastBill(user : String) : BillEntity?
    fun checkIfNoDebt(user : String) : Boolean
    fun payTheLastBill(user : String, money : Int) : String
}