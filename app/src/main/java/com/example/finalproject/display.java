package com.example.finalproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.model.Data;

public class display extends AppCompatActivity {
    private Button button2;//编辑
    private ImageView imageView;
    private TextView textView9, textView10, textView11, textView12;
    private Data data;
    private boolean favorite;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        imageView = findViewById(R.id.imageView3);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);

        data = (Data) this.getIntent().getExtras().getSerializable("item");

        favorite = data.isFavorite();
        String name = data.getName();
        String phone = data.getPhone();
        String email = data.getEmail();
        String Note = data.getNote();

        textView9.setText(name);
        textView10.setText(phone);
        textView11.setText(email);
        textView12.setText(Note);


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        Menu menu = toolbar.getMenu();
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent();
                Bundle bundle1 = new Bundle();
                String name, phone, email, Note;
                boolean favorite1;
                name = textView9.getText().toString();
                phone = textView10.getText().toString();
                email = textView11.getText().toString();
                Note = textView12.getText().toString();
                favorite1 = favorite;
                Data data1 = new Data(name, phone, favorite1, email, Note);
                bundle1.putSerializable("data", data1);
                intent.putExtras(bundle1);
                intent.setClass(display.this, edit.class);
                startActivityForResult(intent, 100);

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 100) {

            Data data2 = (Data) data.getExtras().getSerializable("data1");
            Log.d("abc", data2.toString());
            textView9.setText(data2.getName());
            textView10.setText(data2.getPhone());
            textView11.setText(data2.getEmail());
            textView12.setText(data2.getNote());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
