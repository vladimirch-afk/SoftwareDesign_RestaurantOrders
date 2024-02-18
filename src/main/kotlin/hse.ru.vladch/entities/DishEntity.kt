package hse.ru.vladch.entities

data class DishEntity(var name : String,
                      var price : Int,
                      var timeRequirement : Long,
                      var amount : Int,
                      val reviews : MutableList<ReviewEntity> = mutableListOf<ReviewEntity>()
) {
    override fun toString(): String {
        return "Name: $name, Price: $price, Time complexity: $timeRequirement, Amount: $amount"
    }
}