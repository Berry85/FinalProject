package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.floatingActionButton);

        Data data = new Data("橘喵喵", "11111111111", false, "at3ith@gmail.com", "压塌坑的橘喵喵", "/storage/emulated/0/Pictures/喀蓝一家/1.jpg");
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

}


