package hse.ru.vladch.service

import hse.ru.vladch.dao.InMemoryOrderDao
import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.OrderStatus
import java.time.LocalDateTime

class KitchenServiceImpl() : KitchenService {
    val orderDao = InMemoryOrderDao()
    private val cookers = mutableListOf<Cooker>()
    private val orderQueue = mutableListOf<Pair<Long, OrderEntity>>()
    private val allocatedOrders = mutableListOf<OrderEntity>()
    private var currIndex : Int? = null
    init {
        for (i in 1..10) {
            cookers.add(CookerImpl())
        }
        currIndex = orderDao.getOrdersNumber()
    }
    override fun createOrder(user: String, dishes: MutableList<DishEntity>) {
        if (orderQueue.find { it.second.user == user } != null ||
            allocatedOrders.find { it.user == user } != null) {
            throw RuntimeException("The previous order of this user was not finished!")
        }
        val creationTime = (System.currentTimeMillis() / 1000)
        val order = OrderEntity(currIndex!!,
            user, creationTime, dishes, OrderStatus.CREATED)
        orderQueue.add(Pair(calculateCompletionTime(dishes), order))
        orderQueue.sortBy { it.first }
        allocateWork()
    }

    private fun allocateWork() {
        val order = orderQueue[0].second
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
        var order = orderQueue.find { it.second.user == user }?.second
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
        var order = orderQueue.find { it.second.user == user }?.second
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

    override fun getOrdersOfVisitor(user: String) : MutableList<OrderEntity> {
        val orders = mutableListOf<OrderEntity>()
        val order = findOrder(user)
        if (order != null) {
            orders.add(order)
        }
        return orders
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
            if (item.second.user == user) {
                return item.second
            }
        }
        return null
    }

    override fun changeOrderStatus(order: OrderEntity) {
        order.status = OrderStatus.READY
        orderDao.addOrder(order)
    }
}