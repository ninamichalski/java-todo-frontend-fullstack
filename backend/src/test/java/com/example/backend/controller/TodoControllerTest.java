package com.example.backend.controller;

import com.example.backend.model.TodoElement;
import com.example.backend.repo.TodoRepo;
import com.example.backend.service.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepo repo;

    @MockBean
    private IdService idService;

    @Test
    void addTodo() throws Exception {

        //GIVEN
        when(idService.generateID()).thenReturn("1");

        String requestBody = """
                {
                    "description":"Test",
                    "status":"OPEN"
                }
                """;

        String expectedJSON = """
                {
                    "id":"1",
                    "description":"Test",
                    "status":"OPEN"
                }
                """;

        //WHEN+THEN
        mockMvc.perform(
                post("/api/todo")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }

    @Test
    void getAllTodoShouldReturnItemsFromDB() throws Exception {

        //GIVEN

        repo.postTodo(new TodoElement("1","Test", "OPEN"));
        repo.postTodo(new TodoElement("2","Test2", "DONE"));

        String expectedJSON = """
                [
                    {
                        "id":"1",
                        "description":"Test",
                        "status":"OPEN"
                    },
                    {
                        "id":"2",
                        "description":"Test2",
                        "status":"DONE"
                    }
                ]
                """;
        //WHEN+THEN
        mockMvc.perform(get("/api/todo"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));
    }

    @Test
    void updateTodoShouldUpdate() throws Exception {

        //GIVEN
        repo.postTodo(new TodoElement("1","Test", "OPEN"));
        repo.postTodo(new TodoElement("2","Test2", "DONE"));

        String requestBody = """
                {
                    "id":"1",
                    "description":"get some coffee",
                    "status":"IN_PROGRESS"
                }
                """;

        String expectedJSON = """
                {
                    "id":"1",
                    "description":"get some coffee",
                    "status":"IN_PROGRESS"
                }
                """;
        //WHEN+THEN
        mockMvc.perform(
                put("/api/todo/1")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

        //THEN
        List<TodoElement> todoElements = repo.findAll();
        assertThat(todoElements, containsInAnyOrder(
                new TodoElement("1","get some coffee", "IN_PROGRESS"),
                new TodoElement("2","Test2", "DONE")));

    }

    @Test
    void getTodoByIdShouldReturnRequestedItem() throws Exception {

        //GIVEN
        repo.postTodo(new TodoElement("1","Test", "OPEN"));
        repo.postTodo(new TodoElement("2","Test2", "DONE"));

        String expectedJSON = """
                {
                    "id":"1",
                    "description":"Test",
                    "status":"OPEN"
                }
                """;
        //WHEN+THEN
        mockMvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJSON));

    }

    @Test
    void deleteTodoShouldDeleteRequestedTodoFromList() throws Exception {

        //GIVEN
        TodoElement todo1 = new TodoElement("1","Test", "OPEN");
        TodoElement todo2 = new TodoElement("2","Test2", "DONE");
        repo.postTodo(todo1);
        repo.postTodo(todo2);

        //WHEN
        mockMvc.perform(delete("/api/todo/1"));

        //THEN
        List<TodoElement> actual = repo.findAll();
        assertEquals(actual, List.of(todo2));
    }

}
