package com.hackatrip.iranmate.iranmate.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by H4miD on 5/31/16.
 */
public class Post {
    // post photo
    Bitmap postPhoto;
    User creator ;                  // person who create post for the first time
    ArrayList<User> addedUsers;

    String postDiscription;         // first line of the post is little discription that will be shown in the main page

    public Post(Bitmap postPhoto,User creator,ArrayList addedUsers,String postDiscription){         // post constructor
        this.postPhoto = postPhoto;
        this.creator = creator;
        this.addedUsers = addedUsers;
        this.postDiscription = postDiscription;
    }

    public Post(){                              // non-argument costructor for saving to the backend

    }

    public Bitmap getPostPhoto() {
        return postPhoto;
    }

    public void setPostPhoto(Bitmap postPhoto) {
        this.postPhoto = postPhoto;
    }

   // public String getCreator() {
   //     return creator;
    //}

    public void setCreator(User creator) {
        //this.creator = creator.getUsername();
    }

    public ArrayList<User> getAddedUsers() {
        return addedUsers;
    }

    public void setAddedUsers(ArrayList<User> addedUsers) {
        this.addedUsers = addedUsers;
    }

    public String getPostDiscription() {
        return postDiscription;
    }

    public void setPostDiscription(String postDiscription) {
        this.postDiscription = postDiscription;
    }



}
