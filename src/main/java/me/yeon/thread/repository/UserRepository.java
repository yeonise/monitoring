package me.yeon.thread.repository;

import org.springframework.data.repository.CrudRepository;

import me.yeon.thread.domain.User;

public interface UserRepository extends CrudRepository<User, String> {
}
