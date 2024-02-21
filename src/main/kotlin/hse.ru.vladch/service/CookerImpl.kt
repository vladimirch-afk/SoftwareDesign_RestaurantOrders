package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity

class CookerImpl : Cooker {
    private var isFree = true
    private var order : OrderEntity? = null
    private var kitchen : KitchenService? = null
    private var process : Thread? = null
    private var start : Long? = null
    override fun startCooking(order: OrderEntity, kitchen: KitchenService) {
        this.order = order
        this.kitchen = kitchen
        start = order.creationTime
        process = Thread {
            cookDishes()
        }
    }

    override fun addNewDish(dish: DishEntity) {
        order?.dishes?.add(dish)
    }

    override fun getStatus(): Boolean {
        return isFree
    }

    override fun getClientName(): String {
        if (order == null) {
            return ""
        }
        return order!!.user
    }

    override fun getOrder(): OrderEntity? {
        return order
    }

    private fun cookDishes() {

    }
}