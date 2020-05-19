package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.model.Data;

public class display extends AppCompatActivity {
    private Button button2;//编辑
    private ImageView imageView;
    private TextView textView9, textView10, textView11, textView12;
    private Data data;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        button2 = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView3);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);

        //接受数组
        Intent intent = this.getIntent();
        final Bundle bundle = intent.getExtras();
        final Data data = (Data) bundle.getSerializable("List");

        final String name = data.getName();
        final String phone = data.getPhone();
        final String email = data.getEmail();
        final String Note = data.getNote();

        textView9.setText(name);
        textView10.setText(phone);
        textView11.setText(email);
        textView12.setText(Note);

        //编辑button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                Data data1 = new Data(name, phone, false, email, Note);
                bundle.putSerializable("data", data1);
                intent.putExtras(bundle);
                intent.setClass(display.this, edit.class);
                startActivity(intent);
            }
        });

        //点击电话跳转至拨号
        textView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_DIAL);
                String tel = data.getPhone();
                String str = "tel:" + tel;
                intent1.setData(Uri.parse(str));
                startActivity(intent1);
            }
        });

        //邮件
        textView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setAction(Intent.ACTION_SEND);
                String mail = data.getEmail();
                intent2.putExtra(Intent.EXTRA_EMAIL, mail);
                intent2.putExtra("subject", "");
                intent2.putExtra(Intent.EXTRA_TEXT, "");
                intent2.setType("text/plain");
                startActivity(intent2);
            }
        });


    }


}
