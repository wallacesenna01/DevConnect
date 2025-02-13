package com.br.wallaceartur.DevConnect.resources;

import com.br.wallaceartur.DevConnect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserResource extends JpaRepository <User, Long> {

    boolean existsByUsername(String user);

    Optional<User> findByUsername(String username);
}
