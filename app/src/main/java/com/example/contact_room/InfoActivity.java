package com.example.contact_room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {
    FloatingActionButton delete_btn, edit_btn;
    TextView name,phone, email, job;
    List<Contact> dataList=new ArrayList<>();
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();

        name=findViewById(R.id.textname);
        phone=findViewById(R.id.textphone);
        email=findViewById(R.id.textemail);
        job=findViewById(R.id.textjob);

        name.setText(getIntent().getStringExtra("name"));
        phone.setText(getIntent().getStringExtra("phone"));
        job.setText(getIntent().getStringExtra("job"));
        email.setText(getIntent().getStringExtra("email"));

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+getIntent().getStringExtra("phone")));
                startActivity(intent);
            }
        });

        delete_btn=findViewById(R.id.delete_btn);
        edit_btn=findViewById(R.id.edit_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, UpdateActivity.class);
                intent.putExtra("id",getIntent().getStringExtra("id"));
                intent.putExtra("name",name.getText().toString());
                intent.putExtra("phone",phone.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("job",job.getText().toString());
                startActivity(intent);
            }
        });
    }
}
