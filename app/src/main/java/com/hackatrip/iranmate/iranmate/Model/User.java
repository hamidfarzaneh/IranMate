package com.hackatrip.iranmate.iranmate.Model;

import java.util.ArrayList;

/**
 * Created by H4miD on 5/31/16.
 */
public class User {
    String username ;
    String email;
    String password;
    ArrayList<User> friends;
    ArrayList<Post> posts;
    ArrayList<Group> groups;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public User(){

    }
    public User(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
        friends = new ArrayList<>();
        posts = new ArrayList<>();
        groups = new ArrayList<>();

    }

    public void setCurrentLocation(){

    }

}
