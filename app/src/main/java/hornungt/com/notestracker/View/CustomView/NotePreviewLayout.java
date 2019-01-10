package hornungt.com.notestracker.View.CustomView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

import hornungt.com.notestracker.Model.Note;
import hornungt.com.notestracker.R;

public class NotePreviewLayout extends ConstraintLayout {

    private TextView title, description, lastUpdated;
    private Note note;

    /**
     * constructor
     * @param context the context of the app
     * @param note the note that the NotePreviewLayout is displaying
     */
    public NotePreviewLayout(Context context, @NonNull  Note note){
        super(context);
        this.note = note;
        init(context);

        //listeners are assigned in MainActivity
    }

    /**
     * sets up the title, description, and lastUpdated portions of the layout
     * @param context the context of the app
     */
    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.note_preview_layout, this);

        title = this.findViewById(R.id.note_view_title);
        title.setText(note.getTitle());
        description = this.findViewById(R.id.note_view_description);
        description.setText(note.getDescription());
        lastUpdated = this.findViewById(R.id.note_view_last_updated);
        lastUpdated.setText(note.getUpdatedOnString()); //todo: test what this is setting the text to
    }

    /**
     * accessor for the Note being displayed
     * @return the Note being displayed
     */
    public Note getNote() {
        return note;
    }
}
