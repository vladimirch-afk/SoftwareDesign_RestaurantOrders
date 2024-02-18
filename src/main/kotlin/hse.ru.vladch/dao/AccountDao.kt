package hse.ru.vladch.dao

import hse.ru.vladch.entities.UserEntity
import hse.ru.vladch.enums.AccountType

interface AccountDao {
    fun createAccount(type : AccountType, login : String, password : String)
    fun deleteAccount(login : String, password : String)
    fun findAccount(login: String) : UserEntity?
    fun authorizeUser(login: String, password: String) : UserEntity
}