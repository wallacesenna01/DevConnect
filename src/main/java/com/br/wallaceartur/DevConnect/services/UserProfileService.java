package com.br.wallaceartur.DevConnect.services;

import com.br.wallaceartur.DevConnect.entities.User;
import com.br.wallaceartur.DevConnect.entities.UserProfile;
import com.br.wallaceartur.DevConnect.resources.UserProfileResource;
import com.br.wallaceartur.DevConnect.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileResource userProfileResource;

    @Autowired
    private UserResource userResource;


    public UserProfileService(UserProfileResource userProfileResource, UserResource userResource) {
        this.userProfileResource = userProfileResource;
        this.userResource = userResource;
    }

    //Criação ou atualização do perfil

    public UserProfile createOrUpdateProfile(Long userId, String name, String bio, String technologies, String profilePictureUrl) {
        User user = userResource.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário nao encontrado"));

        UserProfile userProfile = userProfileResource.findByUser(user)
                .orElse(new UserProfile(user, name, bio, technologies,profilePictureUrl));

        userProfile.setName(name);
        userProfile.setBio(bio);
        userProfile.setTechnologies(technologies);
        userProfile.setProfilePictureUrl(profilePictureUrl);

        return userProfileResource.save(userProfile);
    }


    // buscar perfil do usuário
    public UserProfile getProfile(Long userid){
        User user = userResource.findById(userid)
                .orElseThrow(() -> new RuntimeException("Usuário nao encontrado"));

        return userProfileResource.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Pefil nao encontrado"));
    }
}
