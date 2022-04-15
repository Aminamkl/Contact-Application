package com.example.contact_room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    FloatingActionButton home_btn;
    Button update;
    EditText editName, editPhone, editEmail, editJob;
    List<Contact> dataList=new ArrayList<>();
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        editName=findViewById(R.id.editName);
        editPhone=findViewById(R.id.editPhone);
        editEmail=findViewById(R.id.editEmail);
        editJob=findViewById(R.id.editJob);
        update=findViewById(R.id.btnUpdate);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String job = intent.getStringExtra("job");

        editName.setText(name);
        editEmail.setText(email);
        editPhone.setText(phone);
        editJob.setText(job);

        home_btn=findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=Integer.parseInt(getIntent().getStringExtra("id"));
                String name = editName.getText().toString().trim();
                String job = editJob.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                if (!name.equals("")&& !phone.equals("")) {
                    database.mainDao().update(name,phone,job,email);
                    editEmail.setText("");editName.setText("");editJob.setText("");editPhone.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    //adapter.notifyDataSetChanged();
                }
            }
        });

    }
}
