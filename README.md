# javalin-demo
一个新发布的web框架.默认servlet容器为Jetty,一个轻量级嵌入式容器.      
借助官方文档,顺带学下kotlin.  
首先,引入maven依赖包    
```
	<dependency>
            <groupId>io.javalin</groupId>
            <artifactId>javalin</artifactId>
            <version>${javalin.version}</version>
        </dependency>
 	<dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>1.7.25</version>
        </dependency>

```      
## hello Javalin
Javalin->java && kotlin,这意味着,你可以使用java||kotlin进行编码.   
本身是一个轻量级的web框架,目的是用来构建一系列REST API.  
虽然没有MVC的概念,不过提供了对模板引擎/WS/静态文件服务的支持.  
### Hello.kt  
```
	val app = Javalin.create().port(7000).start()
	app.get("/"){ctx->ctx.result("Hello World!")}

```  
## simple REST API  
### 创建User实体类  
```
	data class User(var name:String,var email:String,var id: Int)

```  
### 创建UserDao(暂且不引入DB操作)
```
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
```  
### 创建 REST API -> SimpleRest.kt
```
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
```
### 运行 SimpleRest.kt  
成功运行控制台打印如下信息   
```
...
[main] INFO io.javalin.embeddedserver.EmbeddedServer - Jetty is listening on: [http://localhost:7000]
[main] INFO io.javalin.Javalin - Javalin has started \o/

```



