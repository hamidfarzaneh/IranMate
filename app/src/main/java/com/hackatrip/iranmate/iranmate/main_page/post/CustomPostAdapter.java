package com.hackatrip.iranmate.iranmate.main_page.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.Model.Post;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.MainActivity;

import java.util.ArrayList;

/**
 * Created by H4miD on 5/31/16.
 */
public class CustomPostAdapter extends BaseAdapter {
    private static final MainApp mainModel  = (MainApp) MainApp.getContext();                       // link to the application module

    Context context;
    ArrayList<Post> posts;                                                                          // posts to be shown

    private static LayoutInflater inflater=null;
    public CustomPostAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.posts = mainModel.getPosts();
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(posts==null){
            return 0;
        }
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {                                                                           // every each post
        TextView userPostTextView;
        TextView postLittleDiscritionTextView;
        ImageButton postImageButton;
        ImageButton postLikeImageButton;
        ImageButton postCommentImageButton;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Post selectedPost = posts.get(position);                                                    // selected post to be shown
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.post_layout, null);

        holder.userPostTextView = (TextView) rowView.findViewById(R.id.username_show_post_textview);
        holder.postLittleDiscritionTextView = (TextView) rowView.findViewById(R.id.little_post_discription);
        holder.postImageButton = (ImageButton) rowView.findViewById(R.id.post_imageButton);
        holder.postLikeImageButton = (ImageButton) rowView.findViewById(R.id.like_imageButton);
        holder.postCommentImageButton = (ImageButton) rowView.findViewById(R.id.comment_imageButton);
        //holder.userPostTextView.setText(selectedPost.getCreator().getUsername());                 // null problem
        holder.postLittleDiscritionTextView.setText(selectedPost.getPostDiscription().split("\n")[0]);                  // just the first line
        //holder.postImageButton.setImageBitmap(selectedPost.getPostPhoto());

        holder.postImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                            // null on posts .. should implement it
                // TODO Auto-generated method stub
                MainActivity mainActivity = (MainActivity)context;
                mainActivity.showPost(null);

            }
        });
        holder.postLikeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.postCommentImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rowView;
    }
}
