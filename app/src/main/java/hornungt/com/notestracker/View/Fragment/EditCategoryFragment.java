package hornungt.com.notestracker.View.Fragment;

import android.app.Dialog;
import android.os.Bundle;

public class EditCategoryFragment extends CategoryFragment {

    public static EditCategoryFragment newInstance(String text){
        EditCategoryFragment f = new EditCategoryFragment();

        Bundle args = new Bundle();
        args.putString("title", text);
        f.setArguments(args);

        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        getTitleLayout().getEditText().setText(getArguments().getString("title"));
        return dialog;
    }

    @Override
    protected void passCategoryData(String categoryTitle){
        getPasser().passEditCurrentCategory(categoryTitle);
    }

}
