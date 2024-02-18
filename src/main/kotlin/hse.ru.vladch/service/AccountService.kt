package hse.ru.vladch.service

import hse.ru.vladch.enums.AccountType
import hse.ru.vladch.entities.UserEntity

interface AccountService {
    fun createAccount(type : AccountType, login : String, password : String)
    fun deleteAccount(login : String, password : String)
    fun findAccount(login: String) : UserEntity
    fun authorizeUser(login : String, password : String)
}