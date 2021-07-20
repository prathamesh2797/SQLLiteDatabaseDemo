package com.example.sqllitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context,"EmployeeDatabase", null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {

        val CREATE_CONTACTS_TABLE = ("CREATE TABLE EmployeeTable(_id INTEGER PRIMARY KEY, name TEXT, email TEXT)")
        p0!!.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0!!.execSQL("DROP TABLE IF EXISTS EmployeeTable")
        onCreate(p0)
    }


    //Inserting Data in Database
    fun addEmployee(emp : EmpModelClass): Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()


        contentValues.put("name" , emp.tvName.toString())
        contentValues.put("email", emp.tvEmail.toString())

        // Inserting Row
        val success = db.insert("EmployeeTable", null, contentValues)
        db.close()

        return  success
    }


    //Reading Data from Database
    fun viewEmployee(): ArrayList<EmpModelClass>{
        val empList: ArrayList<EmpModelClass> = ArrayList<EmpModelClass>()

        val selectQuery = "SELECT * FROM EmployeeTable"
        
        val db = this.readableDatabase

        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery,null)

        }catch (e: SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id : Int
        var name: String
        var email: String


        if (cursor.moveToFirst()){
            do {

                id = cursor.getInt(cursor.getColumnIndex("_id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val emp = EmpModelClass(id, name, email)
                empList.add(emp)

            }while (cursor.moveToNext())

        }
        return empList
    }



    //For Updating Data in Database
    fun updateEmployee(emp: EmpModelClass): Int{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", emp.tvName)
        contentValues.put("email", emp.tvEmail)

        val success = db.update("EmployeeTable", contentValues, "_id = ${emp.id}", null )
        db.close()

        return success

    }


    //For Deleting Data in Database
    fun deleteEmployee(emp: EmpModelClass): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("_id", emp.id)

        val success = db.delete("EmployeeTable", "_id = ${emp.id}",null)
        db.close()
        return success
    }
}