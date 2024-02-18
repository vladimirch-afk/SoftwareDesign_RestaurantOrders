package hse.ru.vladch.service

import hse.ru.vladch.enums.AccountType
import hse.ru.vladch.entities.UserEntity

class AccountServiceImpl : AccountService {
    override fun createAccount(type: AccountType, login: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAccount(login: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun findAccount(login: String): UserEntity {
        TODO("Not yet implemented")
    }

    override fun authorizeUser(login: String, password: String) {
        TODO("Not yet implemented")
    }
}