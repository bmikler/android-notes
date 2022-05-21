package com.example.notes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Optional;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM NOTE")
    LiveData<List<Note>> getAll();

    @Delete
    void delete(Note note);

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

}
