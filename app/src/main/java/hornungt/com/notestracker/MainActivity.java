package hornungt.com.notestracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import hornungt.com.notestracker.Controller.NoteController;
import hornungt.com.notestracker.Model.Note;
import hornungt.com.notestracker.Model.NoteCategory;
import hornungt.com.notestracker.View.CustomView.NotePreviewLayout;
import hornungt.com.notestracker.View.Fragment.CreateCategoryFragment;
import hornungt.com.notestracker.View.Fragment.CreateNoteFragment;
import hornungt.com.notestracker.View.Fragment.DeleteCategoryFragment;
import hornungt.com.notestracker.View.Fragment.DeleteNoteFragment;
import hornungt.com.notestracker.View.Fragment.EditCategoryFragment;
import hornungt.com.notestracker.View.Fragment.EditNoteFragment;
import hornungt.com.notestracker.View.Fragment.InvalidCategoryAlertFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnDataPass {

    private NoteCategory noteCategory;

    private LinearLayout list;

    private Menu categoryMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteCategory != null) showCreateNoteDialog();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        list = findViewById(R.id.note_view_list);

        categoryMenu = navigationView.getMenu().addSubMenu("Categories");
        refreshMenu(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete_category && noteCategory != null) {
            showDeleteCategoryDialog();
            return true;
        } else if (id == R.id.action_edit_category && noteCategory != null){
            showEditCategoryDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_create_new_category_fragment) {
            showCreateCategoryDialog();
        } else {
            noteCategory = NoteController.getInstance().readCategory(this, (String)item.getTitle());
            updateNoteListView();
            setTitle(item.getTitle());
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void passNewCategoryData(String categoryName){
        if (!NoteController.getInstance().hasCategory(this, categoryName)) {
            this.noteCategory = NoteController.getInstance().createCategory(this, categoryName);
            updateNoteListView();
            categoryMenu.add(noteCategory.getName());
            setTitle(noteCategory.getName());
        } else {
            showInvalidCategoryDialog();
        }
    }

    @Override
    public void passEditCurrentCategory(String newName){
        if (!NoteController.getInstance().hasCategory(this, newName)) {
            this.noteCategory = NoteController.getInstance().editCategory(this, noteCategory.getName(), newName);
            refreshMenu(noteCategory.getName());
        } else if (!newName.equals(noteCategory.getName())) {
            showInvalidCategoryDialog();
        }
    }

    @Override
    public void passDeleteCurrentCategory(){
        NoteController.getInstance().deleteCategory(this, noteCategory.getName());
        this.noteCategory = null;
        setTitle(R.string.no_category_selected);
        list.removeAllViews();
        refreshMenu(null); // todo: (low priority) figure out using Menu.removeItem() instead of using refreshMenu()
    }

    @Override
    public void passNewNoteData(String noteTitle, String noteDescription){
        NoteController.getInstance().createNote(this, noteCategory, noteTitle, noteDescription);
        updateNoteListView();
    }

    @Override
    public void passEditNoteData(Note oldNote, String noteTitle, String noteDescription){
        NoteController.getInstance().editNote(this, noteCategory, oldNote, noteTitle, noteDescription);
        updateNoteListView();
    }

    @Override
    public void passDeleteNoteData(Note note){
        NoteController.getInstance().deleteNote(this, noteCategory, note);
        updateNoteListView();
    }

    /**
     * displays a CreateCategoryFragment, allows user to create new note category
     */
    private void showCreateCategoryDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("New Category Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = CreateCategoryFragment.newInstance("Create Category Fragment");
        newFragment.show(getSupportFragmentManager(), "New Category Dialog");
    }

    /**
     * displays a CreateNoteFragment, allows user to create a new note
     */
    private void showCreateNoteDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("New Note Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = CreateNoteFragment.newInstance("Create Note Fragment");
        newFragment.show(getSupportFragmentManager(), "New Note Dialog");
    }

    /**
     * displays an EditNoteFragment, used to display an existing note's info and edit an existing note
     */
    private void showEditNoteDialog(Note note){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("Edit Note Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = EditNoteFragment.newInstance(note);
        newFragment.show(getSupportFragmentManager(), "Edit Note Dialog");
    }

    /**
     * displays a DeleteNoteFragment, allows user to delete individual notes
     */
    private void showDeleteNoteDialog(Note note){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("Delete Note Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = DeleteNoteFragment.newInstance(note);
        newFragment.show(getSupportFragmentManager(), "Delete Note Dialog");
    }

    /**
     * displays an EditCategoryFragment, allows user to change title of current category
     */
    private void showEditCategoryDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("Edit Note Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = EditCategoryFragment.newInstance(noteCategory.getName());
        newFragment.show(getSupportFragmentManager(), "Edit Note Dialog");
    }

    /**
     * displays a DeleteCategoryFragment, allows user to delete current category and all notes that it contains
     */
    private void showDeleteCategoryDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("Delete Category Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = DeleteCategoryFragment.newInstance("Delete Category Fragment");
        newFragment.show(getSupportFragmentManager(), "Delete Category Dialog");
    }

    /**
     * displays an InvalidCategoryAlertFragment that tells the user that they tried to create a category that already exists
     */
    private void showInvalidCategoryDialog(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("Invalid Category Dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = InvalidCategoryAlertFragment.newInstance("Invalid Category Dialog");
        newFragment.show(getSupportFragmentManager(), "Invalid Category Dialog");
    }

    /**
     * refreshes the list of NoteLayout objects
     */
    private void updateNoteListView(){
        list.removeAllViews();
        for (Note note : noteCategory) {
            NotePreviewLayout tempNote = new NotePreviewLayout(this, note);
            tempNote.setOnClickListener(v -> showEditNoteDialog(note) );
            tempNote.setOnLongClickListener(v -> {
                showDeleteNoteDialog(note);
                return true;
            });

            list.addView(tempNote, 0); // add NoteLayout at index 0 of the list
        }
    }

    /**
     * refreshes DrawerLayout menu for whenever a category is added, edited, or deleted
     */
    private void refreshMenu(String newTitle){
        if (newTitle != null) setTitle(newTitle);
        else setTitle(R.string.no_category_selected);

        categoryMenu.clear();
        for (NoteCategory category : NoteController.getInstance().readAll(this)) {
            categoryMenu.add(category.getName());
        }
    }
}
