package com.br.wallaceartur.DevConnect.repositories;

import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);
}
