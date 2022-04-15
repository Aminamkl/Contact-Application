package com.example.contact_room;





import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {
    @Insert(onConflict = REPLACE)
    void insert(Contact contact);

    @Delete
    void delete(Contact contact);

    @Delete
    void reset(List<Contact> contacts);

    @Query("UPDATE Contact SET name = :name and phone = :phone and job=:job and email=:email WHERE phone=:phone and name=:name")
    void update(String name, String phone, String job, String email);

    @Query("UPDATE Contact SET name = :name and phone = :phone and job=:job and email=:email WHERE id=:id")
    void update2(int id, String name, String phone, String job, String email);

    @Query("SELECT * FROM Contact")
    List<Contact> getAll();

    @Query("SELECT * FROM Contact where id= :id")
    Contact getById(int id);
}
