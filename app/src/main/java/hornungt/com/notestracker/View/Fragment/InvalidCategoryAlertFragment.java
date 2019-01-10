package hornungt.com.notestracker.View.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import hornungt.com.notestracker.R;

public class InvalidCategoryAlertFragment extends DialogFragment {

    public static InvalidCategoryAlertFragment newInstance(String data){
        InvalidCategoryAlertFragment f = new InvalidCategoryAlertFragment();

        Bundle args = new Bundle();
        args.putString("data", data);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final View createNoteDialog = getActivity().getLayoutInflater().inflate(R.layout.invalid_category_dialog, null);
        dialogBuilder.setView(createNoteDialog);
        dialogBuilder.setNeutralButton(android.R.string.ok, (dialog, which) ->{
            if (dialog != null) dialog.dismiss();
        });
        return dialogBuilder.create();
    }

}
