import com.google.gson.annotations.Expose;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class representation of a subject in which all subject times, which are saved separately in the Lab Plan and
 * {@linkplain Assignment Assignments} of this subject are saved.
 */
class MainSubject {

    /**
     * The name of this MainSubject
     */
    @Expose
    private String name;

    /**
     * {@link Color} of this subject
     */
    private Color color;

    @Expose
    private boolean hasAssign;

    /**
     * The rgbValue of the {@link Color} of this MainSubject needed for saving purpose
     */
    @Expose
    private int rgbValue;

    /**
     * Lessons done of this subject
     */
    @Expose
    private int lessonsDone;

    /**
     * {@link ArrayList} of {@linkplain Assignment Assignments} in this subject
     */
    private ArrayList<Assignment> assignments;

    /**
     * {@link ArrayList} of {@link Subject} time entries in the Lab Plan of this subject
     */
    private ArrayList<Subject> subjects;

    /**
     * GUI that shows how many lessons are done in this subject already
     */
    private StundenGUI stdGUI;

    MainSubject(String name, Color color) {
        this.name = name;
        this.color = color;
        rgbValue = color.getRGB();
        subjects = new ArrayList<>();
        assignments = new ArrayList<>();
        init();
    }

    /**
     * Constructor for saving purpose
     */
    MainSubject() {
        name = "";
        color = null;
        rgbValue = 0;
        subjects = new ArrayList<>();
        assignments = new ArrayList<>();
    }

    /**
     * Removes itself from the GUIs if it has no entries in the Lab Plan ({@link MainSubject#subjects})
     *
     * @return Whether there are still entries of this subject in the Lab Plan and the subject could be removed
     */
    boolean removeMe() {
        if (!subjects.isEmpty()) return false;
        else {
            Main.inst.getGui().lessonPanel.remove(stdGUI.content);
            Main.inst.getGui().lessonPanel.revalidate();
            Main.inst.getGui().lessonPanel.repaint();
            return true;
        }
    }

    /**
     * Initialization of the MainSubject. Adds itself to the GUIs and updates {@link MainSubject#hasAssign}
     */
    void init() {
        stdGUI = new StundenGUI(this);
        Main.inst.getGui().lessonPanel.add(stdGUI.content);
        Main.inst.getGui().lessonPanel.revalidate();
        Main.inst.getGui().lessonPanel.repaint();
        updateHasAssign();
    }

    /**
     * Updates, whether the subject has an {@link Assignment}
     */
    void updateHasAssign() {
        setHasAssign(!assignments.isEmpty());
    }

    /**
     * Adds the {@link Subject} to {@link MainSubject#subjects} in the MainSubject
     *
     * @param subject The Subject to be added
     */
    void addSubject(Subject subject) {
        subjects.add(subject);
    }

    /**
     * Removes the {@link Subject} from {@link MainSubject#subjects} in the MainSubject
     *
     * @param s The Subject to be removed
     */
    void removeSubject(Subject s) {
        subjects.remove(s);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MainSubject)) return false;
        MainSubject mainSubject = (MainSubject) obj;
        if (hasAssign != mainSubject.hasAssign) return false;
        if (lessonsDone != mainSubject.lessonsDone) return false;
        if (!name.equals(mainSubject.name)) return false;
        if (!color.equals(mainSubject.color)) return false;
        if (!assignments.equals(mainSubject.assignments)) return false;
        return subjects.equals(mainSubject.subjects);
    }

    /**
     * Calculation of the attention level color of the MainSubject
     *
     * @return Color which represents the attention level
     */
    Color getDrawAttColor() {
        Color ret = Color.green;
        if (assignments.isEmpty()) ret = Color.gray;
        for (Assignment a : assignments) {
            if (a.needAttention() == 1) ret = Color.red;
            else if (a.needAttention() == 2) if (!ret.equals(Color.red)) ret = Color.orange;
        }
        return ret;
    }

    /**
     * Converts the MainSubject into a {@link Subject}
     *
     * @return The Subject of this MainSubject and empty teacher
     */
    Subject getAsSubject() {
        return new Subject("", this);
    }

    String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the color according to the {@link MainSubject#rgbValue} of the MainSubject and returns it
     *
     * @return The Color corresponding to the rgbValue of the MainSubject
     */
    Color getColor() {
        color = new Color(rgbValue);
        return color;
    }

    /**
     * Sets the color and {@link MainSubject#rgbValue} according to the specified color
     *
     * @param color The color to be set to
     */
    void setColor(Color color) {
        this.color = color;
        rgbValue = color.getRGB();
    }

    /**
     * Adds the {@link Assignment} to {@link MainSubject#assignments} and updates
     * {@link MainSubject#hasAssign}
     *
     * @param assignment The Assignment to be added
     */
    void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        updateHasAssign();
    }

    /**
     * Removes the {@link Assignment} from {@link MainSubject#assignments} and updates
     * {@link MainSubject#hasAssign}
     *
     * @param assignment The Assignment to be removed
     */
    void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
        updateHasAssign();
    }

    public int getRgbValue() {
        return rgbValue;
    }

    /**
     * Sets the rgbValue and updates the {@link MainSubject#color} of the MainSubject for each subject for saving
     * purpose
     *
     * @param rgbValue The rgb Value which the Subject should be set to
     */
    public void setRgbValue(int rgbValue) {
        this.rgbValue = rgbValue;
        setColor(new Color(rgbValue));
        for (Subject s : subjects) s.updateColor();
    }

    /**
     * Sets {@link MainSubject#hasAssign} also for all {@linkplain Subject Subjects} from {@link MainSubject#subjects}
     *
     * @param hasAssign The boolean has hasAssign shall be set to
     */
    void setHasAssign(boolean hasAssign) {
        this.hasAssign = hasAssign;
        for (Subject s : subjects) s.setHasAssign(hasAssign);
    }

    int getLessonsDone() {
        return lessonsDone;
    }

    void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }

    ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    ArrayList<Subject> getSubjects() {
        return subjects;
    }
}