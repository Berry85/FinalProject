package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.model.Data;

public class edit extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText1, editText2, editText3, editText4;
    Button button;
    private Data data;
    private final static int RESULT_LOAD_IMAGE = 203;
    private String imagePATH;

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
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
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
                data = new Data(name, phone, false, email, note, imagePATH);
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
        imagePATH = data.getImagePath();
        imageView.setImageBitmap(BitmapFactory.decodeFile(imagePATH));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Uri selectImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String PicturePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(PicturePath);
                imageView.setImageBitmap(bitmap);
                imagePATH = PicturePath;

                Log.d("abc", PicturePath);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
