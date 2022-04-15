package com.example.contact_room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    Button btnSave;
    EditText editName, editJob, editEmail,editPhone;

    List<Contact> dataList=new ArrayList<>();
    RoomDB database;
    FloatingActionButton home_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        editName=findViewById(R.id.editName);
        editJob=findViewById(R.id.editJob);
        editEmail=findViewById(R.id.editEmail);
        editPhone=findViewById(R.id.editPhone);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        home_btn=findViewById(R.id.home_btn);
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String job = editJob.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                if (!name.equals("")&& !phone.equals("")) {
                    Contact c = new Contact(name,email,job,phone);
                    database.mainDao().insert(c);
                    editEmail.setText("");editName.setText("");editJob.setText("");editPhone.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    //adapter.notifyDataSetChanged();
                }
            }
        });
    }

}
