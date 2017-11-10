package com.mello.dao

import com.mello.entity.User
import java.util.concurrent.atomic.AtomicInteger

/**
 * 用户dao
 * Created by MelloChan on 2017/11/10.
 */
class UserDao {
    val users = hashMapOf(
            0 to User("alice", "alice@aclice.kt", 0),
            1 to User("bob", "bob@bob.kt", 1),
            2 to User("carol", "carol@carol.kt", 2),
            3 to User("dave", "dave@dave.kt", 3)
    ) //创建一个哈希表
    var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun save(name: String, email: String) {
        val id = lastId.incrementAndGet()
        users.put(id, User(name = name, email = email, id = id))
    }//新加入一个user实例

    fun findById(id: Int): User? {
        return users[id]
    }//查找id用户  :User? ->返回user实例  ? 表明返回的实例可为null

    fun findByEmail(email: String): User? {
        return users.values.find { it.email == email }
    }//通过email查找用户  it -> this

    fun updateUser(id: Int, user: User) {
        users.put(id, User(name = user.name, email = user.email, id = id))
    }//更新实例

    fun delete(id: Int) {
        users.remove(id)
    }//移除
}