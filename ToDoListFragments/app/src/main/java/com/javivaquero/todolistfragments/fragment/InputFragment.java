package com.javivaquero.todolistfragments.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.javivaquero.todolistfragments.R;
import com.javivaquero.todolistfragments.model.ToDo;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {
    private Button btnAdd;
    private TextView txtView;
    private TODOItemListener target;
    public interface TODOItemListener{
        public void addTodo(ToDo todo);
    }
    public InputFragment() {
// Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View layout= inflater.inflate(R.layout.fragment_input, container, false);
        btnAdd= (Button) layout.findViewById(R.id.btnAdd);
        txtView=(TextView)layout.findViewById(R.id.txtView);
        addEventListeners();
        return layout;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            this.target=(TODOItemListener) activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.toString()+" must implement TODOItemListener");
        }
    }
    private void addEventListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDo todo = new ToDo(txtView.getText().toString());
                txtView.setText("");

                target.addTodo(todo);

            }
        });
    }
}