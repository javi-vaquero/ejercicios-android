package com.javivaquero.calculator.listeners;

import android.view.View;
import android.widget.Button;

import com.javivaquero.calculator.MainActivity;

/**
 * Created by cursomovil on 20/03/15.
 */
public class NumberOnClickListener implements View.OnClickListener {

    public interface NumberListenerInterface {
        public void setNumber(String number);
    }

    private NumberListenerInterface target;

    public  NumberOnClickListener(NumberListenerInterface target){
        this.target=target;
    }

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            String val = b.getText().toString();

            target.setNumber(b.getText().toString());


        }
 }