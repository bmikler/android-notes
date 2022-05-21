package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {

    private RoomNoteDatabase db = RoomNoteDatabase.getDatabase(this);
    private NoteDao noteDao = db.noteDao();
    private TextView titleView;
    private TextView noteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent.getExtras() == null) {

            setContentView(R.layout.activity_note_new);

            titleView = findViewById(R.id.title);
            noteView = findViewById(R.id.note);

            createNewNote();

        } else {

            setContentView(R.layout.activity_note_edit);

            titleView = findViewById(R.id.title);
            noteView = findViewById(R.id.note);

            String id = intent.getStringExtra("id");
            String title = intent.getStringExtra("title");
            String noteText = intent.getStringExtra("note");

            System.out.println(id + title + noteText);

            Note note = new Note(Long.parseLong(id), title, noteText);

            editNote(note);

        }

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(this::back);

    }

    private void createNewNote() {

        Button createNote = findViewById(R.id.save_button);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                back(v);
            }
        });

    }

    private void editNote(Note note) {

        titleView.setText(note.getTitle());
        noteView.setText(note.getNote());

        Button createNote = findViewById(R.id.save_button);
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNote(note.getId());
                back(v);
            }
        });

        Button deleteButton = findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(note);
                back(v);
            }
        });
    }

    private void updateNote(long id) {
        CharSequence title = titleView.getText();
        CharSequence note = noteView.getText();
        RoomNoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.update(new Note(id, title.toString(), note.toString()));
        });
    }

    private void save() {
        CharSequence title = titleView.getText();
        CharSequence note = noteView.getText();
        RoomNoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.insert(new Note(title.toString(), note.toString()));
        });
    }

    private void delete(Note note) {
        RoomNoteDatabase.databaseWriteExecutor.execute(() -> {
            noteDao.delete(note);
        });
    }

    private void back(View v) {
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }
}
