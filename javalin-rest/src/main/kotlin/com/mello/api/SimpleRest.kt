package com.mello.api

import com.mello.dao.UserDao
import com.mello.entity.User
import io.javalin.ApiBuilder.*
import io.javalin.Javalin

/**
 * 简单的rest操作
 * Created by MelloChan on 2017/11/10.
 */
// val -> final var -> 可变数据类型
fun main(args: Array<String>) {
    val userDao = UserDao()

    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start() //端口-> 7000

    //路由映射
    app.routes {
        get("/users") { ctx ->
            ctx.json(userDao.users)
        }

        get("/users/:id") { ctx ->
            ctx.json(userDao.findById(ctx.param("id")!!.toInt())!!)
        }// !! 若为null则抛出异常 这里很明显id不为空,转为int型

        get("/users/email/:email") { ctx ->
            ctx.json(userDao.findByEmail(ctx.param("email")!!)!!)
        }

        post("users/create") { ctx ->
            val user = ctx.bodyAsClass(User::class.java)
            userDao.save(name = user.name, email = user.email)
            ctx.status(201)
        } // bodyAsClass -> 自动数据绑定

        patch("/users/update/:id") { ctx ->
            val user = ctx.bodyAsClass(User::class.java)
            userDao.updateUser(
                    id = ctx.param("id")!!.toInt(),
                    user = user
            )
            ctx.status(204)
        }

        delete("/users/delete/:id") { ctx ->
            userDao.delete(ctx.param("id")!!.toInt())
            ctx.status(204)
        }
    }
}