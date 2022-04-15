package com.example.contact_room;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {

    FloatingActionButton add_btn;
    RecyclerView recyclerView;

    List<Contact> dataList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        database = RoomDB.getInstance(this);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dataList.clear();
        dataList.addAll(database.mainDao().getAll());
        adapter = new MainAdapter(dataList, MainActivity.this,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        add_btn=findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });




        return super.onCreateOptionsMenu(menu);
    }

    

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
        intent.putExtra("id",dataList.get(position).getId());
        intent.putExtra("name",dataList.get(position).getName());
        intent.putExtra("phone",dataList.get(position).getPhone());
        intent.putExtra("email",dataList.get(position).getEmail());
        intent.putExtra("job",dataList.get(position).getJob());
        startActivity(intent);
    }
}