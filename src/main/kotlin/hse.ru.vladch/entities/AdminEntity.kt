package hse.ru.vladch.entities

import hse.ru.vladch.enums.AccountType

class AdminEntity(
    login: String,
    password: String,
    type : AccountType
) : UserEntity(login, password, type) {

}
