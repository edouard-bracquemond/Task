package com.example.afe.task.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.afe.task.R;
import com.example.afe.task.model.AppDatabase;
import com.example.afe.task.model.Task;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TacheAdapter.OnTaskListener {
    private Button create;
    private RecyclerView recycler;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
        List<Task> tmp=instance.taskDao().getTasks();
        tasks=new ArrayList<>();
        tasks.addAll(tmp);
        create= (Button)findViewById(R.id.create_tache);
        initRecycleView();
        Log.i("TOTO","Je suis dans le onCreate MainActivity");
    }

    @Override
    public void onResume() {
        super.onResume();
        AppDatabase instance = AppDatabase.getInstance(this.getApplicationContext());
        tasks.clear();
        List<Task> tmp=instance.taskDao().getTasks();
        tasks.addAll(tmp);
        Collections.sort(tasks);
        initRecycleView();
    }

    private void initRecycleView(){
        recycler = (RecyclerView)findViewById(R.id.r_tache);
        TacheAdapter tacheAdapter=new TacheAdapter(tasks,this,this);
        recycler.setAdapter(tacheAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    public void form_creation(View v){
        lanceCreation();
    }

    public void lanceCreation(){
        startActivity(new Intent(this, Form_tacheCreate.class));
    }

    @Override
    public void onTaskClick(int position) {
        Log.i("TOTA","Je suis dans le Onclick MainActivity");
        Intent intent = new Intent(this, Form_tacheModif.class);
        intent.putExtra("id_select_task",tasks.get(position).getId());
        startActivity(intent);
    }
}
