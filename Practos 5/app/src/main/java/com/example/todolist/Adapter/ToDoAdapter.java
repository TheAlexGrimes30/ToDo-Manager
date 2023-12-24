package com.example.todolist.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DataBaseHelper;

import java.util.List;

import com.example.todolist.Phrases.MotivationGenerator;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TaskViewHolder>{

    /*
        Класс ToDoAdapter
        Используется для связи данных с представлением элементов списка
        Содержит интерфейс IToDoAdapter
    */

    private List<ToDoModel> dataList;
    private MainActivity activity;
    private DataBaseHelper myDB;

    //Конструктор класса ToDoAdapter  по умолчанию
    public ToDoAdapter(){}

    public ToDoAdapter(DataBaseHelper myDB , MainActivity activity){

        /* Конструктор класса ToDoAdapter
        Парaметры: myDB, activity
        */

        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*
        Этот метод используется в адаптере для создания нового ViewHolder.

        Парметры: parent, viewType

        return: new TaskViewHolder(v)
         */

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout , parent , false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        /*
            Этот метод используется в адаптере
            для связывания данных с элементом списка (View) в указанной позиции.

            Каждый элемент списка получает название, дату и время выполнения.

            При нажатии на CheckBox выводится мотивационная фраза, если до этого не был нажат

            Параметры: holder, position

         */

        final ToDoModel item = dataList.get(position);
        holder.taskCheckBox.setText(item.getTask());
        holder.taskCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.textDate.setText(item.getDueDate());
        holder.textTime.setText(item.getDueTime());
        holder.taskCheckBox.setOnCheckedChangeListener(new CompoundButton
                .OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);

                    MotivationGenerator generator = new MotivationGenerator();
                    String motivation = generator.generateMotivation();

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Мотивационная фраза");
                    builder.setMessage(motivation);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Do something
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    builder.show();
                }else
                    myDB.updateStatus(item.getId() , 0);
            }
        });
    }

    public boolean toBoolean(int num){

        /*
            Этот метод выполняет преобразование целого числа в булево значение.

            return True/False
         */

        return num!=0;
    }

    public Context getContext(){

        /*
            Этот метод возвращает значение activity

            return: activity
         */

        return activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTasks(List<ToDoModel> dataList){

        /*
            Этот метод устанавливает новый список задач для адаптера и
            вызывает метод notifyDataSetChanged().

            Параметр: dataList
         */

        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){

        /*
            Этот метод удаляет задачу из списка задач и из базы данных.

            Параметр: position
         */

        ToDoModel item = dataList.get(position);
        myDB.deleteTask(item.getId());
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){

        /*
            Этот метод открывает диалоговое окно редактирования задачи.

            Параметр: position
         */

        ToDoModel item = dataList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());
        bundle.putString("comment", item.getComment());
        bundle.putString("dueDate", item.getDueDate());
        bundle.putString("dueTime", item.getDueTime());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager() , task.getTag());


    }

    @Override
    public int getItemCount() {

        /*
            Этот метод возвращает длину списка

            return: dataList.size()
         */

        return dataList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        /*
            Этот класс содержит ссылки на элементы пользовательского интерфейса
         */

        CheckBox taskCheckBox;
        TextView textDate, textTime;

        /*
            Конструктор TaskViewHolder
            Принимет параметры itemView, taskCheckBox, textDate, textTime
         */

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskCheckBox = itemView.findViewById(R.id.taskcheckbox);
            textDate = itemView.findViewById(R.id.due_date_tv);
            textTime = itemView.findViewById(R.id.due_time_tv);
        }
    }

}
