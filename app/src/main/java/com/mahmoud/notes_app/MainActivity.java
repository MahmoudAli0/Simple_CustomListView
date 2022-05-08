package com.mahmoud.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static ArrayAdapter arrayAdapter;

    private ListView items;

    static ArrayList<String> Notes = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()== R.id.note_id){

            Intent intent = new Intent(MainActivity.this,noteActivity.class);
            startActivity(intent);
            return true ;
        }
       return false ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items=findViewById(R.id.list_view_items);

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences
                ("package com.mahmoud.notes_app", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if (set== null) {
            Notes.add("Mahmoud Ali As an example Note ");
        }else {
            Notes=new ArrayList<>(set);

        }

         arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,Notes);
        items.setAdapter(arrayAdapter);
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,noteActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });

        items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemToDelete=i;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).
                        setTitle("are your sure ?? :( ").setMessage("do you want to delete this note ?").
                        setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Notes.remove(itemToDelete);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences
                                        ("package com.mahmoud.notes_app", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(MainActivity.Notes);
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        }).setNegativeButton("no",null).show();

                return true;
            }
        });

    }
}