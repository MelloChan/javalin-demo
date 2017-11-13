# javalin-demo
һ���·�����web���.Ĭ��servlet����ΪJetty,һ��������Ƕ��ʽ����.      
�����ٷ��ĵ�,˳��ѧ��kotlin.  
����,����maven������    
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
## Hello Javalin
Javalin->java && kotlin,����ζ��,�����ʹ��java||kotlin���б��.   
������һ����������web���,Ŀ������������һϵ��REST API.  
��Ȼû��MVC�ĸ���,�����ṩ�˶�ģ������/WS/��̬�ļ������֧��.  
### Hello.kt  
```
	val app = Javalin.create().port(7000).start()
	app.get("/"){ctx->ctx.result("Hello World!")}

```  
## Simple REST API  
### ����Userʵ����  
```
	data class User(var name:String,var email:String,var id: Int)

```  
### ����UserDao(���Ҳ�����DB����)
```
class UserDao {
    val users = hashMapOf(
            0 to User("alice", "alice@aclice.kt", 0),
            1 to User("bob", "bob@bob.kt", 1),
            2 to User("carol", "carol@carol.kt", 2),
            3 to User("dave", "dave@dave.kt", 3)
    ) //����һ����ϣ��
    var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun save(name: String, email: String) {
        val id = lastId.incrementAndGet()
        users.put(id, User(name = name, email = email, id = id))
    }//�¼���һ��userʵ��

    fun findById(id: Int): User? {
        return users[id]
    }//����id�û�  :User? ->����userʵ��  ? �������ص�ʵ����Ϊnull

    fun findByEmail(email: String): User? {
        return users.values.find { it.email == email }
    }//ͨ��email�����û�  it -> this

    fun updateUser(id: Int, user: User) {
        users.put(id, User(name = user.name, email = user.email, id = id))
    }//����ʵ��

    fun delete(id: Int) {
        users.remove(id)
    }//�Ƴ�
}
```  
### ���� REST API -> SimpleRest.kt
```
fun main(args: Array<String>) {
    val userDao = UserDao()

    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start() //�˿�-> 7000

    //·��ӳ��
    app.routes {
        get("/users") { ctx ->
            ctx.json(userDao.users)
        }

        get("/users/:id") { ctx ->
            ctx.json(userDao.findById(ctx.param("id")!!.toInt())!!)
        }// !! ��Ϊnull���׳��쳣   

        get("/users/email/:email") { ctx ->
            ctx.json(userDao.findByEmail(ctx.param("email")!!)!!)
        }

        post("users/create") { ctx ->
            val user = ctx.bodyAsClass(User::class.java)
            userDao.save(name = user.name, email = user.email)
            ctx.status(201)
        } // bodyAsClass -> �Զ����ݰ�

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
### ���� SimpleRest.kt  
�ɹ����п���̨��ӡ������Ϣ   
```
...
[main] INFO io.javalin.embeddedserver.EmbeddedServer - Jetty is listening on: [http://localhost:7000]
[main] INFO io.javalin.Javalin - Javalin has started \o/

```
GET /users  
```
{
    "0":{
        "name":"alice",
        "email":"alice@aclice.kt",
        "id":0
    },
    "1":{
        "name":"bob",
        "email":"bob@bob.kt",
        "id":1
    },
    "2":{
        "name":"carol",
        "email":"carol@carol.kt",
        "id":2
    },
    "3":{
        "name":"dave",
        "email":"dave@dave.kt",
        "id":3
    }
}
```  
GET /users/0
```
{
    "name":"alice",
    "email":"alice@aclice.kt",
    "id":0
}
```
GET /users/email/alice@aclick.kt
```
{
    "name":"alice",
    "email":"alice@aclice.kt",
    "id":0
}
```
POST /users/create
```
{
	
	"name":"json",
	
	"email":"json@json.kt"

}
```
PATCH /users/update/4
```
{
	
	"name":"mello",
	
	"email":"mello@mello.kt"

}

```
DELETE /users/delete/5    

## Email-Sending
```
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-email</artifactId>
            <version>1.4</version>
        </dependency>
```
## Simple Chat
## secure rest  

