package com.br.wallaceartur.DevConnect.services;

import com.br.wallaceartur.DevConnect.dtos.RegisterRequest;
import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



  public User findByUsername(String user ) {
      return  findByUsername(user);
    }


    public User registerNewUser(RegisterRequest registerRequest) {
      if (userRepository.existsByUsername(registerRequest.getUsername())) {
          throw new RuntimeException("Usuário ja existe");
      }

      User user = new User();

      user.setUsername(registerRequest.getUsername());
      user.setPassword(registerRequest.getPassword());
      user.setEmail(registerRequest.getEmail());
      return userRepository.save(user);

    }
}
