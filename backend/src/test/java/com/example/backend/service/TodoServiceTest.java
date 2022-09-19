package com.example.backend.service;

import com.example.backend.model.TodoDTO;
import com.example.backend.model.TodoElement;
import com.example.backend.repo.TodoRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoServiceTest {

    TodoRepo todoRepo = mock(TodoRepo.class);
    IdService idService = mock(IdService.class);
    TodoService service = new TodoService(todoRepo, idService);

    @Test
    void shouldReturnWholeListWhenGetAllIsRequested(){
        //GIVEN
        TodoElement todo1 = new TodoElement();
        TodoElement todo2 = new TodoElement();
        List<TodoElement> todoList = new ArrayList<>(List.of(todo1, todo2));
        when(todoRepo.findAll()).thenReturn(todoList);

        //WHEN
        List<TodoElement> actual = service.getAllTodo();

        //THEN
        assertEquals(List.of(todo1, todo2), actual);
    }

    @Test
    void shouldReturnTodoElementWhenGivenAnDTO(){
        //GIVEN
        TodoDTO todoDTO = new TodoDTO("Test","OPEN");
        TodoElement expected = new TodoElement("1", "Test", "OPEN");
        when(idService.generateID()).thenReturn("1");
        when(todoRepo.postTodo(any())).thenReturn(TodoElement.builder()
                .id("1")
                .description(todoDTO.getDescription())
                .status(todoDTO.getStatus())
                .build());

        //WHEN
        TodoElement actual = service.postNewTodo(todoDTO);

        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnElement2WhenRequestedById(){
        //GIVEN
        TodoElement todo1 = new TodoElement("1", "Test", "OPEN");
        TodoElement todo2 = new TodoElement("2", "KeinTest", "OPEN");
        List<TodoElement> todoList = new ArrayList<>(List.of(todo1, todo2));
        when(todoRepo.findByID("2")).thenReturn(todo2);

        //WHEN

        TodoElement actual = service.getTodoByID("2");

        //THEN
        assertEquals(todo2, actual);
    }

    @Test
    void shouldReturnUpdatedTodo(){
        //GIVEN
        TodoElement todo1 = new TodoElement("1", "Test", "OPEN");
        when(todoRepo.updateTodo(todo1)).thenReturn(todo1);
        when(todoRepo.existsById("1")).thenReturn(true);

        //WHEN
        TodoElement actual = service.updateTodo(todo1);

        //THEN
        assertEquals(todo1, actual);

    }

    @Test
    void shouldReturnDeletedElement(){
        //GIVEN
        TodoElement todo2 = new TodoElement("2", "Test", "DONE");
        when(todoRepo.deleteTodoById("2")).thenReturn(todo2);

        //WHEN
        TodoElement actual = service.deleteTodoById("2");

        //THEN
        assertEquals(todo2, actual);

    }

}
