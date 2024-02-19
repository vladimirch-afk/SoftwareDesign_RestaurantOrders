package hse.ru.vladch.entities

class OrderEntity(
    val id : Int,
    val creationTime : Long,
    val dishes : MutableList<DishEntity>
) {
}