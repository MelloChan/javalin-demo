package com.mello.websocket

import io.javalin.Javalin
import j2html.TagCreator.*
import org.eclipse.jetty.websocket.api.Session
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by MelloChan on 2017/11/12.
 */
private val userUsernameMap = ConcurrentHashMap<Session, String>()
private var nextUserNumber = 1
fun main(args: Array<String>) {
    Javalin.create().apply {
        port(7000)
        enableStaticFiles("/public")
        ws("/chat") { ws ->
            ws.onConnect { session ->
                val username = "User " + nextUserNumber++
                userUsernameMap.put(session, username)
                broadcastMessage("Server", username + " join the char")
            }
            ws.onClose { session, status, message ->
                val username = userUsernameMap[session]
                userUsernameMap.remove(session)
                broadcastMessage("Server", username + " left the char")
            }
            ws.onMessage { session, message ->
                broadcastMessage(userUsernameMap[session]!!, message)
            }
        }.start()
    }
}

fun broadcastMessage(sender: String, message: String) {
    userUsernameMap.keys.filter { it.isOpen }.forEach { session ->
        session.remote.sendString(
                JSONObject()
                        .put("userMessage", createHtmlMessageFromSender(sender, message))
                        .put("userlist", userUsernameMap.values).toString()
        )
    }
}

private fun createHtmlMessageFromSender(sender: String, message: String): String {
    return article(
            b(sender + " says:"),
            span(attrs(".timestamp"),SimpleDateFormat("HH:mm:ss").format(Date())),
            p(message)
    ).render()
}