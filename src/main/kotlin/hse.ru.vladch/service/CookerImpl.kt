package hse.ru.vladch.service

import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import kotlin.concurrent.thread

class CookerImpl : Cooker {
    private var isFree = true
    private var order : OrderEntity? = null
    private var kitchen : KitchenService? = null
    private var process : Thread? = null
    private var start : Long? = null
    private var dishNum = 0
    override fun startCooking(order: OrderEntity, kitchen: KitchenService) {
        this.order = order
        this.kitchen = kitchen
        //start = order.creationTime
        dishNum = order.dishes.size
        isFree = false
        process = Thread {
            cookDishes(this)
        }
        process?.start()
    }

    override fun addNewDish(dish: DishEntity) {
        order?.dishes?.add(dish)
        dishNum += 1
    }

    override fun cancelProcess() {
        try {
            process?.interrupt()
            process = null
        } catch (e : Exception) {}
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

    override fun notifyKitchen() {
        process?.interrupt()
        process = null
        isFree = true
        kitchen!!.changeOrderStatus(order!!)
    }

    private fun cookDishes(ctx : Cooker) {
        var i = 0
        while(i < dishNum) {
            Thread.sleep(order!!.dishes[i].timeRequirement * 1000)
            ++i
        }
        ctx.notifyKitchen()
    }
}