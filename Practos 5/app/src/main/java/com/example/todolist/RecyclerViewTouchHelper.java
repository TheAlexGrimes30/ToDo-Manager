package com.example.todolist;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecyclerViewTouchHelper extends ItemTouchHelper.SimpleCallback{

    /*
    Класс для создания свайпов редактивания и удаления элементов RecyclerView
    Наследуется класс ItemTouchHelper.SimpleCallback
     */

    private ToDoAdapter adapter;

    public RecyclerViewTouchHelper(ToDoAdapter adapter) {

        /*
        Конструктор класса RecyclerViewTouchHelper.
        Параметр: adapter
        */

        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {

        /*
        Этот метод определяет поведение при перемещении элемента списка.
        В данном случае, метод возвращает значение false,
        что означает, что перемещение элемента списка не разрешено.

        Параметры: recyclerView, viewHolder, target
        return false
        */
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        /*
        Получает позицию элемента в адаптере, который был смахнут влево или вправо.
        При нажатии на CheckBox вызывается всплывающее окно с мотивационной фразой.

        Параметры: viewHolder, direction
         */
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());
            builder.setTitle("Delete Task");
            builder.setMessage("Are You Sure ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.deleteTask(position);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else{
            adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        /*
        Этот метод выполняет рисование свайпа для элемента списка в RecyclerView.

        Параметры: c, recyclerView, viewHolder, dX, dY,
        actionState, isCurrentActive
         */

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState,
                isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(adapter.getContext() ,
                        R.color.colorPrimaryDark))
                .addSwipeLeftActionIcon(R.drawable.edit)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.delete)
                .create()
                .decorate();
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
