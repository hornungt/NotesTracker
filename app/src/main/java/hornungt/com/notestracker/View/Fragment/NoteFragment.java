package hornungt.com.notestracker.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import hornungt.com.notestracker.OnDataPass;
import hornungt.com.notestracker.R;

public abstract class NoteFragment extends DialogFragment {

    private TextInputLayout titleLayout, descriptionLayout;

    private OnDataPass passer;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View createNoteDialog = getActivity().getLayoutInflater().inflate(R.layout.note_dialog, null);
        titleLayout = createNoteDialog.findViewById(R.id.note_title_layout);
        descriptionLayout = createNoteDialog.findViewById(R.id.note_description_layout);
        titleLayout.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (titleLayout.getEditText().getText().toString().contains("\\")) {
                            titleLayout.setError("Invalid character");
                        } else {
                            titleLayout.setError(null);
                        }
                    }
                }
        );
        descriptionLayout.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (descriptionLayout.getEditText().getText().toString().contains("\\")) {
                            descriptionLayout.setError("Invalid character");
                        } else {
                            descriptionLayout.setError(null);
                        }
                    }
                }
        );
        dialogBuilder.setView(createNoteDialog);
        dialogBuilder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String noteTitle = titleLayout.getEditText().getText().toString().trim();
            String noteDescription = descriptionLayout.getEditText().getText().toString().trim();
            if (!noteTitle.isEmpty() && titleLayout.getError() == null
                    && !noteDescription.isEmpty() && descriptionLayout.getError() == null) {
                passNoteData(noteTitle, noteDescription);
            }
        }).setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            if (dialog != null) {
                dialog.dismiss();
            }
        });
        return dialogBuilder.create();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passer = (OnDataPass) context;
    }

    protected TextInputLayout getTitleLayout() { return titleLayout; }

    protected TextInputLayout getDescriptionLayout() { return descriptionLayout; }

    protected OnDataPass getPasser() { return passer; }

    /**
     * abstract method for passing data about the note to the MainActivity
     * @param title
     * @param description
     */
    protected abstract void passNoteData(String title, String description);

}
