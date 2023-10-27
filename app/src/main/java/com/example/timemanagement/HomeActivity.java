package com.example.timemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private ArrayList<TaskToDo> myList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String userID = mAuth.getCurrentUser().getUid();
        DatabaseReference fireDBTasks = FirebaseDatabase.getInstance().getReference("Tasks")
                .child(userID);
        fireDBTasks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView viewNoTasks = findViewById(R.id.viewNoTasks);
                RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
                // only grab one/first item stored
                TaskToDo myTask = snapshot.getValue(TaskToDo.class);
                myList.add(myTask); // add to list

                if (myList.isEmpty()){
                    viewNoTasks.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else{
                    viewNoTasks.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                // pass to recycler view
                MyAdapter mAdapter = new MyAdapter(myList, HomeActivity.this);

                // decorate and set adapter
                mRecyclerView.addItemDecoration(new DividerItemDecoration(HomeActivity.this,
                        LinearLayoutManager.VERTICAL));
                mRecyclerView.setAdapter(mAdapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    public void addTask(MenuItem item){

    }

    public void logout(MenuItem item){
        mAuth.signOut();
        finish();
    }

    public void updateMyList(ArrayList<TaskToDo> adapterList){
        myList = adapterList;
    }
}