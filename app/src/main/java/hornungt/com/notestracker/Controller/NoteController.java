package hornungt.com.notestracker.Controller;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import hornungt.com.notestracker.Model.Note;
import hornungt.com.notestracker.Model.NoteCategory;

import static android.content.ContentValues.TAG;

public class NoteController {

    private static final NoteController ourInstance = new NoteController();

    public static NoteController getInstance() {
        return ourInstance;
    }

    private NoteController() {
    }

    /**
     * reads every category in the app
     * @param context the context of the app
     * @return an ArrayList of each note category stored in the app
     */
    public ArrayList<NoteCategory> readAll(Context context){
        ArrayList<NoteCategory> allNotes = new ArrayList<>();
        File directory = context.getFilesDir();
        String[] children = directory.list();
        for (String child : children) {
            if (child.contains(".txt")){
                allNotes.add(readCategory(context, child.substring(0, child.indexOf(".txt"))));
            }
        }
        return allNotes;
    }

    /**
     * tells if a category already exists
     * @param context the context of the app
     * @param categoryName the name of the category to be checked
     * @return boolean whether or not category exists already
     */
    public boolean hasCategory(Context context, String categoryName){
        File directory = context.getFilesDir();
        String[] children = directory.list();
        for (String child : children) {
            if (child.equalsIgnoreCase(categoryName + ".txt")){
                return true;
            }
        }
        return false;
    }

    /**
     * reads all of the notes from a given category
     * @param context the context of the app
     * @param categoryName the name of the category being read from
     * @return a NoteCategory with all of the notes from the specified file
     */
    public NoteCategory readCategory(Context context, String categoryName){
        File file = new File(context.getFilesDir(), categoryName + ".txt");
        NoteCategory category = new NoteCategory("File Not Found");
        if (file.exists()){
            FileInputStream inStream;
            byte[] bytes = new byte[(int)file.length()];
            try {
                inStream = context.openFileInput(categoryName + ".txt");
                inStream.read(bytes);
                inStream.close();
            } catch (Exception e){
                Log.e(TAG, "write: FileManager.read()", e);
            }
            String data = new String(bytes);
            category = new NoteCategory(categoryName);
            if (!(data.length() == 0)){
                category.add(NoteCategory.parseNotes(data));
            }
        }
        return category;
    }

    /**
     * Creates a new category
     * @param context the context of the app
     * @param categoryName the name of the new NoteCategory
     * @return the newly created NoteCategory
     */
    public NoteCategory createCategory(Context context, String categoryName){
        NoteCategory category = null;
        try {
            File dir = new File(context.getFilesDir(), categoryName + ".txt");
            dir.createNewFile();
            category = new NoteCategory(categoryName);
        } catch (IOException e){
            Log.e(TAG, "NoteController.createCategory", e);
        }
        return category;
    }

    /**
     * creates a new note based on title and description, writes to file, and adds new note to category
     * @param context the current Context of the app
     * @param category the NoteCategory that is getting the note
     * @param title the title of the new note
     * @param description the description of the new note
     */
    public void createNote(Context context, NoteCategory category, String title, String description){
        Calendar now = Calendar.getInstance();
        Note note = new Note(title, description, now);
        File file = new File(context.getFilesDir(), category.getName() + ".txt");
        try {
            if (file.exists()) {
                String stringData;
                byte[] writeData;
                if (file.length() != 0){
                    NoteCategory categoryTemp = readCategory(context, category.getName());
                    stringData = (categoryTemp.toString() + note.toString());
                    writeData = stringData.getBytes();
                } else {
                    stringData = note.toString();
                    writeData = stringData.getBytes();
                }
                FileOutputStream outStream =
                        context.openFileOutput(category.getName() + ".txt", Context.MODE_PRIVATE);
                outStream.write(writeData, (int)file.length(), writeData.length);
                outStream.close();
            } else {
                throw new FileNotFoundException();
            }
        } catch (IOException e){
            Log.e(TAG, "NoteController.createNote", e);
        }

        category.add(note);
    }

    /**
     * deletes a note from the specified category
     * @param context the context of the app
     * @param category the category of the note being deleted
     * @param note the note being deleted
     */
    public void deleteNote(Context context, NoteCategory category, Note note){
        NoteCategory notes = readCategory(context, category.getName());
        notes.remove(note);
        writeCategory(context, notes);
        category.remove(note);
    }

    /**
     * edits a current note by reading the current category, replacing the old note, and rewriting to the file
     * @param context context of the app
     * @param category category of the note that is being edited
     * @param oldNote the note that is being replaced
     * @param newTitle title of the new note
     * @param newDescription description of the new note
     */
    public void editNote(Context context, NoteCategory category, Note oldNote, String newTitle, String newDescription){
        NoteCategory notes = readCategory(context, category.getName());
        if (notes.contains(oldNote)){
            Note newNote = new Note(newTitle, newDescription, oldNote.getCreatedOn(), Calendar.getInstance());
            notes.set(notes.indexOf(oldNote), newNote);
            category.set(category.indexOf(oldNote), newNote);
            writeCategory(context, notes);
        }
    }

    /**
     * writes new data to an existing NoteCategory in the internal directory
     *
     * @param context the context of the app
     * @param category the NoteCategory being written to
     */
    public void writeCategory(Context context, NoteCategory category){
        File file = new File(context.getFilesDir(), category.getName() + ".txt");
        try {
            if (file.exists()) {
                FileOutputStream outStream =
                        context.openFileOutput(category.getName() + ".txt", Context.MODE_PRIVATE);
                byte[] writeData = category.toString().getBytes();
                outStream.write(writeData);
                outStream.close();
            }
        } catch (IOException e){
            Log.e(TAG, "NoteController.writeCategory", e);
        }
    }

    /**
     * renames a NoteCategory by renaming the text file its data is stored in
     *
     * @param context the context of the app
     * @param original the original note name
     * @param newName the new note name
     * @return a NoteCategory with the new name
     */
    public NoteCategory editCategory(Context context, String original, String newName){
        File file = new File(context.getFilesDir(), original + ".txt");
        File newFile = new File(context.getFilesDir(), newName + ".txt");
        file.renameTo(newFile);
        return readCategory(context, newName);
    }

    /**
     * deletes a text file that stores the data for a NoteCategory
     * @param context the context of the app
     * @param categoryName the name of the category to be deleted
     */
    public void deleteCategory(Context context, String categoryName){
        File dir = new File(context.getFilesDir(), categoryName + ".txt");
        dir.delete();
    }

}