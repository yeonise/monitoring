package me.yeon.thread.repository;

import org.springframework.data.repository.CrudRepository;

import me.yeon.thread.domain.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
