package hse.ru.vladch.service

import hse.ru.vladch.dao.BillDaoImpl
import hse.ru.vladch.entities.BillEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.PaymentStatus

class BankServiceImpl(bDao : BillDaoImpl) : BankService {
    private val billDao = bDao
    override fun createBill(order: OrderEntity) {
        billDao.createBill(order)
    }

    override fun getTheLastBill(user: String) : BillEntity? {
        try {
            val bills = billDao.getUserBills(user)
            if (bills.isEmpty()) {
                return null
            }
            return bills.last()
        } catch (e : Exception) {
            throw RuntimeException(e.message)
        }
    }

    override fun checkIfNoDebt(user: String): Boolean {
        val bill = getTheLastBill(user)
        return bill == null || bill.status == PaymentStatus.PAYED
    }

    override fun payTheLastBill(user: String, money : Int) : String {
        if (checkIfNoDebt(user)) {
            throw RuntimeException("The user does not have any unpaid bills yet!")
        }
        val bill = getTheLastBill(user)
        var response = ""
        if (money > bill!!.value) {
            response = "Order was paid, change: ${money - bill.value}"
            billDao.changeBillStatus(bill.id, PaymentStatus.PAYED)
        }
        if (money == bill.value) {
            response = "Order was paid successfully!"
            billDao.changeBillStatus(bill.id, PaymentStatus.PAYED)
        }
        if (money < bill.value) {
            throw RuntimeException("Not enough money!")
        }
        return response
    }
}