package com.example.submissionbelajarcompose.utils

import android.text.TextUtils
import android.util.Log
import java.lang.reflect.Method
import java.lang.reflect.Modifier

fun findMethodByReflection(classMethod: Class<*>?, methodName: String): Method? {
    Log.i("MardiMaantap", "Class: halio")
    return try {
        if (!TextUtils.isEmpty(methodName)) {
            classMethod?.let { clazz ->
                clazz.methods.find { it.name.equals(methodName) && Modifier.isStatic(it.modifiers) }
            } ?: run {
                null
            }
        } else {
            null
        }
    } catch (e: Throwable) {
        null
    }
}

fun loadClassByReflection(className: String): Class<*>? {
    return try {
        val classLoader = ::loadClassByReflection.javaClass.classLoader
        Log.i("MardiMaantap", "class loader $classLoader")
        classLoader?.loadClass(className)
    } catch (e: Throwable) {
        Log.i("MardiMaantap", "class loader $e")
        null
    }
}

fun invokeMethod(method: Method, obj: Any, vararg args: Any): Boolean {
    return try {
        method.invoke(obj, *(args))
        true
    } catch (e: Throwable) {
        false
    }
}