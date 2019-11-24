import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.util.*;

class Main {

    /**
     * Instance of this main
     */
    static transient Main inst;

    /**
     * {@link GUI} of this main
     */
    private transient GUI gui;

    /**
     * {@link HashMap} of all {@linkplain MainSubject MainSubjects}. The key is their {@link String} name.
     */
    @Expose
    private HashMap<String, MainSubject> aMSubjects;

    /**
     * {@link HashMap} with all {@linkplain Day Days} mapped from their according {@link String} eg. "Monday"
     */
    @Expose
    private HashMap<String, Day> aDays;

    /**
     * {@link ArrayList} of all {@linkplain Assignment Assignments}
     */
    @Expose
    private ArrayList<Assignment> aAss;

    /**
     * {@link ArrayList} of all {@linkplain AssignmentGUI AssignmentGUIs} of all {@linkplain Assignment Assignments} to
     * make sorting them in the Assignments Tab easier
     */
    private final ArrayList<AssignmentGUI> aassGUIs;

    /**
     * Whether this is the first start of the program
     */
    @Expose
    private boolean first;

    /**
     * Number of all Lab lessons done in total already
     */
    @Expose
    private int lessonsDoneT;

    /**
     * Number of Lab lessons to be done per {@linkplain MainSubject subject}
     */
    @Expose
    private int maxPerSubject;

    /**
     * Number of Lab lesson to be done in total of all subjects
     */
    @Expose
    private int maxTotal;

    /**
     * The {@link JFileChooser} that points to the file in which everything is saved
     */
    private final transient JFileChooser f;

    /**
     * Int representation of the {@link Day} of the week
     */
    private int d;

    /**
     * Whether {@link Main#d} should be updated because {@link Day} of week changed
     */
    private boolean ch;

    /**
     * Constructor
     */
    Main() {
        inst = this;
        aMSubjects = new HashMap<>();
        aDays = new HashMap<>();
        aAss = new ArrayList<>();
        aassGUIs = new ArrayList<>();
        f = new JFileChooser();
        first = true;
        Thread t = new Thread(() -> {
            while (true) {
                if (inst.gui != null && inst.gui.contentPane != null)
                    inst.gui.contentPane.paintImmediately(0, 0, gui.getWidth(), gui.getHeight());
                if (Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_WEEK) != d) {
                    ch = false;
                    d = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_WEEK);
                } else ch = true;
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * Initialization of the main by calling all init() methods of the {@linkplain Assignment#init Assignments},
     * {@linkplain Day#init Days} and {@linkplain MainSubject#init MainSubjects}
     */
    void init() {
        for (Day day : getaDays()) day.init();
        for (Assignment a : aAss) a.init();
        for (MainSubject s : aMSubjects.values()) s.init();
    }

    /**
     * Adds the {@linkplain Assignment Assignment} to {@link Main#aAss} of the main
     *
     * @param assignment The Assignment to be added
     */
    void addAssignment(Assignment assignment) {
        aAss.add(assignment);
    }

    /**
     * Converts the {@link HashMap} to a {@link Collection} of its values
     *
     * @return The Collection of MainSubjects
     */
    Collection<MainSubject> getaMSubjects() {
        return aMSubjects.values();
    }

    public void setaMSubjects(HashMap<String, MainSubject> aMSubjects) {
        this.aMSubjects = aMSubjects;
    }

    /**
     * Converts the {@link HashMap} to a {@link Collection} of its values
     *
     * @return The Collection of Days
     */
    Collection<Day> getaDays() {
        return aDays.values();
    }

    public void setaDays(HashMap<String, Day> aDays) {
        this.aDays = aDays;
    }

    public ArrayList<Assignment> getaAss() {
        return aAss;
    }

    public void setaAss(ArrayList<Assignment> aAss) {
        this.aAss = aAss;
    }

    /**
     * Returns the value associated with the specified {@link String}
     *
     * @param name The name String of the MainSubject
     * @return The MainSubject with the specified name
     */
    MainSubject getMainSubject(String name) {
        return aMSubjects.get(name);
    }

    /**
     * Returns the value associated with the specified {@link String}
     *
     * @param s The String representing the Day
     * @return The Day represented by that String
     */
    Day getDay(String s) {
        return aDays.get(s);
    }

    /**
     * Adds the {@link MainSubject} to {@link Main#aMSubjects} of the main
     *
     * @param mainSubject The MainSubject to be added
     */
    void addMainSubject(MainSubject mainSubject) {
        aMSubjects.put(mainSubject.getName(), mainSubject);
    }

    /**
     * Adds the {@link Day} to {@link Main#aDays} of the main
     *
     * @param day The Day to be added
     */
    void addDay(Day day) {
        aDays.put(day.getDaySt(), day);
    }

    /**
     * Adds the {@link Subject} to the corresponding {@link MainSubject} in {@link Main#aMSubjects} of the main.
     *
     * @param subject The Subject to be added
     */
    public void addSubject(Subject subject) {
        for (MainSubject mS : aMSubjects.values()) if (mS.equals(subject.getMainSubject())) mS.addSubject(subject);
    }

    /**
     * Removes the {@link MainSubject} from {@link Main#aMSubjects} of the main when the call to
     * {@link MainSubject#removeMe} was successful
     *
     * @param s The name string of the MainSubject to be removed
     * @return Whether the removal was successful
     */
    boolean removeMainSubject(String s) {
        if (getMainSubject(s).removeMe()) {
            aMSubjects.remove(s);
            return true;
        } else return false;
    }

    /**
     * Removes the {@link MainSubject} and all its contents
     *
     * @param s The name string of the MainSubject to be removed
     */
    void removeMainSubjectAndAllContents(String s) {
        MainSubject mS = getMainSubject(s);
        for (Assignment a : mS.getAssignments()) removeAssignment(a);
        for (int i = 0; i < mS.getSubjects().size(); i++)
            for (Day day : getaDays()) {
                if (day.getSubjects().contains(mS.getSubjects().get(i))) {
                    day.removeSubject(mS.getSubjects().remove(i));
                    i--;
                    break;
                }
            }
        mS.removeMe();
    }

    /**
     * Removes the {@link Assignment} from its {@link MainSubject}, {@link Main#aAss} and
     * {@link Main#aassGUIs} of the main
     *
     * @param assignment The Assignment to be removed
     */
    void removeAssignment(Assignment assignment) {
        assignment.getmSubject().removeAssignment(assignment);
        inst.aAss.remove(assignment);
        inst.aassGUIs.remove(assignment.getAssGUI());
    }

    JFileChooser getFileChooser() {
        return f;
    }

    int getLessonsDoneT() {
        return lessonsDoneT;
    }

    void setLessonsDoneT(int lessonsDoneT) {
        this.lessonsDoneT = lessonsDoneT;
    }

    int getMaxPerSubject() {
        return maxPerSubject;
    }

    void setMaxPerSubject(int maxPerSubject) {
        this.maxPerSubject = maxPerSubject;
    }

    int getMaxTotal() {
        return maxTotal;
    }

    void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    boolean isCh() {
        return ch;
    }

    public void setCh(boolean ch) {
        this.ch = ch;
    }

    ArrayList<AssignmentGUI> getAassGUIs() {
        return aassGUIs;
    }

    boolean isFirst() {
        return first;
    }

    void setFirst(boolean first) {
        this.first = first;
    }

    GUI getGui() {
        return gui;
    }

    void setGui(GUI gui) {
        this.gui = gui;
    }
}