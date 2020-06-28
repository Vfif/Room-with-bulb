package org.example.controller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.example.model.Room;
import org.example.service.RoomService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> findAllRooms() {
        return roomService.findAllRooms();
    }

    @PostMapping
    public Room save(@RequestBody Room room) {
        return roomService.save(room);
    }

    @GetMapping("/{id}")
    public Room findRoomById(@PathVariable int id, HttpServletRequest request) throws IOException, GeoIp2Exception {
        InetAddress address = InetAddress.getByName(/*request.getRemoteAddr()*/"185.158.218.126");

        return roomService.findRoomById(id, address);
    }
}
