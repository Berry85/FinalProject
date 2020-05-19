package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);

        getData();
        final String name = editText1.getText().toString();
        final String phone = editText2.getText().toString();
        final String email = editText3.getText().toString();
        final String Note = editText4.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                data = new Data(name, phone, false, email, Note);
                bundle.putSerializable("data1", data);
                intent.putExtras(bundle);
                intent.setClass(edit.this, MainActivity.class);
                startActivity(intent);
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

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.back:
//                if (editText1.getText() == null | editText2.getText() == null) {
//                    builder = new AlertDialog.Builder(edit.this);
//                    builder.setTitle("Warning!");
//                    builder.setIcon(R.drawable.ic_warning_black_24dp);
//                    builder.setMessage("你尚未保存!");
//
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent();
//                            intent.setClass(edit.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    });
//
//                    builder.setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                        }
//                    });
//                    builder.show();
//                }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
