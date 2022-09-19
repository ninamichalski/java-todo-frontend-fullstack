package com.example.backend.repo;

import com.example.backend.model.TodoElement;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class TodoRepo {

    HashMap<String, TodoElement> todoDB = new HashMap<>();

    public List<TodoElement> findAll() {
        return new ArrayList<>(todoDB.values());
    }

    public TodoElement postTodo(TodoElement todo) {
        todoDB.put(todo.getId(), todo);
        return todo;
    }

    public TodoElement findByID(String id) {
        return todoDB.get(id);
    }

    public TodoElement updateTodo(TodoElement todo) {
        todoDB.put(todo.getId(), todo);
        return todo;
    }

    public TodoElement deleteTodoById(String id) {
        return todoDB.remove(id);
    }

    public boolean existsById(String id){
        return todoDB.containsKey(id);
    }
}
