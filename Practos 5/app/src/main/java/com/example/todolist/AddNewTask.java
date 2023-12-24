package com.example.todolist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class AddNewTask extends BottomSheetDialogFragment {

    /*
        Данный класс работает со всплывающим окном.
        Выполняет роль контроллера для добавления
        и обновления данных в бд.

        Часть паттерна MVC

        Наследует класс BottomSheetDialog
     */

    public static final String TAG = "AddNewTask";

    private TextInputEditText mEditTask;
    private TextInputEditText mEditComment;
    private TextView mEditDate;
    private TextView mEditTime;
    private ImageView mSaveButton;

    private DataBaseHelper myDb;
    private int YEAR;
    private int MONTH;
    private int DAY;
    private int HOUR;
    private int MINUTE;


    public static AddNewTask newInstance(){

        // Данный метод возвращает экземпляр класса AddNewTask

        return new AddNewTask();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /*
        Этот метод выполняется при создании и отображении фрагмента на экране.
        Он создает и возвращает объект View,
        который представляет графический интерфейс фрагмента.

        Параметры: inflater, container, savedInstanceState

        Return: view
         */

        View v = inflater.inflate(R.layout.add_new_task , container , false);
        return v;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /*
        Данный метод выполняет инициализацию и установку различных обработчиков событий
        для элементов пользовательского интерфейса во фрагменте.

        Параметры: view, savedInstanceState
         */

        super.onViewCreated(view, savedInstanceState);

        mEditTask = view.findViewById(R.id.todoEt);
        mEditComment = view.findViewById(R.id.commEt);
        mEditDate = view.findViewById(R.id.dateChoose);
        mEditTime = view.findViewById(R.id.timeChoose);
        mSaveButton = view.findViewById(R.id.todoSaveBtn);

        myDb = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if (bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            String comment = bundle.getString("comment");
            String dueDate = bundle.getString("dueDate");
            String dueTime = bundle.getString("dueTime");

            mEditTask.setText(task);
            mEditComment.setText(comment);
            mEditDate.setText(dueDate);
            mEditTime.setText(dueTime);

            if (task.length() > 0 ){
                mSaveButton.setEnabled(false);
            }

        }
        mEditTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    mSaveButton.setEnabled(false);

                }else{
                    mSaveButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        YEAR = calendar.get(Calendar.YEAR);
        MONTH = calendar.get(Calendar.MONTH) + 1;
        DAY = calendar.get(Calendar.DAY_OF_MONTH);

        HOUR = calendar.get(Calendar.HOUR_OF_DAY);
        MINUTE = calendar.get(Calendar.MINUTE);


        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if ((dayOfMonth <= DAY) && (month + 1 <= MONTH) && (year <= YEAR)){
                            mEditDate.setText("");
                        }else{
                            mEditDate.setText(dayOfMonth +"."+month+1+"."+year);
                        }

                    }
                }, YEAR, MONTH, DAY);
                datePickerDialog.show();
            }
        });

        mEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(((hourOfDay <= HOUR) && (minute <= MINUTE)) &&
                                (mEditDate.getText().toString()).equals("")){
                            mEditTime.setText("");
                        } else{
                            mEditTime.setText(hourOfDay+":"+minute);
                        }

                    }
                }, HOUR, MINUTE, true);
                timePickerDialog.show();
            }
        });



        final boolean finalIsUpdate = isUpdate;
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String task = mEditTask.getText().toString();
                String comment = mEditComment.getText().toString();
                String dueDate = mEditDate.getText().toString();
                String dueTime = mEditTime.getText().toString();

                if (finalIsUpdate){
                    myDb.updateTask(bundle.getInt("id") , task);
                    myDb.updateComment(bundle.getInt("id") , comment);
                    myDb.updateDate(bundle.getInt("id") , dueDate);
                    myDb.updateTime(bundle.getInt("id") , dueTime);
                }else{
                    ToDoModel item = new ToDoModel();
                    item.setTask(task);
                    item.setStatus(0);
                    item.setComment(comment);
                    item.setDueDate(dueDate);
                    item.setDueTime(dueTime);
                    myDb.insertTask(item);
                }
                dismiss();

            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

        /*
        Данный метод вызывается при закрытии диалогового окна.
        Он переопределяет метод onDismiss()
        из класса DialogInterface.OnDismissListener.

        Параметр: dialog
         */
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener){
            ((OnDialogCloseListener)activity).onDialogClose(dialog);
        }
    }
}
