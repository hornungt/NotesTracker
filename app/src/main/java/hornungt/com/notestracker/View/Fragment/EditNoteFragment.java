package hornungt.com.notestracker.View.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import hornungt.com.notestracker.Model.Note;

public class EditNoteFragment extends NoteFragment {

    private static Note original;

    public static EditNoteFragment newInstance(Note note){
        EditNoteFragment f = new EditNoteFragment();

        Bundle args = new Bundle();
        args.putString("title", note.getTitle());
        args.putString("description", note.getDescription());
        f.setArguments(args);

        original = note;

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        getTitleLayout().getEditText().setText(getArguments().getString("title"));
        getDescriptionLayout().getEditText().setText(getArguments().getString("description"));

        return dialog;
    }

    /**
     * passes data from the fragment to the MainActivity
     * @param title the title of the newly created note
     * @param description the description of the newly created note
     */
    @Override
    protected void passNoteData(String title, String description) {
        getPasser().passEditNoteData(original, title, description);
    }
}
