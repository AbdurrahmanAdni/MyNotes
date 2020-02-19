package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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

    public void saveMap (HashMap<String, String> hashMap){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myNote", Context.MODE_PRIVATE);
        if (sharedPreferences != null){
            JSONObject jsonObject = new JSONObject(hashMap);
            String jsonToString = jsonObject.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("noteMap").commit();
            editor.putString("noteMap", jsonToString);
            editor.commit();
        }
    }


    public HashMap<String,String> loadHashMap(){

        HashMap<String,String> hashMap = new HashMap<String, String>();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myNote", Context.MODE_PRIVATE);
        try {
            if (sharedPreferences != null) {
                String jsonString = sharedPreferences.getString("noteMap", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keyItr = jsonObject.keys();
                while (keyItr.hasNext()) {
                    String title = keyItr.next();
                    String text = (String) jsonObject.get(title);
                    hashMap.put(title, text);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return hashMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.mynotes", Context.MODE_PRIVATE);

        //Pembuatan List View
        final ListView listView = (ListView)findViewById(R.id.listView);

        //Pembuatan HashMap yang memetakan Title ke isi Notes
        noteMap = new HashMap<String, String>();
        noteMap = loadHashMap();

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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int item, long l) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                noteMap.remove(listOfTitle.get(item));
                                listOfTitle.remove(item);
                                saveMap(noteMap);
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });



    }
}
