package hornungt.com.notestracker.View.Fragment;

import android.os.Bundle;

public class CreateNoteFragment extends NoteFragment {

    public static CreateNoteFragment newInstance(String text){
        CreateNoteFragment f = new CreateNoteFragment();

        Bundle args = new Bundle();
        args.putString("title", text);
        f.setArguments(args);

        return f;
    }

    @Override
    protected void passNoteData(String title, String description){
        getPasser().passNewNoteData(title, description);
    }
}
