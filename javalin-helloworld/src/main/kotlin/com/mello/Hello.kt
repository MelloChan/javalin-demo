package com.mello

import io.javalin.Javalin

fun main(args: Array<String>) {
    val app = Javalin.create().port(7000).start()
    app.get("/") { ctx -> ctx.result("Hello World!") }
}

