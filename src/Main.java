import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.util.*;

public class Main {

    /**
     * Instance of this class
     */
    public static transient Main inst;
    /**
     * GUI of this main
     */
    public transient GUI gui;
    /**
     * HashMap of all MainSubjects. The key is their name string.
     */
    @Expose
    private HashMap<String, MainSubject> aSubjects;
    /**
     * HashMap with all Days mapped from their according string eg. "Monday"
     */
    @Expose
    private HashMap<String, Day> aDays;
    /**
     * ArrayList of all Assignments
     */
    @Expose
    private ArrayList<Auftrag> aAss;
    /**
     * ArrayList of all AuftragGUIs from the Assignments. Makes it easier to sort the Assignments in the Assignments Tab.
     */
    private ArrayList<AuftragGUI> aGuis;
    /**
     * Whether this is the first start of the program
     */
    @Expose
    private boolean first;
    /**
     * Number of all Lab lessons done already
     */
    @Expose
    private int lessonsDone;
    /**
     * Number of Lab lessons to be done per Subject
     */
    @Expose
    private int maxPerSubject;
    /**
     * Number of Lab lesson to be done in total of all Subjects
     */
    @Expose
    private int maxTotal;
    /**
     * The file chooser that points to the file in which everything is saved
     */
    private transient JFileChooser f;

    /**
     * Int representation of the Day of the week
     */
    private int d;

    /**
     * Whether d should be updated because day of week changed
     */
    private boolean ch;

    /**
     * Constructor
     */
    public Main() {
        inst = this;
        aSubjects = new HashMap<>();
        aDays = new HashMap<>();
        aAss = new ArrayList<>();
        aGuis = new ArrayList<>();
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
     * Initialisation of Main by calling all init() Methods of the Assignments, Days and MainSubjects
     */
    void init() {
        for (Day day : getaDays()) day.init();
        for (Auftrag a : aAss) a.init();
        for (MainSubject s : aSubjects.values()) s.init();
    }

    /**
     * Adds an Assignment to the ArrayList aAss
     *
     * @param auftrag The Assignment to be added
     */
    void addAuftrag(Auftrag auftrag) {
        aAss.add(auftrag);
    }

    Collection<MainSubject> getaSubjects() {
        return aSubjects.values();
    }

    public void setaSubjects(HashMap<String, MainSubject> aSubjects) {
        this.aSubjects = aSubjects;
    }

    Collection<Day> getaDays() {
        return aDays.values();
    }

    public void setaDays(HashMap<String, Day> aDays) {
        this.aDays = aDays;
    }

    ArrayList<Auftrag> getaAss() {
        return aAss;
    }

    public void setaAss(ArrayList<Auftrag> aAss) {
        this.aAss = aAss;
    }

    MainSubject getMainSubject(String s) {
        return aSubjects.get(s);
    }

    Day getDay(String s) {
        return aDays.get(s);
    }

    void addMainSubject(MainSubject mainSubject) {
        aSubjects.put(mainSubject.getName(), mainSubject);
    }

    void addDay(Day day) {
        aDays.put(day.getDay(), day);
    }

    /**
     * Adds a Subject. Also adds the Subject to the corresponding MainSubject.
     *
     * @param subject The Subject o be added
     */
    public void addSubject(Subject subject) {
        for (MainSubject ms : aSubjects.values()) if (ms.equals(subject.getMainSub())) ms.addSubject(subject);
    }

    /**
     * Removes a MainSubject
     *
     * @param s The MainSub to be removed
     * @return if the removal was successful
     */
    boolean removeMainSubject(String s) {
        MainSubject m = getMainSubject(s);
        if (m.removeMe()) {
            aSubjects.remove(s);
            return true;
        } else {
            return false;
        }
    }

    void removeMainSubAndAllContents(String s) {
        MainSubject ms = getMainSubject(s);
        for (Auftrag a : ms.getAuftrage()) {
            removeAuftrag(a);
        }
        for (int i = 0; i < ms.getSubjects().size(); i++)
            for (Day day : getaDays()) {
                if (day.getSubjects().contains(ms.getSubjects().get(i))) {
                    day.removeSubject(ms.getSubjects().remove(i));
                    i--;
                    break;
                }
            }
        ms.removeMe();
    }

    void removeAuftrag(Auftrag a) {
        a.getSubject().removeAuftrag(a);
        inst.aAss.remove(a);
        inst.aGuis.remove(a.getAssGUI());
    }

    JFileChooser getFileChooser() {
        return f;
    }

    int getLessonsDone() {
        return lessonsDone;
    }

    void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
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

    ArrayList<AuftragGUI> getaGuis() {
        return aGuis;
    }

    boolean isFirst() {
        return first;
    }

    void setFirst(boolean first) {
        this.first = first;
    }
}