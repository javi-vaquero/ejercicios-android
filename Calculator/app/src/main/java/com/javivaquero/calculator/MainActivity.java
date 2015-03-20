package com.javivaquero.calculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.javivaquero.calculator.listeners.NumberOnClickListener;
import com.javivaquero.calculator.listeners.OperationOnClickListener;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements NumberOnClickListener.NumberListenerInterface {



    TextView opText;
    ArrayList<Button> numberButtons;
    ArrayList<Button> operationButtons;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        opText = (TextView) findViewById(R.id.opText);
        getButtonsFromLayout();
        addEventListeners();

        /*btn0 = (Button) findViewById(R.id.btn_0);
        btn1 = (Button) findViewById(R.id.btn_1);
        btn2 = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);
        btn4 = (Button) findViewById(R.id.btn_4);
        btn5 = (Button) findViewById(R.id.btn_5);
        btn6 = (Button) findViewById(R.id.btn_6);
        btn7 = (Button) findViewById(R.id.btn_7);
        btn8 = (Button) findViewById(R.id.btn_8);
        btn9 = (Button) findViewById(R.id.btn_9);
        btnEq = (Button) findViewById(R.id.btn_eq);
        btnDot = (Button) findViewById(R.id.btn_dot);
        btnDiv = (Button) findViewById(R.id.btn_div);
        btnTimes = (Button) findViewById(R.id.btn_times);
        btnPlus = (Button) findViewById(R.id.btn_plus);
        btnMinus = (Button) findViewById(R.id.btn_minus);
*/


    }

    private void getButtonsFromLayout(){
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Dot"};
        String id;
        Button btn;
        for(int i=0; i<numbers.length;i++){
            id="btn".concat(numbers[i]);
            btn = (Button) findViewById(getResources().getIdentifier(id, "id", getPackageName()));
            numberButtons.add(btn);
        }

        String[] symbols = {"Plus", "Minus", "Times", "Div", "Eq"};
        for(int i=0; i<symbols.length;i++){
            id="btn".concat(symbols[i]);
            btn = (Button) findViewById(getResources().getIdentifier(id, "id", getPackageName()));
            operationButtons.add(btn);
        }


    }



    private void addEventListeners(){

        NumberOnClickListener noc = new NumberOnClickListener(this);
        OperationOnClickListener ooc = new OperationOnClickListener();

        for (int i=0;i<numberButtons.size();i++){
            numberButtons.get(i).setOnClickListener(noc);
        }
        for (int i=0;i<operationButtons.size();i++){
            operationButtons.get(i).setOnClickListener(ooc);
        }

/*
        btn0.setOnClickListener(but);
        btn1.setOnClickListener(but);
        btn2.setOnClickListener(but);
        btn3.setOnClickListener(but);
        btn4.setOnClickListener(but);
        btn5.setOnClickListener(but);
        btn6.setOnClickListener(but);
        btn7.setOnClickListener(but);
        btn8.setOnClickListener(but);
        btn9.setOnClickListener(but);
        btnEq.setOnClickListener(but);
        btnDot.setOnClickListener(but);
        btnDiv.setOnClickListener(but);
        btnTimes.setOnClickListener(but);
        btnPlus.setOnClickListener(but);
        btnMinus.setOnClickListener(but);*/
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setNumber(String l){

    }
}
