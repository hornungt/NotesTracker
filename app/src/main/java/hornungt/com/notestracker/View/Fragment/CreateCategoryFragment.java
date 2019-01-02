package hornungt.com.notestracker.View.Fragment;

import android.os.Bundle;

public class CreateCategoryFragment extends CategoryFragment {

    public static CreateCategoryFragment newInstance(String text){
        CreateCategoryFragment f = new CreateCategoryFragment();

        Bundle args = new Bundle();
        args.putString("title", text);
        f.setArguments(args);

        return f;
    }

    @Override
    protected void passCategoryData(String categoryTitle){
        getPasser().passNewCategoryData(categoryTitle);
    }

}
