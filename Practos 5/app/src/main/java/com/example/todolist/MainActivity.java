package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener{

    /*
    В данном классе отображается список добавленных задач.
    Наследуется от класса AppCompatActivity
    и интерфейса OnDialogCloseListener
     */

    private RecyclerView recyclerView;
    private ImageView TaskBtn;
    private DataBaseHelper myDB;
    private List<ToDoModel> dataList;
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        Этот метод представляет собой переопределение метода onCreate() в классе Activity.
         Он используется для инициализации и установки начального состояния
         пользовательского интерфейса при создании активности.

         Параметр: savedInstanceSaved
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.mainRecyclerView);
        TaskBtn = findViewById(R.id.addTaskBtn);
        myDB = new DataBaseHelper(MainActivity.this);
        dataList = new ArrayList<>();
        adapter = new ToDoAdapter(myDB , MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        dataList = myDB.getAllTasks();
        Collections.reverse(dataList);
        adapter.setTasks(dataList);

        TaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager() , AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onDialogClose(DialogInterface dialogInterface) {

        /*
        Данный метод обновляет данные в списке задач и уведомляет адаптер
        о необходимости обновить свои данные и перерисовать список.
         */

        dataList = myDB.getAllTasks();
        Collections.reverse(dataList);
        adapter.setTasks(dataList);
        adapter.notifyDataSetChanged();
    }
}