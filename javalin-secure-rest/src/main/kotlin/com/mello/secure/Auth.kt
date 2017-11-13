package com.mello.secure

import io.javalin.Context
import io.javalin.Handler
import io.javalin.security.Role

/**
 * 权限控制
 * Created by MelloChan on 2017/11/13.
 */
enum class ApiRole: Role {ANYONE,USER_READ,USER_WRITE}
object Auth{
    fun accessManager(handler:Handler,ctx:Context,permittedRoles:List<Role>){
        when{
            permittedRoles.contains(ApiRole.ANYONE)->handler.handle(ctx)
            ctx.userRoles.any{it in permittedRoles}->handler.handle(ctx)
            else -> ctx.status(401).json("Unauthorized")
        }
    }

    private val Context.userRoles:List<ApiRole>
    get() = this.basicAuthCredentials()?.let { (username,password)->
        userRoleMap[Pair(username,password)]?: listOf()
    }?: listOf()

    private val userRoleMap= hashMapOf(
            Pair("alice","weak-password")to listOf(ApiRole.USER_READ),
            Pair("bob","better-password")to listOf(ApiRole.USER_WRITE)
    )
}
