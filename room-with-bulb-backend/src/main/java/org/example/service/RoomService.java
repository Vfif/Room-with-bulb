package org.example.service;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.example.exception.ForbiddenException;
import org.example.exception.ResourceNotFoundException;
import org.example.model.Room;
import org.example.repository.RoomRepository;
import org.example.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private IpUtil util;

    private RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room findRoomById(int id, InetAddress address) throws IOException, GeoIp2Exception {
        Room room = roomRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        if (util.getCountryByIpAddress(address).equals(room.getCountry())) {
            return room;
        } else {
            throw new ForbiddenException();
        }
    }
}
