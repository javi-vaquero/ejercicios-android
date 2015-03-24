package com.javivaquero.intentapplication;

import android.content.Intent;
import android.net.Uri;
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


public class SecondaryActivity extends ActionBarActivity {

    private EditText txtMessage;
    private Button btnSend;
    private TextView lblReceived;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        lblReceived = (TextView) findViewById(R.id.lblReceived);
        btnClose = (Button) findViewById(R.id.btnClose);

        addListeners();

        processIntent();
    }

    private void processIntent() {
        Intent data = getIntent();
        String msg = data.getStringExtra(MainActivity.MESSAGE);
        lblReceived.setText(msg);
    }

    private void addListeners() {

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = txtMessage.getText().toString();
                if(msg.length()>0) {
                    Intent result = new Intent();
                    result.putExtra(MainActivity.MESSAGE, msg);
                    setResult(RESULT_OK, result);
                    finish();
                }
                else{
                    Toast toast = Toast.makeText(SecondaryActivity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                }

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }


}
