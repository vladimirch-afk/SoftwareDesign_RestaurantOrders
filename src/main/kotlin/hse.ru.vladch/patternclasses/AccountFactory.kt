package hse.ru.vladch.patternclasses

import hse.ru.vladch.entities.AdminEntity
import hse.ru.vladch.entities.UserEntity
import hse.ru.vladch.entities.VisitorEntity
import hse.ru.vladch.enums.AccountType

class AccountFactory {
    fun createAccount(type : AccountType, login : String, password : String) : UserEntity {
        return when(type) {
            AccountType.ADMINISTRATOR -> {
                AdminEntity(login, password, type)
            }

            AccountType.CLIENT -> {
                VisitorEntity(login, password, type)
            }
        }
    }
}