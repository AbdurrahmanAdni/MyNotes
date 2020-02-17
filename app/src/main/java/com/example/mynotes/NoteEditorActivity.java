package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class NoteEditorActivity extends AppCompatActivity {

    String oldTitle;
    String oldText;
    String newTitle;
    String newText;
    Boolean isTitleChanged;
    Boolean isTextChanged;
    int option;
    int noteId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_editor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.save){

            Log.i("Menu Editor Toolbar", "Saving");

            //Kasus ketika add notes
            if (option == 1) {

                Log.i("Option", "Save for New Notes");

                if (isTitleChanged && isTextChanged) {

                    Log.i("Saving", "Title and text changed");

                    MainActivity.noteMap.put(newTitle, newText);
                    MainActivity.listOfTitle.add(newTitle);
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                } else if (isTitleChanged && !isTextChanged) {

                    Log.i("Saving", "Title and not(text changed)");
                    MainActivity.noteMap.put(newTitle, "");
                    MainActivity.listOfTitle.add(newTitle);
                    MainActivity.arrayAdapter.notifyDataSetChanged();

                }

            } else if (option == 2) {
                //Kasus ketika edit note yang telah ada

                Log.i("Option", "Save for Edited Notes");

              if (isTitleChanged && isTextChanged) {

                  Log.i("Saving", "Title and text changed");
                  MainActivity.noteMap.put(newTitle, newText);
                  MainActivity.listOfTitle.remove(noteId);
                  MainActivity.listOfTitle.add(newTitle);
                  MainActivity.arrayAdapter.notifyDataSetChanged();

              } else if (isTitleChanged && !isTextChanged) {

                  Log.i("Saving", "Title and not(text changed)");

                  MainActivity.noteMap.put(newTitle, oldText);
                  MainActivity.listOfTitle.remove(noteId);
                  MainActivity.listOfTitle.add(newTitle);
                  MainActivity.arrayAdapter.notifyDataSetChanged();

              } else if (!isTitleChanged && isTextChanged) {

                  Log.i("Saving", "!(Title) and text changed");
                  MainActivity.noteMap.put(oldTitle, newText);
                  MainActivity.listOfTitle.remove(noteId);
                  MainActivity.listOfTitle.add(oldTitle);
                  MainActivity.arrayAdapter.notifyDataSetChanged();


              }

            }

            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.notes){

            Log.i("Menu Editor Toolbar", "Notes");

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //Inisialisasi isTitleChaged = false
        isTitleChanged = false;
        //Inisialisasi isTextChanged = false
        isTextChanged = false;


        //Integrasi Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //EditText
        EditText titleField = (EditText) findViewById(R.id.title);
        EditText textField = (EditText) findViewById(R.id.text);

        //getIntent
        Intent intent = getIntent();
        option = intent.getIntExtra("option", -1);

        //Memastikan apakah pindah activity karena addNotes atau edit notes yang telah ada
        //Jika add Notes , maka option == 1
        //Jika edit Notes, maka option == 2
        if (option == 1){

            Log.i("option", "Add Notes");

        } else if (option == 2) {

            Log.i("option", "Edit notes");

            noteId = intent.getIntExtra("noteId",-1);

            oldTitle = MainActivity.listOfTitle.get(noteId);
            oldText = MainActivity.noteMap.get(oldTitle);

            titleField.setText(oldTitle);
            textField.setText(oldText);
        }

        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                newTitle = String.valueOf(charSequence);
                isTitleChanged = true;

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                newText = String.valueOf(charSequence);
                isTextChanged = true;

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }
}
