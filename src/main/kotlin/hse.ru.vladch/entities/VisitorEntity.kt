package hse.ru.vladch.entities

import hse.ru.vladch.enums.AccountType

class VisitorEntity(
                    login: String,
                    password: String,
                    type : AccountType
) : UserEntity(login, password, type) {

}
