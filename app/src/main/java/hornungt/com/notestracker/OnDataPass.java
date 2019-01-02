package hornungt.com.notestracker;

import hornungt.com.notestracker.Model.Note;

public interface OnDataPass {

    void passNewCategoryData(String categoryName);

    void passEditCurrentCategory(String newName);

    void passDeleteCurrentCategory();

    void passNewNoteData(String noteTitle, String noteDescription);

    void passEditNoteData(Note old, String newTitle, String newDescription);

    void passDeleteNoteData(Note note);

}