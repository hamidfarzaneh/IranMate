package com.hackatrip.iranmate.iranmate.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.Model.Post;
import com.hackatrip.iranmate.iranmate.main_page.post.NewPostActivity;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.post.ShowPostActivity;
import com.hackatrip.iranmate.iranmate.first_page.FirstPageActivity;
import com.hackatrip.iranmate.iranmate.main_page.group.CreateGroupActivity;
import com.hackatrip.iranmate.iranmate.main_page.group.CustomGroupAdapter;
import com.hackatrip.iranmate.iranmate.main_page.post.CustomPostAdapter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final MainApp mainModel  = (MainApp) MainApp.getContext();                         // link to the application class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                                                           // a dialog should be shown :
                                                                                                    // make post - make group
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


                                                                                                    // first thing that will be shown in application startup
                                                                                                    // are the posts
        getPostsMain();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {

        } else if (id == R.id.action_logout){                                                   // log out
            Backendless.UserService.logout(new AsyncCallback<Void>() {
                @Override
                public void handleResponse(Void aVoid) {
                    Intent firstPageIntent = new Intent(MainActivity.this, FirstPageActivity.class);

                    firstPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    firstPageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(firstPageIntent);

                }
                @Override
                public void handleFault(BackendlessFault backendlessFault) {
                    ;
                }
            });



        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_group) {
            Intent createGroupIntent = new Intent(this, CreateGroupActivity.class);
            startActivity(createGroupIntent);
        } else if (id == R.id.nav_new_post){
            Intent newPostIntent = new Intent(this, NewPostActivity.class);
            startActivity(newPostIntent);
        } else if (id == R.id.nav_show_map) {
            Intent showMapIntent = new Intent(this, ShowMapActivity.class);
            startActivity(showMapIntent);

        } else if (id == R.id.nav_posts) {
            getPostsMain();
        } else if (id == R.id.nav_groups) {
            getGroupsMain();
        } else if (id == R.id.nav_setting){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void getPostsMain(){                                                                    // after show posts button clicked on drawer
                                                                                                    // this will run -> getting posts from backend (in adapter)


        CustomPostAdapter postAdapter = new CustomPostAdapter(this);

        ListView listView = (ListView)findViewById(R.id.mainPageListView);
        listView.setAdapter(postAdapter);

    }
    private void getGroupsMain(){
                                                                                                    // after show group button clicked on drawer
                                                                                                    // this will run -> getting groups from backend (in adapter)

        CustomGroupAdapter groupAdapter = new CustomGroupAdapter(this);

        ListView listView = (ListView)findViewById(R.id.mainPageListView);
        listView.setAdapter(groupAdapter);

    }
    public void showPost(Post post){                                                                // first thing that will be shown in application startup
                                                                                                    // are the posts
        Intent showPostIntent = new Intent(this, ShowPostActivity.class);
        startActivity(showPostIntent);
    }


}
