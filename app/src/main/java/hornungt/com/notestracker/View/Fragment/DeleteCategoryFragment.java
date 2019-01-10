package hornungt.com.notestracker.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import hornungt.com.notestracker.OnDataPass;
import hornungt.com.notestracker.R;

public class DeleteCategoryFragment extends DialogFragment {

    private OnDataPass passer;

    public static DeleteCategoryFragment newInstance(String text){
        DeleteCategoryFragment f = new DeleteCategoryFragment();

        Bundle args = new Bundle();
        args.putString("title", text);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(R.string.action_delete_category);

        dialogBuilder.setPositiveButton(android.R.string.ok, (dialog, which) ->{
            passer.passDeleteCurrentCategory();
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
