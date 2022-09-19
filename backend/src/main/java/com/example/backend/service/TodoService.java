package com.example.backend.service;

import com.example.backend.model.TodoDTO;
import com.example.backend.model.TodoElement;
import com.example.backend.repo.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    private final TodoRepo repo;
    private final IdService idService;

    @Autowired
    public TodoService(TodoRepo repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
    }

    public List<TodoElement> getAllTodo() {
        return repo.findAll();
    }

    public TodoElement postNewTodo(TodoDTO todo) {
        TodoElement todoElement = TodoElement.builder()
                .status(todo.getStatus())
                .description(todo.getDescription())
                .id(idService.generateID())
                .build();
        return repo.postTodo(todoElement);
    }

    public TodoElement getTodoByID(String id) {
        return repo.findByID(id);
    }

    public TodoElement updateTodo(TodoElement todo) {
        if (!repo.existsById(todo.getId())){
            throw new NoSuchElementException("There is no Element with the requested ID");
        }
        return repo.updateTodo(todo);
    }

    public TodoElement deleteTodoById(String id) {
        return repo.deleteTodoById(id);
    }
}
