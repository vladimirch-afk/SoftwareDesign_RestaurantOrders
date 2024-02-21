package hse.ru.vladch.entities

import hse.ru.vladch.enums.OrderStatus

class OrderEntity(
    val id : Int,
    val user : String,
    val creationTime : Long,
    val dishes : MutableList<DishEntity>,
    var status : OrderStatus
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (item in dishes) {
            stringBuilder.append(item.name)
            stringBuilder.append(", ")
        }
        return "ID: $id, User: $user, Status: $status, ${stringBuilder.toString()}"
    }
}