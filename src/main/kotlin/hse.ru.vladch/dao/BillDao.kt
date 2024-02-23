package hse.ru.vladch.dao

import hse.ru.vladch.entities.BillEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.PaymentStatus

interface BillDao {
    fun createBill(user : String, order : OrderEntity) : BillEntity
    fun getUserBills(user : String) : MutableList<BillEntity>
    fun getBillStatus(id : Int) : PaymentStatus
    fun changeBillStatus(id : Int, newStatus : PaymentStatus)
}
