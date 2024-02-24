package hse.ru.vladch.dao

import hse.ru.vladch.entities.UserEntity
import hse.ru.vladch.enums.AccountType
import hse.ru.vladch.patternclasses.AccountFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import hse.ru.vladch.entities.BillEntity
import java.io.File
import kotlin.io.path.Path

class InMemoryAccountDao : AccountDao {
    private var accounts = mutableListOf<UserEntity>()
    override fun createAccount(type: AccountType, login: String, password: String) {
        if (login.isEmpty()) {
            throw RuntimeException("Login cannot be empty!")
        }
        if (password.isEmpty()) {
            throw RuntimeException("Password cannot be empty!")
        }
        if (findAccount(login) != null) {
            throw RuntimeException("Account already exists! Choose another login")
        }
        val accountFactory = AccountFactory()
        accounts.add(accountFactory.createAccount(type, login, password))
    }

    override fun deleteAccount(login: String, password: String) {
        val acc = findAccount(login) ?:
                    throw RuntimeException("Account does not exists or was already deleted!")
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

    override fun loadData() {
        val directory = "src/main/resources"
        val fileName = "accounts.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        accounts = objectMapper.readValue<MutableList<UserEntity>>(file.readText())
    }

    override fun saveData() {
        val directory = "src/main/resources"
        val fileName = "accounts.json"
        File(directory).mkdirs()
        val file = Path(directory, fileName).toFile()
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerKotlinModule()
        objectMapper.writeValue(file, accounts)
    }
}