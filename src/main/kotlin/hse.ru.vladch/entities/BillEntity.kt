package hse.ru.vladch.entities

import hse.ru.vladch.enums.PaymentStatus

data class BillEntity(
    val id : Int,
    val user : String,
    val value : Int,
    var status : PaymentStatus
)
