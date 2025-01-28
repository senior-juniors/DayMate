package com.example.daymate.Class_Schedule


import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStreamReader


//fun readJsonFromAssets(context: Context, filename:String): List<ClassData>? {
//    val moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//    val listType =Types.newParameterizedType(List::class.java,ClassData::class.java)
//    val jsonAdapter = moshi.adapter(listType)
//
//    //val jsonString=context.assets.open(filename).bufferedReader().use { it.readText() }
//    val jsonString = InputStreamReader(context.assets.open(filename)).use { it.readText() }
//
//    return jsonAdapter?.fromJson(jsonString)
//}
//
//fun filterClassDatabyInput(classData: List<ClassData>,semester:String,branch:String,section:String): ClassData?{
//    return classData.find { it.semester==semester && it.branch== branch && it.section==section }
//}