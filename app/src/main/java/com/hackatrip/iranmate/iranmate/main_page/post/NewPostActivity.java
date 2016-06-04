package com.hackatrip.iranmate.iranmate.main_page.post;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hackatrip.iranmate.iranmate.Model.MainApp;
import com.hackatrip.iranmate.iranmate.Model.User;
import com.hackatrip.iranmate.iranmate.R;
import com.hackatrip.iranmate.iranmate.main_page.MainActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewPostActivity extends AppCompatActivity {
    private static final MainApp mainModel  = (MainApp) MainApp.getContext();                       // link to application module
    // post items
    ImageButton postImageButton;
    ImageButton addUserToPostImageButton;
    TextView people;
    EditText postDiscription;

                                                                                                     //dialog items to be shown when on image button clicked
    String[] dialogItems = {"Take Picture","Choose from gallery"};
                                                                                                     // users tagged on the post
    ArrayList<User> addedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postImageButton = (ImageButton) findViewById(R.id.newPost_imagebutton);
        postDiscription = (EditText) findViewById(R.id.new_post_discription);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.postFab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();

            }
        });
        postImageButton.setOnClickListener(new View.OnClickListener() {                                 // choose picture dialog
            @Override
            public void onClick(View v) {
                AlertDialog.Builder cameraChoicesBuilder = new AlertDialog.Builder(NewPostActivity.this);
                cameraChoicesBuilder.setItems(dialogItems, choosePictureDialog);
                AlertDialog dialog = cameraChoicesBuilder.create();
                dialog.show();
            }
        });

    }

    public void createPost(){                                                                           // runs when a post is want to be created
        if(addedUsers == null){
            addedUsers = new ArrayList<User>();
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                mainModel.addNewPost(chosenImageBitmap,addedUsers,postDiscription.getText().toString());
            }
        };

        thread.start();
        Intent goToMainIntent = new Intent(NewPostActivity.this, MainActivity.class);
        goToMainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        goToMainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToMainIntent);

    }

    protected DialogInterface.OnClickListener choosePictureDialog = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                case 0 :
                    captureCameraImage();

                case 1:
                    takePictureFromGallery();


            }


        }
    };



                                                                                                    // *** chosen image part***
    private Uri tokenPictureUri;
    Bitmap chosenImageBitmap;
    private static final int CAMERA_PHOTO = 111;                                                    // camera photo request code
    private static final int GALLERY_PHOTO = 222;                                                   // gallery photo request code



    private void captureCameraImage() {                                                             // take photo from camera
        Intent chooserIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Date now = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);
        String path =File.separator;
        path = path+"IMG"+timeStamp + ".jpg";
        File f = new File(Environment.getExternalStorageDirectory(), path);
        chooserIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        tokenPictureUri = Uri.fromFile(f);
        startActivityForResult(chooserIntent, CAMERA_PHOTO);
    }


                                                                                                    // getting result from 2 way :
                                                                                                    // 1 ) camera
                                                                                                    // 2 ) gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PHOTO && resultCode == Activity.RESULT_OK) {
            if(tokenPictureUri != null){
                Uri selectedImage = tokenPictureUri;
                getContentResolver().notifyChange(selectedImage, null);
                chosenImageBitmap = getBitmap(tokenPictureUri.getPath());

                if(chosenImageBitmap != null){
                    postImageButton.setImageBitmap(chosenImageBitmap);
                }else{
                    Toast.makeText(this,"Error while capturing Image",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this,"Error while capturing Image",Toast.LENGTH_LONG).show();
            }
        }
        else if (requestCode == GALLERY_PHOTO && resultCode == Activity.RESULT_OK){
            tokenPictureUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(tokenPictureUri,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            chosenImageBitmap = BitmapFactory.decodeFile(picturePath);
            postImageButton.setImageBitmap(chosenImageBitmap);


        }
    }
    private Bitmap getBitmap(String path) {                                                         // reducing image size to prevent lag
                                                                                                    // taken from stackOverFlow

        Uri uri = Uri.fromFile(new File(path));
        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 1200000; // 1.2MP
            in = getContentResolver().openInputStream(uri);

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, o);
            in.close();


            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }
            Log.d("", "scale = " + scale + ", orig-width: " + o.outWidth + ", orig-height: " + o.outHeight);

            Bitmap b = null;
            in = getContentResolver().openInputStream(uri);
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = b.getHeight();
                int width = b.getWidth();
                Log.d("", "1th scale operation dimenions - width: " + width + ", height: " + height);

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(b, (int) x,
                        (int) y, true);
                b.recycle();
                b = scaledBitmap;

                System.gc();
            } else {
                b = BitmapFactory.decodeStream(in);
            }
            in.close();

            Log.d("", "bitmap size - width: " + b.getWidth() + ", height: " +
                    b.getHeight());
            return b;
        } catch (IOException e) {
            Log.e("", e.getMessage(), e);
            return null;
        }
    }

    public void takePictureFromGallery(){
        Intent choosePicture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePicture, GALLERY_PHOTO);
    }


}
