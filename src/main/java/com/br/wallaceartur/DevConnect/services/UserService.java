package com.br.wallaceartur.DevConnect.services;

import com.br.wallaceartur.DevConnect.dtos.RegisterRequest;
import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserResource userResource;

    public UserService(UserResource userResource) {
        this.userResource = userResource;
    }

    private PasswordEncoder passwordEncoder;

  public User findByUsername(String user ) {
      return  findByUsername(user);
    }


    public User registerNewUser(RegisterRequest registerRequest) {
      if (userResource.existsByUsername(registerRequest.getUsername())) {
          throw new RuntimeException("Usuário ja existe");
      }

      User user = new User();

      user.setUsername(registerRequest.getUsername());
      user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
      user.setEmail(registerRequest.getEmail());
      return userResource.save(user);

    }
}
