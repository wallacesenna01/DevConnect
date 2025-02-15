package com.br.wallaceartur.DevConnect.controller;

import com.br.wallaceartur.DevConnect.entities.UserProfile;
import com.br.wallaceartur.DevConnect.services.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long userId){
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<UserProfile> create( @RequestBody UserProfile userProfile) {
        UserProfile updateProfile = userProfileService.create(userProfile);
        return ResponseEntity.ok(updateProfile);
    }
    
}
