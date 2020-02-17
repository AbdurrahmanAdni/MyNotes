package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> listOfTitle = new ArrayList<String>();
    static ArrayAdapter arrayAdapter;
    static HashMap<String, String> noteMap;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addNotes){
            Log.i("Main Menu Toolbar", "Add Notes");

            Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
            intent.putExtra("option", 1);
            startActivity(intent);

        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);

        //Pembuatan List View
        ListView listView = (ListView)findViewById(R.id.listView);

        //Pembuatan HashMap yang memetakan Title ke isi Notes
        noteMap = new HashMap<String, String>();

        //Pembuatan arrayAdapter
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfTitle);

        //Set adapter untuk listView
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                intent.putExtra("option", 2);
                intent.putExtra("noteId", i);
                startActivity(intent);

            }
        });



    }
}
