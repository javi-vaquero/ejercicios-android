package com.javivaquero.todolistfragments;

import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.javivaquero.todolistfragments.fragment.InputFragment;
import com.javivaquero.todolistfragments.model.ToDo;


public class MainActivity extends ActionBarActivity implements InputFragment.TODOItemListener {

    private final String TODO = "TODO";
    private InputFragment.TODOItemListener listFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            listFragment = (InputFragment.TODOItemListener) getFragmentManager().findFragmentById(R.id.listFragment);
        }
        catch(Exception e){
            throw new ClassCastException(this.toString() + "must implement TODOItemListener");
        }

    }


    @Override
    public void addTodo(ToDo todo) {
        listFragment.addTodo(todo);
    }
}
