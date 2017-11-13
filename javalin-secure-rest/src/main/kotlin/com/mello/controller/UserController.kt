package com.mello.controller

import java.util.*
import io.javalin.Context


/**
 * 控制器
 * Created by MelloChan on 2017/11/13.
 */
object UserController {
    private data class User(val name: String, val email: String)

    private val users = hashMapOf(
            randomId() to User(name = "Alice", email = "alice@alice.kt"),
            randomId() to User(name = "Bob", email = "bob@bob.kt"),
            randomId() to User(name = "Carol", email = "carol@carol.kt"),
            randomId() to User(name = "Dave", email = "dave@dave.kt")
    )

    fun getAllUserIds(ctx: Context) {
        ctx.json(users.keys)
    }

    fun createUser(ctx: Context) {
        users[randomId()] = ctx.bodyAsClass(User::class.java)
    }

    fun getUser(ctx: Context) {
        ctx.json(users[ctx.param(":user-id")!!]!!)
    }

    fun updateUser(ctx: Context) {
        users[ctx.param(":user-id")!!] =
                ctx.bodyAsClass(User::class.java)
    }

    fun deleteUser(ctx: Context) {
        users.remove(ctx.param(":user-id")!!)
    }

    private fun randomId() = UUID.randomUUID().toString()
    //查看编译后的class  emm 还算不错的语法糖


}