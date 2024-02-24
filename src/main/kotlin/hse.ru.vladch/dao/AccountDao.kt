package hse.ru.vladch.dao

import hse.ru.vladch.entities.UserEntity
import hse.ru.vladch.enums.AccountType

interface AccountDao {
    // Создать аккаунт пользователя
    fun createAccount(type : AccountType, login : String, password : String)
    // Удалить аккаунт пользователя
    fun deleteAccount(login : String, password : String)
    // Найти аккаунт
    fun findAccount(login: String) : UserEntity?
    // Авторизовать пользователя
    fun authorizeUser(login: String, password: String) : UserEntity
    // Сохранить данные об аккаунтах в файл
    fun saveData()
    // Загрузить данные об аккаунтах из файла
    fun loadData()
}