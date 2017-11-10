package com.mello.entity

/**
 * 用户实体
 * Created by MelloChan on 2017/11/10.
 */
//data -> 自动创建 toString equals hashcode get/set 方法
data class User(var name:String,var email:String,var id: Int)
fun main(args:Array<String>){
    val alice=User("alice","alice@alice.kt",0)
    val aliceNewEmail=alice.copy(email = "alice@bob.kt") //alice为基础修改email copy新的user实例
    val(name,email,id)=aliceNewEmail  //按列表顺序赋值
    println("$name 's new email is $email by $id")
}