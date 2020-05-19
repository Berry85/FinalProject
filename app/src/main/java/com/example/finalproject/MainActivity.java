package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalproject.adpater.DataAdpater;
import com.example.finalproject.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.TextView.*;

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
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        floatingActionButton = findViewById(R.id.floatingActionButton);

//        initData(dataList);
        Data data = new Data("DAVID", "124512", false, "at3ith@gmail.com", "Sun");
        dataList.add(data);

        recyclerView = findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new DataAdpater(dataList);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerViewAdapter.notifyDataSetChanged();

//        recyclerViewAdapter.getItemId();
        recyclerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, display.class);
                intent.putExtra("dataList", (Serializable) dataList);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, add.class);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.search_action);
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setQuery("New Item", false);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//
//            public boolean onQueryTextSubmit(String query) {
//                onQueryTextChange(query);
//                if (id == -1) {
//                    Toast.makeText(getApplicationContext(), "NOT FOUND!!!", Toast.LENGTH_SHORT).show();
//                } else {
//                    recyclerView.scrollToPosition(id);
//                }
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                id = search(newText, dataList);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        Menu menu = toolbar.getMenu();
//        MenuItem searchItem = menu.findItem(R.id.search_action);
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        String str;
//        str = searchView.getQuery().toString();
//        return true;
//    }


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

    public void DataOnClicked(View view) {
        int id = view.getId();
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, display.class);
        intent.putExtra("dataList", (Serializable) dataList);
        intent.putExtra("id", id);
        startActivity(intent);
    }


}


