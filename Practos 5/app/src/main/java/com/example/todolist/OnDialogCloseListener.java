package com.example.todolist;

import android.content.DialogInterface;

public interface OnDialogCloseListener {

    /*
    Интерфейс, который используется для обработки события закрытия
     диалогового окна. Он содержит метод, который будет вызываться при закрытии диалога
      и передает объект интерфейса DialogInterface в качестве аргумента.
     */

    void onDialogClose(DialogInterface dialogInterface);
}
