package com.example.timemanagement;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<TaskToDo> myList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView viewName;
        public TextView viewDesc;
        public TextView viewDDate;
        public RelativeLayout myRow;
        public MyViewHolder(View itemView) {
            super(itemView);
            viewName = (TextView) itemView.findViewById(R.id.viewName);
            viewDesc = (TextView) itemView.findViewById(R.id.viewDesc);
            viewDDate = (TextView) itemView.findViewById(R.id.viewDDate);
            myRow = itemView.findViewById(R.id.myRow);
        }
    }

    public MyAdapter(ArrayList<TaskToDo> myDataset, Context cntxt) {
        myList = myDataset;
        context = cntxt;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final TaskToDo myTask = myList.get(position);
        holder.viewName.setText(myTask.getName());
        holder.viewDesc.setText(myTask.getDesc());
        holder.viewDDate.setText(myTask.getDueDate());


        holder.myRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_dialog);

                EditText etName = dialog.findViewById(R.id.editName);
                EditText etDesc = dialog.findViewById(R.id.editDesc);
                EditText etdDate = dialog.findViewById(R.id.editdDate);
                Button confirmButton = dialog.findViewById(R.id.confirmbutton);

                TaskToDo myTask = myList.get(position);
                etName.setText(myTask.getName());
                etDesc.setText(myTask.getDesc());
                etdDate.setText(myTask.getDueDate());

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        String name = etName.getText().toString();
                        String desc = etDesc.getText().toString();
                        String ddate = etdDate.getText().toString();

                        myList.set(position, new TaskToDo(name, desc, ddate, false));
                        notifyItemChanged(position);

                        HomeActivity ha = (HomeActivity) context;
                        ha.updateMyList(myList);

                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}

