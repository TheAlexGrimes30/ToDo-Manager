package com.example.todolist.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    /*
        Класс для создания и работы с базой данных
        Содержит поля ID, TASK, STATUS,
         COMMENT, DATE, TIME
     */

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "STATUS";
    private static final String COL_4 = "COMMENT";
    private static final String COL_5 = "DATE";
    private static final String COL_6 = "TIME";
    private SQLiteDatabase db;
    public DataBaseHelper(@Nullable Context context) {

        /*
            Конструктор класса DataBaseHelper
            Содержит параметр context
         */

        super(context,  DATABASE_NAME, null,
                1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Метод создания базы данных
        // Параметр: db

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT , STATUS INTEGER," +
                " COMMENT TEXT," +
                " DATE TEXT, TIME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*
            Метод обновления базы данных
            Параметры: db, oldVersion, newVersion
         */

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(ToDoModel model){

        /*
            Метод добавления данных
            о здачах пользователя в базу данных

            Параметр: model
         */

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , model.getTask());
        values.put(COL_3 , 0);
        values.put(COL_4, model.getComment());
        values.put(COL_5, model.getDueDate());
        values.put(COL_6, model.getDueTime());
        db.insert(TABLE_NAME , null , values);
    }

    public void updateTask(int id , String task){

        /*
            Метод обновления задачи пользователя
            в базе данных
            Параметры: id, task
         */

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2 , task);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateComment(int id, String comment){

        /*
            Метод обновления комментария пользователя
            в базе данных
            Параметры: id, comment
         */

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_4, comment);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateDate(int id, String dueDate){

        /*
            Метод обновления даты выполнения задачи
             пользователя в базе данных
            Параметры: id, dueDate
         */

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_5, dueDate);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void updateTime(int id, String dueTime){

        /*
            Метод обновления времени выполнения задачи
            пользователя в базе данных
            Параметры: id, duetime
         */

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_6, dueTime);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }


    public void updateStatus(int id , int status){

        /*
            Метод обновления статуса выполнения задачи
            пользователя в базе данных
            Параметры: id, status
         */

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_3 , status);
        db.update(TABLE_NAME , values , "ID=?" , new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id ){

        /*
            Метод обновления удаления задачи
            пользователя в базе данных
            Параметр: id
         */

        db = this.getWritableDatabase();
        db.delete(TABLE_NAME , "ID=?" , new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<ToDoModel> getAllTasks(){

        /*
        Этот метод выполняет запрос ко всем записям таблицы в базе данных
        и возвращает список объектов класса ToDoModel, содержащих данные записей.
        Каждый объект содержит значения столбцов
        "id", "task", "status", "comment", "dueDate" и "dueTime"
         */

        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<ToDoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null ,
                    null , null , null);
            if (cursor !=null){
                if (cursor.moveToFirst()){
                    do {
                        ToDoModel task = new ToDoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));
                        task.setComment(cursor.getString(cursor.getColumnIndex(COL_4)));
                        task.setDueDate(cursor.getString(cursor.getColumnIndex(COL_5)));
                        task.setDueTime(cursor.getString(cursor.getColumnIndex(COL_6)));
                        modelList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
