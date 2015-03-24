package com.javivaquero.intentapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText txtMessage;
    private Button btnSend;
    private TextView lblReceived;
    private ImageView imgPhoto;
    private Button btnPhoto;

    public static final String MESSAGE = "MSG";
    private final int REQUEST_STRING = 0;
    private final int REQUEST_PHOTO = 1;

    private final String SAVE_LABEL = "LABEL";
    private final String SAVE_BITMAP = "BITMAP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        lblReceived = (TextView) findViewById(R.id.lblReceived);
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);

        if(savedInstanceState!=null){
            String label = savedInstanceState.getString(SAVE_LABEL);
            if (label!=null) {
                lblReceived.setText(label);
            };
            Bitmap bitmap = savedInstanceState.<Bitmap>getParcelable(SAVE_BITMAP);
            if (bitmap!=null){
                imgPhoto.setImageBitmap(bitmap);
            }
        }

        addListeners();
    }

    private void addListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = txtMessage.getText().toString();
                if(msg.length()>0) {
                    Intent act2 = new Intent(MainActivity.this, SecondaryActivity.class);
                    act2.putExtra(MESSAGE, txtMessage.getText().toString());
                    startActivityForResult(act2, REQUEST_STRING);
                    txtMessage.setText("");
                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (photo.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(photo, REQUEST_PHOTO);
                }
                else{
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.string.no_camera), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            switch(requestCode)	{
                case(REQUEST_STRING):
                    lblReceived.setText(data.getStringExtra(MainActivity.MESSAGE));
                    break;

                case(REQUEST_PHOTO):
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    imgPhoto.setImageBitmap(imageBitmap);
                    break;

                default:
                    break;

            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String label = lblReceived.getText().toString();
        if(label!="") {
            outState.putString(SAVE_LABEL, label);
        };
        if(imgPhoto.getDrawable()!=null){
            Bitmap bitmap = ((BitmapDrawable) imgPhoto.getDrawable()).getBitmap();
            outState.putParcelable(SAVE_BITMAP, bitmap);
        }
        super.onSaveInstanceState(outState);
    }
}
