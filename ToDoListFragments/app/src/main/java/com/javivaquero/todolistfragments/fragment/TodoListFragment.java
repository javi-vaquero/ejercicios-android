package com.javivaquero.todolistfragments.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.javivaquero.todolistfragments.DetailActivity;
import com.javivaquero.todolistfragments.R;
import com.javivaquero.todolistfragments.adapter.ToDoAdapter;
import com.javivaquero.todolistfragments.model.ToDo;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class TodoListFragment extends ListFragment implements InputFragment.TODOItemListener{

    private ArrayList<ToDo> todos;
    private ArrayAdapter<ToDo> aa;

    private final String TODOS_KEY = "TODOS";
    public static final String TODO_ITEM = "TODO_ITEM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        todos = new ArrayList<>();
        aa = new ToDoAdapter(getActivity(), R.layout.todo_list_item, todos);

        if(savedInstanceState!=null){
            ArrayList<ToDo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);
            todos.addAll(tmp);
        }

        setListAdapter(aa);

        return layout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
        ToDo todo = todos.get(position);
        detailIntent.putExtra("TODO_ITEM", todo);
        startActivity(detailIntent);
    }





    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TODOS_KEY, todos);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void addTodo(ToDo todo) {
        todos.add(0,todo);
        aa.notifyDataSetChanged();
    }
}