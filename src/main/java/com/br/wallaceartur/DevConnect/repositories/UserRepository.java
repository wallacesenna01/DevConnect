package com.br.wallaceartur.DevConnect.repositories;

import com.br.wallaceartur.DevConnect.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

    boolean existsByUsername(String user);

}
