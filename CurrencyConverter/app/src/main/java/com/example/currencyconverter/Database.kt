package com.example.currencyconverter

import android.database.sqlite.SQLiteDatabase
class Database(var currency1 : String,
               var currency2: String,
               var rate : String,
               var date : String){



    fun saveData(database: SQLiteDatabase){


        database!!.execSQL("CREATE TABLE IF NOT EXISTS excange(currency1 VARCHAR, currency2 VARCHAR,value VARCHAR,date VARCHAR )")

        val sqlString = "INSERT INTO excange(currency1,currency2,value,date) VALUES (?,?,?,?)"
        val statment = database.compileStatement(sqlString)
        statment.bindString(1,currency1)
        statment.bindString(2,currency2)
        statment.bindString(3,rate)
        statment.bindString(4,date)
        statment.execute()

        database.close()

    }

    fun getData(database: SQLiteDatabase) : Set<String>{
        var set = mutableSetOf<String>()

        var cursor = database.rawQuery("SELECT currency1,currency2 FROM excange",null)

        val currIndex1 = cursor.getColumnIndex("currency1")
        val currIndex2 = cursor.getColumnIndex("currency2")


        cursor.moveToFirst()
        var i = cursor.count
        while(i != 0){

            set.add(cursor.getString(currIndex1)+" "+cursor.getString(currIndex2))
            cursor.moveToNext()

            i--
        }

        cursor!!.close()
        return set

    }


    fun deleteData(database: SQLiteDatabase){

        val sqlString = "DELETE FROM excange WHERE currency1 = ? AND currency2 = ?"

        val statment = database.compileStatement(sqlString)
        statment.bindString(1,currency1)
        statment.bindString(2,currency2)

        statment.execute()

        database.close()

    }


    fun getDatabyDate(database: SQLiteDatabase){

        val sqlString = ("SELECT * FROM excange WHERE currency1='"+currency1+"' AND currency2='"+currency2+"' AND date='"+date+"'" )
        val cursor = database.rawQuery(sqlString,null)

        val valueIndex = cursor.getColumnIndex("value")

        cursor.moveToFirst()

        if(cursor.count != 0) {
            rate = cursor.getString(valueIndex)
        }else{
            rate = "0.0"
        }

        cursor.close()
    }



    fun deleteDate(database: SQLiteDatabase){

        database.execSQL("DELETE FROM excange WHERE currency1='"+currency1+"' AND currency2='"+currency2+"' AND date='"+date+"'")

    }

}