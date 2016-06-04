package com.hackatrip.iranmate.iranmate.first_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hackatrip.iranmate.iranmate.R;


/**
 * Created by H4miD on 5/29/16.
 * adapter for first page slider
 * pics are in the imageId array
 *
 *
 */
public class CustomSlideShowAdapter extends PagerAdapter {
    Context context;                                                                                                    // link to the slider starter
    int[] imageId = {R.drawable.hafez,R.drawable.hamam,R.drawable.hamam2,R.drawable.nasir,R.drawable.teh};              // slider picks

    public CustomSlideShowAdapter(Context context){                                                                     // constructor
        super();
        this.context=context;
    }
    @Override
    public int getCount(){
        return imageId.length;
    }
    @Override
    public Object instantiateItem(ViewGroup container, final int position){                                             // make the view
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View viewItem = inflater.inflate(R.layout.slide_show_image_layout, container, false);
        ImageView imageView = (ImageView) viewItem.findViewById(R.id.imageView);
        imageView.setImageResource(imageId[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent activityHolderIntent = new Intent(context, ActivityHolder.class);
//                activityHolderIntent.putExtra("position",position);


            }
        });
        ((ViewPager) container).addView(viewItem);
        return viewItem;
    }
    @Override
    public boolean isViewFromObject(View view,Object object){
        return view== ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container,int position,Object object){
        ((ViewPager) container).removeView((View)object);
    }


}
