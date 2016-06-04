package com.hackatrip.iranmate.iranmate.main_page.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hackatrip.iranmate.iranmate.Model.Group;
import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.Model.Post;
import com.hackatrip.iranmate.iranmate.R;

import java.util.ArrayList;

/**
 * Adapter for showing the groups
 * it takes the groups from MainApp module
 * creates group n  views   n : groups.size
 * groups = > arraylist
 */
public class CustomGroupAdapter extends BaseAdapter {

    private static final MainApp mainModel  = (MainApp) MainApp.getContext();
    Context context;
    ArrayList<Group> groups;                                                                        // group to be shown
    private static LayoutInflater inflater=null;

    public CustomGroupAdapter(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.groups = mainModel.getGroups();
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if(groups==null){
            return 0;
        }
        return groups.size();
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
    // each group view contains :
    public class Holder {
        TextView groupNameTextView;
        TextView groupHeadToTextView;
        TextView groupStateTextView;
        ImageButton groupImageButton;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
                                                                                                     // problem : after retriving groups from backend it return null

        Group selectedGroup = groups.get(position);

        String groupState = "";
        if(selectedGroup.getPublicGroup()){                                                          // ** null problem
            groupState = "Public";
        } else {
            groupState = "Private";
        }
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.group_layout, null);


        holder.groupNameTextView = (TextView) rowView.findViewById(R.id.group_name_textview);
        holder.groupStateTextView = (TextView) rowView.findViewById(R.id.state_textview);
        holder.groupHeadToTextView = (TextView) rowView.findViewById(R.id.head_to_textview);
        holder.groupImageButton = (ImageButton) rowView.findViewById(R.id.group_imageButton);
        holder.groupNameTextView.setText(selectedGroup.getName());                                 // ** null problem
        holder.groupStateTextView.setText(groupState);
        //holder.groupHeadToTextView.setText(selectedGroup.getLocation().toString());


                                                                                                     // every thing in group object is clickable

        holder.groupImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.groupNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.groupHeadToTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.groupStateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rowView;
    }

}
