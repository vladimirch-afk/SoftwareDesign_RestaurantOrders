package hse.ru.vladch.service

import hse.ru.vladch.dao.OrderDao
import hse.ru.vladch.entities.DishEntity
import hse.ru.vladch.entities.OrderEntity
import hse.ru.vladch.enums.OrderStatus

class KitchenServiceImpl(
    private val orderDao: OrderDao,
    private val bankService: BankService,
) : KitchenService {
    private val cookers = mutableListOf<Cooker>()
    private val orderQueue = mutableListOf<OrderEntity>()
    private val allocatedOrders = mutableListOf<OrderEntity>()
    private var currIndex : Int? = null
    init {
        for (i in 1..1) {
            cookers.add(CookerImpl())
        }
        currIndex = orderDao.getOrdersNumber()
    }
    override fun createOrder(user: String, dishes: MutableList<DishEntity>) {
        if (orderQueue.find { it.user == user } != null ||
            allocatedOrders.find { it.user == user } != null) {
            throw RuntimeException("The previous order of this user was not finished!")
        }
        for (item in dishes) {
            if (dishes.count { it.name == item.name } > item.amount) {
                throw RuntimeException("Not enough dishes available!")
            }
        }
        for (item in dishes) {
            item.amount -= 1
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
        val order = orderQueue.maxBy { it -> it.dishes.sumOf { it -> it.price } }
        for (cooker in cookers) {
            if (cooker.getStatus()) {
                order.status = OrderStatus.COOKING
                cooker.startCooking(order, this)
                allocatedOrders.add(order)
                orderQueue.remove(order)
                break
            }
        }
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
        allocatedOrders.remove(order)
        orderQueue.remove(order)
    }

    override fun getOrderOfVisitor(user: String) : OrderEntity {
        val order = findOrder(user) ?: throw RuntimeException("No current orders")
        return order
    }

    override fun getOrderStatus(user: String) : OrderStatus{
        val order = findOrder(user) ?: throw RuntimeException("The user has no active orders!")
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
        bankService.createBill(order)
        allocateWork()
    }
}