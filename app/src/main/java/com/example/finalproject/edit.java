package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.model.Data;

public class edit extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText1, editText2, editText3, editText4;
    private int Text2;
    AlertDialog.Builder builder;
    Button button;
    private Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        imageView = findViewById(R.id.imageView3);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        button = findViewById(R.id.button);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);

        getData();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                String name, phone, email, note;
                name = editText1.getText().toString();
                phone = editText2.getText().toString();
                email = editText3.getText().toString();
                note = editText4.getText().toString();
                data = new Data(name, phone, false, email, note);
                bundle.putSerializable("data1", data);
                intent.putExtras(bundle);
                setResult(100, intent);
                finish();
            }
        });


    }

    public void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Data data = (Data) bundle.getSerializable("data");
        editText1.setText(data.getName());
        editText2.setText(data.getPhone());
        editText3.setText(data.getEmail());
        editText4.setText(data.getNote());
    }

}
