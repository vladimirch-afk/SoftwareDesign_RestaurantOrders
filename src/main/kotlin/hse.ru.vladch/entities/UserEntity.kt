package hse.ru.vladch.entities

import hse.ru.vladch.enums.AccountType

open class UserEntity(
    var login: String,
    var password: String,
    var type : AccountType
)