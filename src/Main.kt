
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlin.random.Random
import kotlin.system.measureTimeMillis


suspend fun main()  {

    val passwordUsers = mutableMapOf<String, String>( )
    val password = mutableListOf<String>()
    val users = mutableListOf<String>()

    println("Введите кол-во пользователей")
    val length = readln().toInt()
    println("Введите первый символ пароля")
    val input = readln()

    val time = measureTimeMillis {
        withContext(newSingleThreadContext("product_thread_context")) {

            getIdFlow(length).collect {
                users.add(it)
            }

            getPasswordFlow(input, length).collect {
                password.add(it)
            }

            for (i in 0..<length) {
                passwordUsers[users[i]] = password[i]
            }
        }
    }
    passwordUsers.forEach { println("Пользователь ${it.key}: ${it.value}") }
    println("Затраченое время $time мс")

}

fun getIdFlow(length: Int) = getListId(length).asFlow()

fun getPasswordFlow(input: String, length: Int)= getListOfPassword(input, length).asFlow()

fun createPassword(input: String):String{
    val random = Random
    val symbol = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray()
    var password = input


    for (i in 1 .. 5) {
        password += if (i % 2 == 0) symbol[random.nextInt(symbol.size)].uppercaseChar()
        else symbol[random.nextInt(symbol.size)]

    }
    return password
}

fun getListOfPassword(input: String, length: Int): List<String>{
    val passwords = mutableListOf<String>()

    for (i in 0..< length){
        passwords.add(createPassword(input))
    }
   return passwords.toList()
}

fun getListId(length: Int):MutableList<String>{
    val id = mutableListOf<String>()
    for (i in 1.. length){
        var zero = ""
        for (j in 0..5 - i.toString().length){
            zero += 0
        }
        id.add(zero + i.toString())
    }

    return id
}