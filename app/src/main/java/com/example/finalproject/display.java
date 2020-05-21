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
    private int position;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        imageView = findViewById(R.id.imageView4);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_display);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        data = (Data) this.getIntent().getExtras().getSerializable("data2");
        position = this.getIntent().getIntExtra("position", 0);

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
                break;
            case android.R.id.home:
                Intent intent2 = new Intent();
                String name2, phone2, email2, Note2;
                boolean favorite2;
                name2 = this.textView9.getText().toString();
                phone2 = this.textView10.getText().toString();
                email2 = this.textView11.getText().toString();
                Note2 = this.textView12.getText().toString();
                favorite2 = favorite;
                Data data2 = new Data(name2, phone2, favorite2, email2, Note2);
                Bundle bundle2 = new Bundle();
                Log.d("abc", data2.toString());
                bundle2.putSerializable("data2", data2);
                intent2.putExtra("position", position);
                intent2.putExtras(bundle2);
                setResult(201, intent2);
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 100) {
            Data data3 = (Data) data.getExtras().getSerializable("data1");
            textView9.setText(data3.getName());
            textView10.setText(data3.getPhone());
            textView11.setText(data3.getEmail());
            textView12.setText(data3.getNote());
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
