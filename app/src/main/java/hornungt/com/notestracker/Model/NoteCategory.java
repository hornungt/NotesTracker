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

    /**
     * constructor
     * @param name the name of the category
     */
    public NoteCategory(String name){
        this.name = name;
    }

    /**
     * constructor
     * @param name the name of the category
     * @param notes the notes that belong to the category
     */
    public NoteCategory(String name, ArrayList<Note> notes){
        this.name = name;
        this.addAll(notes);
    }

    /**
     * adds a List of Notes to the category
     * @param notes the notes to be added
     */
    public void add(List<Note> notes){
        for (Note note : notes) {
            add(note);
        }
    }

    /**
     * accessor for name
     * @return the name of the category
     */
    public String getName(){ return name; }

    /**
     * mutator for name
     * @param name the new name of the category
     */
    public void setName(String name){ this.name = name; }

    /**
     * parses a List of Notes from a string, used for reading note data from a text file
     * @param rawData the formatted string from which notes are parsed
     * @return the list of notes parsed from the string
     */
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

    /**
     * formats a string from the Notes contained in NoteCategory that is used for writing to a file
     * @return a string representation of the NoteCategory
     */
    @Override
    public String toString(){
        String msg = "";
        for (int i = 0; i < size(); i++) {
            msg += this.get(i).toString() + "*";
        }
        return msg;
    }

}
