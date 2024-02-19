package hse.ru.vladch.entities

import hse.ru.vladch.enums.AccountType

abstract class UserEntity(var login: String,
                          var password: String,
                          var type : AccountType) {
}