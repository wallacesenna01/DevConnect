package com.br.wallaceartur.DevConnect.resources;

import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileResource extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUser(User user);
}
