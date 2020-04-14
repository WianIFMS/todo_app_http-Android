package com.example.exemplo.service;

import com.example.exemplo.model.Todo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TodoAPI {
    public Todo buscarPorId(Long id) throws IOException {
    URL url = new URL("https://test-rest-ifms.herokuapp.com/todo/"+id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            String body = br.readLine();
            Gson gson = new Gson();
            Todo todo = gson.fromJson(body,Todo.class);
            return todo;

        }
        conn.disconnect();
        return null;
    }
    public List<Todo> listarTodos() throws IOException {
        URL url = new URL("https://test-rest-ifms.herokuapp.com/todo");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            String body = br.readLine();
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Todo>>(){}.getType();
            List<Todo> todos = gson.fromJson(body,type);

            return todos;

        }
        conn.disconnect();
        return null;
    }
    public  void  inserirTodo(Todo todo) throws IOException {
        URL url = new URL("https://test-rest-ifms.herokuapp.com/todo");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type","application/json");
        Gson gson = new Gson();
        String body = gson.toJson(todo);
        OutputStream os = conn.getOutputStream();
        os.write(body.getBytes());
        os.flush();
        os.close();

        if (conn.getResponseCode() == 200){

        }else{

        }
        conn.disconnect();
    }
}
