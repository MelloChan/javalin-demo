package com.mello

import com.mello.controller.UserController
import com.mello.secure.ApiRole
import io.javalin.Javalin
import com.mello.secure.Auth
import io.javalin.ApiBuilder.*
import io.javalin.security.Role.roles

/**
 * Created by MelloChan on 2017/11/13.
 */
fun main(array: Array<String>) {
    val app = Javalin.create().apply {
        port(7000)
        accessManager(Auth::accessManager)
    }.start()
    app.routes {
        get("/", { ctx -> ctx.redirect("/users") })
        path("users") {
            get(UserController::getAllUserIds, roles(ApiRole.ANYONE))
            post(UserController::createUser, roles(ApiRole.USER_WRITE))
            path(":user-id") {
                get(UserController::getUser, roles(ApiRole.USER_READ))
                patch(UserController::updateUser, roles(ApiRole.USER_WRITE))
                delete(UserController::deleteUser, roles(ApiRole.USER_WRITE))
            }
        }
    }
}