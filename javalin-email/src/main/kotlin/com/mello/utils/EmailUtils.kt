package com.mello.utils

import io.javalin.Javalin
import org.apache.commons.mail.DefaultAuthenticator
import org.apache.commons.mail.SimpleEmail

/**
 * Send Email Demo
 * Created by MelloChan on 2017/11/11.
 */
fun main(array: Array<String>) {
    sendEmail()
}

fun sendEmail() {
    val app = Javalin.start(7000)

    app.get("/") { ctx ->
        ctx.html("""
            <form action="/contact-us" method="post">
                 <input name="subject" placeholder="Subject">
                  <br>
                 <textarea name="message" placeholder="Your message ..."></textarea>
                 <br>
                <button>submit</button>
            </form>
         """.trimIndent())
    }

    app.post("/contact-us") { ctx ->
        SimpleEmail().apply {
            hostName = "stmp.163.com"
            setAuthenticator(DefaultAuthenticator("YOUR_EMAIL", "YOUR_PASSWORD"))
            isSSLOnConnect = true
            setFrom("YOUR_EMAIL")
            subject = ctx.formParam("subject")
            setMsg(ctx.formParam("message"))
            addTo("RE_EMAIL")
        }.send()
        ctx.redirect("/contact-success")
    }

    app.get("/contact-success") { ctx ->
        ctx.html("SUCCESS")
    }
}