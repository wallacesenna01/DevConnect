package com.br.wallaceartur.DevConnect.entities;

import jakarta.persistence.*;

@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String bio;
    private String technologies;
    private String profilePictureUrl;

    public UserProfile() {}

    public UserProfile(Long id, User user, String name, String bio, String technologies, String profilePictureUrl) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.bio = bio;
        this.technologies = technologies;
        this.profilePictureUrl = profilePictureUrl;
    }

    public UserProfile(User user, String name, String bio, String technologies, String profilePictureUrl) {
        this.user = user;
        this.name = name;
        this.bio = bio;
        this.technologies = technologies;
        this.profilePictureUrl = profilePictureUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }


}
