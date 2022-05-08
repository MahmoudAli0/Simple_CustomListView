package com.mahmoud.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class noteActivity extends AppCompatActivity {

    int noteId ;
    EditText note_example ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        note_example=findViewById(R.id.text_here);
        Intent intent=getIntent() ;
        noteId =intent.getIntExtra("noteId",-1);

        if(noteId != -1){
            note_example.setText(MainActivity.Notes.get(noteId));
        }else{
            MainActivity.Notes.add("");
            noteId= MainActivity.Notes.size() - 1 ;
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }


        note_example.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                MainActivity.Notes.set(noteId,String.valueOf(charSequence));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences
                        ("package com.mahmoud.notes_app", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.Notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}