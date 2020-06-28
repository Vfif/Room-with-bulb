package org.example.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.example.AppTest;
import org.example.model.Room;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@SpringBootTest(classes = AppTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@Sql({"schema.sql", "data.sql"})
public class RoomServiceTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RoomService roomService;

    @Test
    void findAllRooms() {
        List<Room> roomList = roomService.findAllRooms();
        Assertions.assertEquals(roomList.size(), 1);
    }

    @Test
    void save() {
        Room room = Room.builder()
                .name("hello")
                .country("Belarus")
                .build();
        roomService.save(room);
        List<Room> roomList = roomService.findAllRooms();
        Assertions.assertEquals(2, roomList.size());
    }

    @Test
    void findRoomById() throws IOException, GeoIp2Exception {
        InetAddress address = InetAddress.getByName("185.158.218.1");
        Room room = roomService.findRoomById(1, address);
        Assertions.assertEquals(room.getName(), "new");
        Assertions.assertEquals(room.getCountry(), "Belarus");
    }
}
