package com.example.backend.controller;

import com.example.backend.model.TodoDTO;
import com.example.backend.model.TodoElement;
import com.example.backend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }


    @GetMapping
    public List<TodoElement> getAllTodo(){
        return service.getAllTodo();
    }

    @PostMapping
    public TodoElement postNewTodo(@RequestBody TodoDTO todo){
        return service.postNewTodo(todo);
    }

    @GetMapping(path = "/{id}")
    public TodoElement getTodoByID(@PathVariable String id){
        return service.getTodoByID(id);
    }

    @PutMapping(path = "/{id}")
    public TodoElement updateTodo(@PathVariable String id, @RequestBody TodoElement todo){
        return service.updateTodo(todo);
    }

    @DeleteMapping(path = "/{id}")
    public TodoElement deleteTodoById(@PathVariable String id){
        return service.deleteTodoById(id);
    }
}
