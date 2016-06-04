package com.hackatrip.iranmate.iranmate.Model;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by H4miD on 6/1/16.
 * The main model
 * it add new posts
 * it add new groups
 * it manage backend services
 *
 */
public class MainApp extends Application {
    static Context mContext ;

    // backend properties

    String appId = "2E070F55-9CAF-D4AA-FFD7-476B7130EB00";
    String secretKey = "5E7D138B-04A9-2215-FFB1-3EC67F266F00";
    ArrayList<Group> groups ;
    ArrayList<Post> posts;
    User logedInUser;
    BackendlessUser backendLogedInUser;             // loged in user
    String logedInUserName;


    public static Context getContext() {
        return mContext;
    }



    @Override
    public void onCreate() {
        mContext = getApplicationContext();
        super.onCreate();
        String appVersion = "v1";

                                                                                    // for local
        //com.backendless.Backendless.setUrl( "http://172.19.138.16:8080/api" );


        Backendless.initApp(this, appId, secretKey, appVersion);                     // initializing for the first time
        groups = new ArrayList<>();
        posts = new ArrayList<>();

    }

    public BackendlessUser getBackendLogedInUser() {
        return backendLogedInUser;
    }

    public void setBackendLogedInUser(BackendlessUser logedInUser) {
        this.backendLogedInUser = logedInUser;
    }


    public void setLogedInUser(User logedInUser) {
        this.logedInUser = logedInUser;
    }

    public User getLogedInUser(){
        return logedInUser;
    }




                                                                                                     // add new group to the backend
    public void addNewGroup(String name, User user,LatLng location,Boolean isPublic){

        Group newGroup = new Group(name,null,null,isPublic);

        Backendless.Persistence.save( newGroup, new AsyncCallback<Group>() {
            public void handleResponse( Group response )
            {
                Toast.makeText(MainApp.this,"shod",Toast.LENGTH_SHORT).show();
                // new Contact instance has been saved
            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(MainApp.this,fault.getMessage(),Toast.LENGTH_SHORT).show();
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
        groups.add(newGroup);
    }

                                                                                                 // add new post to the backend
    public void addNewPost(Bitmap postPhoto,ArrayList<User> addedUsers,String postDiscription){
        Post newPost = new Post(null,null,null,postDiscription);
        Backendless.Persistence.save( newPost, new AsyncCallback<Post>() {
            public void handleResponse( Post response )
            {

            }

            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(MainApp.this,fault.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        posts.add(newPost);

    }
                                                                                                // add signed up user to backend
    public void addNewUser(User user){
        Backendless.Persistence.save(user, new AsyncCallback<User>() {
            @Override
            public void handleResponse(User user) {

            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(MainApp.this, "Error accorded in registering", Toast.LENGTH_SHORT).show();
            }
        });
    }

                                                                                                // get all the groups made from backend
    public ArrayList getGroups(){
        Backendless.Persistence.of( Group.class).find(new AsyncCallback<BackendlessCollection<Group>>() {
            @Override
            public void handleResponse(BackendlessCollection<Group> foundGroups) {
                groups = new ArrayList<Group>(foundGroups.getData());


            }

            @Override
            public void handleFault(BackendlessFault fault) {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                //Toast.makeText(MainApp.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return groups;
    }


                                                                                                // suppose to get posts from backend
    public ArrayList getPosts(){

        Backendless.Persistence.of(Post.class).find(new AsyncCallback<BackendlessCollection<Post>>() {
            @Override
            public void handleResponse(BackendlessCollection<Post> postBackendlessCollection) {
                //returned posts
                posts = new ArrayList<Post>(postBackendlessCollection.getData());

            }

            @Override
            public void handleFault(BackendlessFault fault) {

                // an error accorded .. print the error
                //Toast.makeText(MainApp.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return posts;
    }
    public void setUserName(String name){
        this.logedInUserName =name;
    }

}
