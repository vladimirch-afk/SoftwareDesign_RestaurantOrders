package hse.ru.vladch.dao

import hse.ru.vladch.entities.BillEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.PaymentStatus

interface BillDao {
    // Создать счет
    fun createBill(order : OrderEntity) : BillEntity
    // Получить счета пользователя
    fun getUserBills(user : String) : MutableList<BillEntity>
    // Получить статус счета по ID
    fun getBillStatus(id : Int) : PaymentStatus
    // Изменить статус счета на новый
    fun changeBillStatus(id : Int, newStatus : PaymentStatus)
    // Сохранить данные о счете в файл
    fun saveData()
    // Загрузить данные о счете из файла
    fun loadData()
}
