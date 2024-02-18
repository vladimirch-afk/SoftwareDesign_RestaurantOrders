package hse.ru.vladch.dao

import hse.ru.vladch.entities.UserEntity
import hse.ru.vladch.enums.AccountType
import hse.ru.vladch.patternclasses.AccountFactory

class InMemoryAccountDao : AccountDao {
    private val accounts = mutableListOf<UserEntity>()
    override fun createAccount(type: AccountType, login: String, password: String) {
        if (findAccount(login) != null) {
            throw RuntimeException("Account already exists! Choose another login")
        }
        if (login.isEmpty()) {
            throw RuntimeException("Login cannot be empty!")
        }
        if (password.isEmpty()) {
            throw RuntimeException("Password cannot be empty!")
        }
        val accountFactory = AccountFactory()
        accounts.add(accountFactory.createAccount(type, login, password))
    }

    override fun deleteAccount(login: String, password: String) {
        val acc = findAccount(login) ?:
                    throw RuntimeException("Account does not exists or already deleted!")
        accounts.remove(acc)
    }

    override fun findAccount(login: String) : UserEntity? {
        return accounts.find { it.login == login }
    }

    override fun authorizeUser(login: String, password: String): UserEntity {
        val acc = findAccount(login) ?: throw RuntimeException("The user does not exists!")
        if (acc.password != password) {
            throw RuntimeException("Incorrect password!")
        }
        return acc
    }
}