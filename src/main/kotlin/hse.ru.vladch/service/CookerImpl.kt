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
    private var isInterrupted = false
    override fun startCooking(order: OrderEntity, kitchen: KitchenService) {
        this.order = order
        this.kitchen = kitchen
        //start = order.creationTime
        dishNum = order.dishes.size
        isFree = false
        isInterrupted = false
        process?.interrupt()
        process = null
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
        isInterrupted = true
        process = null
        isFree = true
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
        try {
            process?.interrupt()
        } catch (e : Exception) {}
        process = null
        isFree = true
        kitchen!!.changeOrderStatus(order!!)
    }

    private fun cookDishes(ctx : Cooker) {
        var i = 0
        while(i < dishNum && !isInterrupted) {
            Thread.sleep(order!!.dishes[i].timeRequirement * 1000)
            ++i
        }
        if (!isInterrupted) {
            ctx.notifyKitchen()
        }
    }
}