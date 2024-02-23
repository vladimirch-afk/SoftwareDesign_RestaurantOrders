package hse.ru.vladch.service

import hse.ru.vladch.dao.InMemoryOrderDao
import hse.ru.vladch.dao.OrderDao
import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.OrderStatus
import java.time.LocalDateTime

class KitchenServiceImpl(
    private val orderDao: OrderDao,
    private val bankService: BankService
) : KitchenService {
    private val cookers = mutableListOf<Cooker>()
    private val orderQueue = mutableListOf<OrderEntity>()
    private val allocatedOrders = mutableListOf<OrderEntity>()
    private var currIndex : Int? = null
    init {
        for (i in 1..10) {
            cookers.add(CookerImpl())
        }
        currIndex = orderDao.getOrdersNumber()
    }
    override fun createOrder(user: String, dishes: MutableList<DishEntity>) {
        if (orderQueue.find { it.user == user } != null ||
            allocatedOrders.find { it.user == user } != null) {
            throw RuntimeException("The previous order of this user was not finished!")
        }
        val creationTime = System.currentTimeMillis()
        val order = OrderEntity(currIndex!!,
            user, creationTime, dishes, OrderStatus.CREATED)
        orderQueue.add(order)
        allocateWork()
    }

    private fun allocateWork() {
        if (orderQueue.isEmpty()) {
            return
        }
        val order = orderQueue[0]
        for (cooker in cookers) {
            if (cooker.getStatus()) {
                order.status = OrderStatus.COOKING
                cooker.startCooking(order, this)
                allocatedOrders.add(order)
                orderQueue.removeAt(0)
                break
            }
        }
    }

    private fun calculateCompletionTime(list : MutableList<DishEntity>) : Long {
        var time : Long = 0
        for (item in list) {
            time += item.timeRequirement
        }
        return time
    }

    override fun addDishToOrder(user: String, dish: DishEntity) {
        var order = orderQueue.find { it.user == user }
        if (order == null) {
            order = allocatedOrders.find { it.user == user }
        }
        if (order == null) {
            throw RuntimeException("There is no active orders for this user!")
        }
        for (cooker in cookers) {
            if (cooker.getClientName() == user) {
                cooker.addNewDish(dish)
                break
            }
        }
    }

    override fun cancelOrder(user: String) {
        var order = orderQueue.find { it.user == user }
        if (order == null) {
            order = allocatedOrders.find { it.user == user }
        }
        if (order == null) {
            throw RuntimeException("There is no active orders for this user!")
        }
        for (cooker in cookers) {
            if (cooker.getClientName() == user) {
                cooker.cancelProcess()
                break
            }
        }
    }

    override fun getOrderOfVisitor(user: String) : OrderEntity {
        val order = findOrder(user) ?: throw RuntimeException("No current orders")
        return order
    }

    override fun getOrderStatus(user: String) : OrderStatus{
        val order = findOrder(user)
        if (order == null) {
            throw RuntimeException("The user has no active orders!")
        }
        return order.status
    }

    private fun findOrder(user : String) : OrderEntity? {
        for (item in allocatedOrders) {
            if (item.user == user) {
                return item
            }
        }
        for (item in orderQueue) {
            if (item.user == user) {
                return item
            }
        }
        return null
    }

    override fun changeOrderStatus(order: OrderEntity) {
        order.status = OrderStatus.READY
        orderDao.addOrder(order)
        allocatedOrders.removeAt(allocatedOrders.indexOf(order))
        allocateWork()
    }
}