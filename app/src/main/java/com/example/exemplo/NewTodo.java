package com.example.exemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exemplo.model.Todo;
import com.example.exemplo.service.TodoAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class NewTodo extends AppCompatActivity {
    private TodoAPI todoAPI;

    private EditText campoDescricao;
    private EditText campoPrioridade;
    private Button botaoSalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        todoAPI = new TodoAPI();
        inicializarReferencias();
        inicializarAcoes();
    }

    private void inicializarReferencias(){
         campoDescricao = (EditText) findViewById(R.id.campo_new_todo_descricao);
       campoPrioridade = (EditText) findViewById(R.id.campo_new_todo_prioridade);
       botaoSalvar = (Button) findViewById(R.id.salvarNovoTodo);

    }
    private void inicializarAcoes(){
        Button button = findViewById(R.id.salvarNovoTodo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewTodo.this, MainActivity.class);
                startActivity(intent);
              AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                          Todo todo = new Todo();

                todo.setDescricao(campoDescricao.getText().toString());
                todo.setPrioridade(campoPrioridade.getText().toString());
                try {
                    todoAPI.inserirTodo(todo);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                    }
                });
            }
        });

    }

}
