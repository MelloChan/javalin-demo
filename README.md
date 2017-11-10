# javalin-demo
一个新发布的web框架.  
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
## hello javalin
javalin->java && kotlin,这意味着,你可以使用java||kotlin进行编码.   
本身是一个轻量级的web框架,目的是用来构建一系列REST API.  
虽然没有MVC的概念,不过提供了对模板引擎/WS/静态文件服务的支持.  
Hello.kt  
```
	val app = Javalin.create().port(7000).start()
	app.get("/"){ctx->ctx.result("Hello World!")}

```  
## simple rest  

