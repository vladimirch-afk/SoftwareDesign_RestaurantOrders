package hse.ru.vladch.dao

import hse.ru.vladch.entities.BillEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.PaymentStatus
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.io.File
import kotlin.io.path.Path

class BillDaoImpl : BillDao {
    private var bills = mutableListOf<BillEntity>()
    override fun createBill(order: OrderEntity): BillEntity {
        try {
            val bill = BillEntity(bills.size, order.user,
                calculateSum(order), PaymentStatus.NOT_PAYED)
            bills.add(bill)
            return bill
        } catch (e : Exception) {
            throw RuntimeException("Unable to create a bill!")
        }
    }

    private fun calculateSum(order : OrderEntity) : Int {
        return order.dishes.sumOf { it.price }
    }

    override fun getUserBills(user: String): MutableList<BillEntity> {
        val tmp = mutableListOf<BillEntity>()
        for (item in bills) {
            if (item.user == user) {
                tmp.add(item)
            }
        }
        return tmp
    }

    override fun getBillStatus(id: Int): PaymentStatus {
        try {
            return bills.find { it.id == id }!!.status
        } catch (e : Exception) {
            throw RuntimeException("Bill with this ID does not exist")
        }
    }

    override fun changeBillStatus(id: Int, newStatus: PaymentStatus) {
        try {
            val bill = bills.find { it.id == id }
            bill!!.status = newStatus
        } catch (e : Exception) {
            throw RuntimeException("Bill with this ID does not exist")
        }
    }

    override fun loadData() {
        val directory = "src/main/resources"
        val fileName = "bills.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        bills = objectMapper.readValue<MutableList<BillEntity>>(file.readText())
    }

    override fun saveData() {
        val directory = "src/main/resources"
        val fileName = "bills.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        objectMapper.writeValue(file, bills)
    }
}