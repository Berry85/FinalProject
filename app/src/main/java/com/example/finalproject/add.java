package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.model.Data;

public class add extends AppCompatActivity {
    Button button;
    EditText editText1, editText2, editText3, editText4;
    ImageView imageView;

    private final static int RESULT_LOAD_IMAGE = 203;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        editText1 = findViewById(R.id.editText5);
        editText2 = findViewById(R.id.editText6);
        editText3 = findViewById(R.id.editText7);
        editText4 = findViewById(R.id.editText8);
        button = findViewById(R.id.button10);
        imageView = findViewById(R.id.imageView5);

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
                String name = editText1.getText().toString();
                String phone = editText2.getText().toString();
                String email = editText3.getText().toString();
                String note = editText4.getText().toString();

                if (name.trim().isEmpty()) {
                    Toast.makeText(add.this, "你的名字为空", Toast.LENGTH_SHORT).show();
                    Log.d("abc", name);
                    return;
                } else if (phone.trim().isEmpty()) {
                    Toast.makeText(add.this, "你的电话为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.trim().isEmpty()) {
                    Toast.makeText(add.this, "你的邮箱为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (imagePath == null) {
                    Toast.makeText(add.this, "你的图片尚未选择", Toast.LENGTH_SHORT).show();
                    return;
                }

                Data data = new Data(name, phone, false, email, note, imagePath);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                intent.putExtras(bundle);
                setResult(200, intent);
                finish();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(add.this);
                builder.setMessage("放弃编辑？");
                builder.setTitle("WARNING");
                builder.setIcon(getResources().getDrawable(R.drawable.ic_warning_black_24dp));
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_back, menu);
        return super.onCreateOptionsMenu(menu);
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
                imagePath = PicturePath;

                Log.d("abc", PicturePath);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
