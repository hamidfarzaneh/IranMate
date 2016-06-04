package com.hackatrip.iranmate.iranmate.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by H4miD on 5/31/16.
 * Group class :
 * containing :
 *             Name  (group name : berim estakhr )
 *             Location ( heading to .. !)
 *             User ( users)
 *             Owner ( started by ... super user some how ... he can delete every thing in the group)
 *             PublicGroup ( is it public or not ,  public -> every one can see it, private -> only Owner can add )
 *
 */
public class Group {
    private static final MainApp mainModel  = (MainApp) MainApp.getContext();

    String name ;
    LatLng location;
    ArrayList<User> users;
    String owner;
    Boolean publicGroup;

    public Group(String name,User owner,LatLng location,Boolean publicGroup){           // group constructor
        this.name = name;
        this.owner = mainModel.logedInUserName;
        this.location = location;
        users = new ArrayList<>();
        this.publicGroup = publicGroup;

    }
    public Group(){                                                                     // non-argument constructor for saving

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public String getOwner() {
        return owner;
    }


    public Boolean getPublicGroup() {
        return publicGroup;
    }

    public void setPublicGroup(Boolean publicGroup) {
        this.publicGroup = publicGroup;
    }


    public String addUser(User user){                                           // no redundunt users are allowed
        if(users.contains(user)){
            return "user already exists";

        } else {
            users.add(user);
            return "successfully added";
        }
    }

}
