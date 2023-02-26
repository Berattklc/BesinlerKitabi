package com.beratklc.besinlerkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.beratklc.besinlerkitabi.model.Besin


@Database(entities = [Besin ::class], version = 1)
abstract class BesinDatabase : RoomDatabase() {

    abstract fun besinDao() : BesinDAO

    //Singleton

    companion object {
        //Volatile diğer thread lere görünür kılmak
        @Volatile private var instance : BesinDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }


        private fun databaseOlustur(context: Context) = Room.databaseBuilder(context.applicationContext,
            BesinDatabase :: class.java,"besindatabase").build()

    }



}
/*
Bu kod bloğu, bir Companion Object içinde yer alan bir Factory method'unu (yani invoke metodunu) göstermektedir. Bu örnek, Kotlin'de singleton pattern kullanarak bir Room veritabanı oluşturmak için bir örnek sunmaktadır.

Bu örnekte BesinDatabase adında bir sınıf oluşturulur ve companion object içinde bir factory method'u olan invoke tanımlanır. Bu methodun görevi, BesinDatabase sınıfının yalnızca bir örneği oluşturmaktır.

Bu factory method, ilk olarak instance adında bir değişkeni tanımlar ve bu değişkeni @Volatile özellikle işaretler. Bu işaret, değişkenin diğer thread'ler tarafından da görünür kılınmasını sağlar.

Daha sonra, synchronized anahtar kelimesi kullanılarak, bu methodun aynı anda yalnızca bir thread tarafından çalıştırılmasını sağlamak için bir lock objesi tanımlanır.

invoke methodu içinde, instance değişkeni null ise, databaseOlustur adlı bir method çağrılır. Bu method, bir Room veritabanı oluşturur ve bu veritabanını geri döndürür. also fonksiyonu kullanılarak, instance değişkenine bu oluşturulan veritabanı örneği atanır.

invoke methodu, instance değişkeninin null olmadığı bir durumda, doğrudan bu instance örneğini geri döndürür. Bu sayede, her seferinde yeni bir veritabanı oluşturma maliyetinden kaçınılır ve yalnızca bir örnek oluşturulur.
 */