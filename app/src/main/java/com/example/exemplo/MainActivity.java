package com.example.exemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exemplo.adapter.TodoListAdapter;
import com.example.exemplo.model.Todo;
import com.example.exemplo.service.TodoAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TodoListAdapter adapter;
    private List<Todo> todos;
    private TodoAPI todoAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarListView();
        inicializarBotaoAdd();
        todoAPI = new TodoAPI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillListView();

    }

    public void iniciarListView(){
        todos = new ArrayList<>();
        ListView listView = findViewById(R.id.activity_main_listview);
        adapter = new TodoListAdapter(this,todos);
        listView.setAdapter(adapter);

    }
    public void inicializarBotaoAdd(){
        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTodo.class);
                startActivity(intent);
              /*  AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        Todo todo = new Todo();
                        todo.setDescricao("Ir ao banhiero");
                        todo.setPrioridade("ALTA");
                        try {
                            todoAPI.inserirTodo(todo);
                            fillListView();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });*/
            }
        });
    }
    private void fillListView(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    todos.clear();
                    todos.addAll(todoAPI.listarTodos());
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            Toast toast = Toast.makeText(MainActivity.this,"Tarefa Salva!",Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }catch (IOException ex){

                }
            }
        });
    }
}
