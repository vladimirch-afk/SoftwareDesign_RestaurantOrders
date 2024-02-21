package hse.ru.vladch.entities

import hse.ru.vladch.enums.OrderStatus

class OrderEntity(
    val id : Int,
    val user : String,
    val creationTime : Long,
    val dishes : MutableList<DishEntity>,
    var status : OrderStatus
) {
}