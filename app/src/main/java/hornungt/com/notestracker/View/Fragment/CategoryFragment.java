package hornungt.com.notestracker.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hornungt.com.notestracker.OnDataPass;
import hornungt.com.notestracker.R;

public abstract class CategoryFragment extends DialogFragment {

    private TextInputLayout titleLayout;

    private OnDataPass passer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.category_dialog, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View categoryDialog = getActivity().getLayoutInflater().inflate(R.layout.category_dialog, null);
        titleLayout = categoryDialog.findViewById(R.id.title_field_layout);
        titleLayout.getEditText().addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { } // do nothing
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) { } // do nothing

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (titleLayout.getEditText().getText().toString().contains("\\")){
                            titleLayout.setError("Invalid character");
                        } else {
                            titleLayout.setError(null);
                        }
                    }
                }
        );
        dialogBuilder.setView(categoryDialog);
        dialogBuilder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            String categoryTitle = titleLayout.getEditText().getText().toString().trim();
            if (!categoryTitle.isEmpty() && titleLayout.getError() == null) {
                passCategoryData(categoryTitle);
            }
        }).setNegativeButton(android.R.string.cancel, (dialog, which) -> {
            if (dialog != null){
                dialog.dismiss();
            }
        });
        return dialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        passer = (OnDataPass) context;
    }

    protected TextInputLayout getTitleLayout() { return titleLayout; }

    protected OnDataPass getPasser() { return passer; }

    protected abstract void passCategoryData(String categoryTitle);
}