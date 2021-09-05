package com.example.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.adpater.DataAdpater;
import com.example.finalproject.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.TextView.OnClickListener;


public class MainActivity extends AppCompatActivity {
    private List<Data> dataList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int id;
    private FloatingActionButton floatingActionButton;
    private static final int WRITE_PERMISSION = 0X01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        Data data = new Data("橘喵喵", "11111", false, "at3ith@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/5.jpg");
        dataList.add(data);
        data = new Data("happy", "1214", true, "happy@gmail.com", "", "/storage/emulated/0/DCIM//喀蓝一家/2.jpg");
        dataList.add(data);
        data = new Data("Kevin", "7834", false, "Kevin@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/3.jpg");
        dataList.add(data);
        data = new Data("GREEN", "5438", false, "GREEN@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/4.jpg");
        dataList.add(data);
        data = new Data("Good", "78678", false, "Good@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/5.jpg");
        dataList.add(data);
        data = new Data("David", "12345", false, "David@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/6.jpg");
        dataList.add(data);
        data = new Data("WARRAN", "798481", false, "WARRAN@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/7.jpg");
        dataList.add(data);
        data = new Data("Rainbow", "1496489", false, "Rainbow@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/8.jpg");
        dataList.add(data);
        data = new Data("Black", "7834453", false, "Black@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/9.jpg");
        dataList.add(data);
        data = new Data("Blue", "7867676", false, "Blue@gmail.com", "", "/storage/emulated/0/DCIM/喀蓝一家/10.jpg");
        dataList.add(data);


        recyclerView = findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new DataAdpater(dataList);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.notifyDataSetChanged();


        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, add.class);
                startActivityForResult(intent, 200);
            }
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(dataList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(dataList, i, i - 1);
                    }
                }
                recyclerViewAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                dataList.remove(position);
                recyclerViewAdapter.notifyItemRemoved(position);
            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (resultCode) {
            case 200:
                Data data1 = (Data) data.getExtras().getSerializable("data");
                dataList.add(data1);
                recyclerViewAdapter.notifyItemInserted(dataList.size());
                break;
            case 201:
                Data data2 = (Data) data.getExtras().getSerializable("data2");
                int position = data.getIntExtra("position", 0);
                dataList.set(position, data2);
                recyclerViewAdapter.notifyItemChanged(position, data2);
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_action);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextChange(query);
                if (id == -1) {
                    Toast.makeText(getApplicationContext(), "NOT FOUND!!!", Toast.LENGTH_SHORT).show();
                } else {
                    recyclerView.scrollToPosition(id);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                id = search(newText, dataList);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public int search(String name, List list) {
        Pattern pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = pattern.matcher(((Data) list.get(i)).getName());
            if (matcher.matches()) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == WRITE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permission", "Write Permission Failed");
                Toast.makeText(this, "You must allow permission", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}


