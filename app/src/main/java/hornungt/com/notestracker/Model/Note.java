package hornungt.com.notestracker.Model;

import java.util.Calendar;

public class Note {

    private String title, description;
    private Calendar createdOn, updatedOn;

    /**
     * default constructor
     */
    public Note() {
        this.title = "";
        this.description = "";
        this.createdOn = null;
        this.updatedOn = null;
    }

    /**
     * constructor
     * @param title the title of the note
     * @param description the description of the note
     * @param createdOn the Calendar time at which the note is created
     */
    public Note(String title, String description, Calendar createdOn) {
        this.title = title;
        this.description = description;
        this.createdOn = this.updatedOn = createdOn;
    }

    /**
     * constructor
     * @param title the title of the note
     * @param description the description of the note
     * @param createdOn the Calendar time at which the note is created
     * @param updatedOn the Calendar time at which the note was last edited
     */
    public Note(String title, String description, Calendar createdOn, Calendar updatedOn) {
        this(title, description, createdOn);
        this.updatedOn = updatedOn;
    }

    /**
     * gets the title
     * @return the title of the note
     */
    public String getTitle() { return title; }

    /**
     * gets the description
     * @return the description of the note
     */
    public String getDescription() { return description; }

    /**
     * gets the time the note was created
     * @return the Calendar time the note was created
     */
    public Calendar getCreatedOn() { return createdOn; }

    /**
     * gets a formatted String for the date of creation
     * @return String representing day/month/year
     */
    public String getCreatedOnString(){
        return createdOn.get(Calendar.DAY_OF_MONTH) + "-" +
            (createdOn.get(Calendar.MONTH) + 1) + "-" +
            createdOn.get(Calendar.YEAR);
}

    /**
     * gets a formatted String for the last edited date
     * @return String representing day/month/year
     */
    public String getUpdatedOnString(){
        return updatedOn.get(Calendar.DAY_OF_MONTH) + "-" +
                (updatedOn.get(Calendar.MONTH) + 1) + "-" +
                updatedOn.get(Calendar.YEAR);
    }

    /**
     * sets the note's title
     * @param title the new title
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * sets the note's description
     * @param description the new description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * checks for equality between two objects of type Note based on title, description, createdOn
     * @param obj the object being checked against the object calling the method
     * @return whether or not the objects are equal
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Note){
            Note temp = (Note)obj;
            return this.getTitle().equals(temp.getTitle())
                    && this.getDescription().equals(temp.getDescription())
                    && this.getCreatedOnString().equals(temp.getCreatedOnString());
        }
        return false;
    }

    /**
     * title, description, createdOn (dd/MM/yy), updatedOn (dd/MM/yy)
     * @return a string representation of a Note
     */
    @Override
    public String toString(){
        return title + "|" + description + "|" +
                createdOn.get(Calendar.DAY_OF_MONTH) + "-" +
                (createdOn.get(Calendar.MONTH) + 1) + "-" +  //  Calendar.MONTH starts at zero
                createdOn.get(Calendar.YEAR) +
                "|" +
                updatedOn.get(Calendar.DAY_OF_MONTH) + "-" +
                (updatedOn.get(Calendar.MONTH) + 1) + "-" +
                updatedOn.get(Calendar.YEAR);
    }
}
