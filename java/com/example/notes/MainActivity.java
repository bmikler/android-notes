package com.example.notes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView list;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);

        RoomNoteDatabase db = RoomNoteDatabase.getDatabase(this);
        NoteDao noteDao = db.noteDao();

        LiveData<List<Note>> allNotes = noteDao.getAll();
        allNotes.observe(this, n -> {
            n.forEach(
                    w-> Log.d("DB", w.toString())
            );
            ArrayAdapter<Note> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, n);
            list.setAdapter(adapter);
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = (Note) parent.getItemAtPosition(position);

                long noteId = selectedNote.getId();
                String title = selectedNote.getTitle();
                String note = selectedNote.getNote();

                Intent intent = new Intent(view.getContext(), NoteActivity.class);
                intent.putExtra("id", String.valueOf(noteId));
                intent.putExtra("title", title);
                intent.putExtra("note", note);
                startActivity(intent);

            }
        });


        Button create = findViewById(R.id.create_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteActivity.class);
                startActivity(intent);
            }
        });

    }
}