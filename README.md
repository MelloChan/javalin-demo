# javalin-demo
һ���·�����web���.  
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
## hello javalin
javalin->java && kotlin,����ζ��,�����ʹ��java||kotlin���б���.   
������һ����������web���,Ŀ������������һϵ��REST API.  
��Ȼû��MVC�ĸ���,�����ṩ�˶�ģ������/WS/��̬�ļ������֧��.  
Hello.kt  
```
	val app = Javalin.create().port(7000).start()
	app.get("/"){ctx->ctx.result("Hello World!")}

```  
## simple rest  

