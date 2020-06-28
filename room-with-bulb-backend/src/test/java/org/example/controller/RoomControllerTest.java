package org.example.controller;

import org.example.AppTest;
import org.example.model.Room;
import org.example.repository.RoomRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTest.class)
public class RoomControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void findAllRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();
        when(roomRepository.findAll()).thenReturn(rooms);

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(rooms.size()));

        verify(roomRepository).findAll();
    }

    @Test
    public void save() throws Exception {
        String json = "{\n" +
                "  \"name\": \"hello\",\n" +
                "  \"country\": \"Belarus\"\n" +
                "}";

        when(roomRepository.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));
        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.is("hello")))
                .andExpect(jsonPath("$.country", Matchers.is("Belarus")))
                .andExpect(jsonPath("$.bulbOn", Matchers.is(false)));
        verify(roomRepository).save(any(Room.class));
    }

    @Test
    public void findRoomByIdNotFound() throws Exception {
        when(roomRepository.findById(anyInt())).thenReturn(Optional.empty());
        mockMvc.perform(get("/rooms/4"))
                .andExpect(status().isNotFound());
        verify(roomRepository).findById(anyInt());
    }
}