package tech.jhavidit.coroutinesdemo.room


interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAll(users: List<User>)

}