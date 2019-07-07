package com.example.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView ListViewNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListViewNotes=(ListView) findViewById(R.id.main_listView_);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_new_note:
                Intent noteActivity=new Intent(getApplicationContext(),NoteActivity.class);
                startActivity(noteActivity);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListViewNotes.setAdapter(null);

        ArrayList<Note> notes=Utilities.getAllSavedNotes(this);

        if(notes==null||notes.size()==0) {
            Toast.makeText(this,"You have no saved notes",Toast.LENGTH_SHORT).show();
            return;
        } else {
            NoteAdapter na=new NoteAdapter(this,R.layout.item_note,notes);
            ListViewNotes.setAdapter(na);

            ListViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String filename=((Note)ListViewNotes.getItemAtPosition(position))
                            .getDateTime()+Utilities.FILE_EXTENSION;

                    Intent viewNoteIntent=new Intent(getApplicationContext(),NoteActivity.class);
                    viewNoteIntent.putExtra("NOTE_FILE",filename);
                    startActivity(viewNoteIntent);
                }
            });
        }

    }
}
