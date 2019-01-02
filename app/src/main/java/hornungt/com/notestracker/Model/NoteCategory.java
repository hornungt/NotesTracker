package hornungt.com.notestracker.Model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NoteCategory extends ArrayList<Note>{

    private String name;

    public NoteCategory(String name){
        this.name = name;
    }

    public NoteCategory(String name, ArrayList<Note> notes){
        this.name = name;
        this.addAll(notes);
    }

    public void add(List<Note> notes){
        for (Note note : notes) {
            add(note);
        }
    }

    public String getName(){ return name; }

    public void setName(String name){ this.name = name; }

    public static List<Note> parseNotes(String rawData){
        String[] data = rawData.split("\\*");
        List<Note> notes = new ArrayList<>();
        for (String s : data){
            String[] noteData = s.split("\\|");
            try {
                String title = noteData[0];
                String description = noteData[1];

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date createdOn = sdf.parse(noteData[2]);
                Calendar calCreated = Calendar.getInstance();
                calCreated.setTime(createdOn);

                Date updatedOn = sdf.parse(noteData[3]);
                Calendar calUpdated = Calendar.getInstance();
                calUpdated.setTime(updatedOn);

                notes.add(new Note(title, description, calCreated, calUpdated));
            } catch (IndexOutOfBoundsException e){
                Log.e(TAG, "parseNoteCategory: note data is formatted incorrectly", e);
            } catch (ParseException e){
                Log.e(TAG, "parseNoteCategory: date formatted incorrectly", e);
            }
        }
        return notes;
    }

    public boolean contains(String noteName){
        for (Note note : this) {
            if (noteName.equals(note.getTitle())) return true;
        }
        return false;
    }

    @Override
    public String toString(){ // formatted for writing to file
        String msg = "";
        for (int i = 0; i < size(); i++) {
            msg += this.get(i).toString() + "*";
        }
        return msg;
    }

}
