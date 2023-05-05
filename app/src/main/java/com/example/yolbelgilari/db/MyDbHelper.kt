package com.example.yolbelgilari.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.yolbelgilari.models.MySign

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbInterface {
    companion object {
        const val DB_NAME = "db_name"
        const val DB_VERSION = 1
        const val TABLE = "my_sign_table"
        const val ID = "id"
        const val SIGN_NAME = "sign_name"
        const val SIGN_ABOUT = "sign_about"
        const val SIGN_TYPE = "sign_type"
        const val LIKED = "sign_liked"
        const val PHOTO_PATH = "sign_path"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val signCustomer =
            "create table $TABLE($ID integer not null primary key autoincrement unique,$SIGN_NAME text not null,$SIGN_ABOUT text not null,$SIGN_TYPE integer not null, $LIKED integer not null, $PHOTO_PATH text not null)"
        p0?.execSQL(signCustomer)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    override fun addSign(mySign: MySign) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SIGN_NAME, mySign.name)
        contentValues.put(SIGN_ABOUT, mySign.about)
        contentValues.put(SIGN_TYPE, mySign.type)
        contentValues.put(LIKED, mySign.liked)
        contentValues.put(PHOTO_PATH, mySign.photoPath)
        database.insert(TABLE, null, contentValues)
        database.close()
    }

    override fun getAllSign(): ArrayList<MySign> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("select * from $TABLE", null)
        val list = ArrayList<MySign>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    MySign(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    override fun deleteSign(mySign: MySign) {
        val database = this.writableDatabase
        database.delete(TABLE, "id=?", arrayOf(mySign.id.toString()))
        database.close()
    }

    override fun editSign(mySign: MySign): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SIGN_NAME, mySign.name)
        contentValues.put(SIGN_ABOUT, mySign.about)
        contentValues.put(SIGN_TYPE, mySign.type)
        contentValues.put(LIKED, mySign.liked)
        contentValues.put(PHOTO_PATH, mySign.photoPath)

        return database.update(TABLE, contentValues, "$ID=?", arrayOf(mySign.id.toString()))
    }

    override fun getLikedSigns(): ArrayList<MySign> {
        val database = this.readableDatabase
        val cursor = database.rawQuery("select * from $TABLE", null)
        val list = ArrayList<MySign>()
        var mySign: MySign?
        if (cursor.moveToFirst()) {
            do {
                mySign = MySign(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5)
                )
                if (mySign.liked == 1) {
                    list.add(mySign)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}