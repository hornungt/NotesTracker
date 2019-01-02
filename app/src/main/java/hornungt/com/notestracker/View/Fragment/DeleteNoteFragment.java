package hornungt.com.notestracker.View.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import hornungt.com.notestracker.Model.Note;
import hornungt.com.notestracker.OnDataPass;
import hornungt.com.notestracker.R;

public class DeleteNoteFragment extends DialogFragment {

    private static Note note;

    private OnDataPass passer;

    public static DeleteNoteFragment newInstance(Note data){
        DeleteNoteFragment f = new DeleteNoteFragment();

        Bundle args = new Bundle();
        args.putString("title", data.getTitle());
        f.setArguments(args);

         note = data;

        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(getString(R.string.delete_note_dialog) + " \"" + getArguments().getString("title") + "\"");

        dialogBuilder.setPositiveButton(android.R.string.ok, (dialog, which) ->{
            passer.passDeleteNoteData(note);
        }).setNegativeButton(android.R.string.cancel, (dialog, which) ->{
            if (dialog != null) dialog.dismiss();
        });

        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        passer = (OnDataPass)context;
    }
}
