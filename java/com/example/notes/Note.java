package com.example.notes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(tableName = "NOTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String note;

    public Note(String title, String note) {
        this.title = title;
        this.note = note;
    }

    @Override
    public String toString() {
        return title;
    }


}
