import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.io.BufferedReader;
import java.util.*;

public class Main {
    /**
     * Instance of this CLass
     */
    public transient static Main inst;
    /**
     * All Main Subjects
     */
    @Expose
    private HashMap<String, MainSubject> aSubjects;
    /**
     * All Days with the according String eg. "Monday"
     */
    @Expose
    private HashMap<String, Day> aDays;
    /**
     * All Assignments
     */
    @Expose
    private ArrayList<Auftrag> aAss;
    /**
     * All GUIs from the Assignments. Makes it easier to Sort the Assignments in the ASsignments Tab
     */
    private ArrayList<AuftragGUI> aGuis;
    /**
     * Is this the first start of the program
     */
    @Expose
    boolean first;
    /**
     * HOw many of all Lab lessons are done already
     */
    @Expose
    private int lessonsDone;
    /**
     * How many Lab lessons have to be done per Subject
     */
    @Expose
    private int maxPerSubject;
    /**
     * How many Lab lesson have to be done collectively
     */
    @Expose
    private int maxIngs;
    /**
     * Gui of this Main
     */
    public transient GUI gui;
    /**
     * The File Chooser. Points to the FIle in which everything is saved
     */
    private transient JFileChooser f;
    /**
     * Int representation of the Day of the week
     */
    private int d;
    /**
     * Should d be updated, aka the day of week changed
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
                if (Main.inst.gui != null)
                    if (Main.inst.gui.contentPane != null)
                        Main.inst.gui.contentPane.repaint();
                if (Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_WEEK) != d) {
                    setCh(false);
                    d = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_WEEK);
                } else
                    setCh(true);
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
     * Initialisation of Main. Calls all init() Methods of the Assignments, Days and Subjects
     */
    public void init() {
        for (Day d : getaDays()) {
            d.init();
        }
        for (Auftrag a : aAss) {
            a.init();
        }
        for (MainSubject s : aSubjects.values()) {
            s.init();
        }
    }

    /**
     * adds an Assignment to the aAss list
     *
     * @param a The assignment to be added
     */
    public void addAuftrag(Auftrag a) {
        aAss.add(a);
    }

    public Collection<MainSubject> getaSubjects() {
        return aSubjects.values();
    }

    public Collection<Day> getaDays() {
        return aDays.values();
    }

    public ArrayList<Auftrag> getaAss() {
        return aAss;
    }

    public MainSubject getMainSubject(String s) {
        return aSubjects.get(s);
    }

    public Day getDay(String s) {
        return aDays.get(s);
    }

    public void addMainSubject(MainSubject s) {
        aSubjects.put(s.getName(), s);
    }

    public void addDay(Day s) {
        aDays.put(s.getDay(), s);
    }

    /**
     * Adds an Subject. Also adds the Subject to the corresponding MainSub
     *
     * @param s THe Subjectt o be added
     */
    public void addSubject(Subject s) {
        for (MainSubject m : aSubjects.values()) {
            if (m.equals(s.getMainSub()))
                m.addSubject(s);
        }
    }

    /**
     * Removes an Main Sub.
     *
     * @param s The MainSub to be removed
     * @return if the removal was successful
     */
    public boolean removeMainSub(String s) {
        MainSubject m = getMainSubject(s);
        if (m.removeMe()) {
            aSubjects.remove(s);
            return true;
        } else {
            return false;
        }
    }

    public void removeMainSubAndAllContents(String s){
        MainSubject m = getMainSubject(s);
        for(Auftrag a:m.getAuftrage()){
            removeAuftrag(a);
        }
        for(int i = 0;i<m.getSubjects().size();i++)
            for(Day d: getaDays()){
                if(d.getSubjects().contains(m.getSubjects().get(i))){
                   d.removeSubject(m.getSubjects().remove(i));
                   i--;
                   break;
                }
            }
        m.removeMe();
    }

    public void removeAuftrag(Auftrag a){
        a.getSubject().removeAuftrag(a);
        Main.inst.getaAss().remove(a);
        Main.inst.getaGuis().remove(a.getAugui());
    }

    public JFileChooser getFileChooser() {
        return f;
    }

    public int getLessonsDone() {
        return lessonsDone;
    }

    public void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }

    public int getMaxPerSubject() {
        return maxPerSubject;
    }

    public void setMaxPerSubject(int maxPerSubject) {
        this.maxPerSubject = maxPerSubject;
    }

    public int getMaxIngs() {
        return maxIngs;
    }

    public void setMaxIngs(int maxIngs) {
        this.maxIngs = maxIngs;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public boolean isCh() {
        return ch;
    }

    public void setCh(boolean ch) {
        this.ch = ch;
    }

    public ArrayList<AuftragGUI> getaGuis() {
        return aGuis;
    }

    public void setaSubjects(HashMap<String, MainSubject> aSubjects) {
        this.aSubjects = aSubjects;
    }

    public void setaDays(HashMap<String, Day> aDays) {
        this.aDays = aDays;
    }

    public void setaAss(ArrayList<Auftrag> aAss) {
        this.aAss = aAss;
    }
}
