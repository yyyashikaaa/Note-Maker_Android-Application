package com.example.notetaker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText content;
    private String NoteFilename;
    private Note LoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title = findViewById(R.id.note_et_title);
        content = findViewById(R.id.note_et_content);


        NoteFilename = getIntent().getStringExtra("NOTE_FILE");
        if (NoteFilename != null && !NoteFilename.isEmpty()) {
            LoadedNote = Utilities.getNoteByName(this, NoteFilename);
            if (LoadedNote != null) {
                title.setText(LoadedNote.getTitle());
                content.setText(LoadedNote.getContent());

            }
            if (LoadedNote == null) {
                Toast.makeText(getApplicationContext(), "Cant Load Note!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_save:
                saveNote();
                break;

            case R.id.action_main_delete:
                deleteNote();
                break;
            case R.id.action_main_share:
                shareNote();
                break;
        }
        return true;
    }

    private void deleteNote() {
        if (LoadedNote == null) {
            finish();
        } else {
            Utilities.deleteNote(getApplicationContext(), LoadedNote.getDateTime() + Utilities.FILE_EXTENSION);

            AlertDialog.Builder dialog = new AlertDialog
                    .Builder(this)
                    .setTitle("Do you really want to delete this note?")
                    .setMessage("You are about to delete " + title.getText().toString())
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Utilities.deleteNote(getApplicationContext(), LoadedNote.getDateTime() + Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(), "Note Deleted!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .setNegativeButton("no", null)
                    .setCancelable(false);
            dialog.show();
        }
    }

    private void shareNote() {
        if (LoadedNote == null) {
            finish();
        } else {
            Utilities.shareNote(getApplicationContext(), LoadedNote.getDateTime() + Utilities.FILE_EXTENSION);
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT,"*"+title.getText().toString()+"*"+"\n"+content.getText().toString());
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveNote() {
        Note note;
        if (title.getText().toString().trim().isEmpty() | content.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter Title & Content", Toast.LENGTH_SHORT).show();
            return;
        }
        if (LoadedNote == null) {
            note = new Note(System.currentTimeMillis(), title.getText().toString(), content.getText().toString());
        } else {
            note = new Note(LoadedNote.getDateTime(), title.getText().toString(), content.getText().toString());
        }
        if (Utilities.saveNote(this, note)) {
            Toast.makeText(getApplicationContext(), "Your note is saved!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cant save Note!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}

