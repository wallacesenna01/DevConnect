package com.br.wallaceartur.DevConnect.services;

import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.entities.UserProfile;
import com.br.wallaceartur.DevConnect.repositories.UserProfileRepository;
import com.br.wallaceartur.DevConnect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;


    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    //Criação ou atualização do perfil

    @Transactional
    public UserProfile create(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }


    public UserProfile getProfile(Long userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado para o Id: " + userId));
    }

    public UserProfile createOrUpDateProfile(Long userId, String name, String bio, String technologies, String profilePictureUrl) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário nao encontrado" + userId));

        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElse(new UserProfile());

        userProfile.setUser(user);
        userProfile.setName(name);
        userProfile.setBio(bio);
        userProfile.setTechnologies(technologies);
        userProfile.setProfilePictureUrl(profilePictureUrl);
        return userProfileRepository.save(userProfile);

    }
}