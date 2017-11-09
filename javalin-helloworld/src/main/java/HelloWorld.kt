import io.javalin.Javalin

/**
 * Hello World !
 * Created by MelloChan on 2017/11/9.
 */

fun main(args:Array<String>){
    val app= Javalin.start(8888);
    app.get("/"){ctx->ctx.result("Hello World!")}
}
