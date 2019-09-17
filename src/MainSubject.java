import com.google.gson.annotations.Expose;

import java.awt.*;
import java.util.ArrayList;

public class MainSubject {
    /**
     * The name of the Main Sub
     */
    @Expose
    private String name;
    /**
     * Color of the Subject
     */
    private Color color;
    @Expose
    private boolean hasAssign = false;
    /**
     * the rgb Value of the Color. NEeded for saving purpose
     */
    @Expose
    private int rgbValue;
    /**
     * Lessons Done of this Subject
     */
    @Expose
    private int lessonsDone;
    /**
     * Assignments in this Subject
     */
    private ArrayList<Auftrag> Auftrage;
    /**
     * Subjects for the LAb Plan of this Type
     */
    private ArrayList<Subject> Subjects;
    /**
     * Gui to show how many lessons are done in this Subject already
     */
    private StundenGUI stdgui;

    public MainSubject(String Name, Color color) {
        super();
        this.name = Name;
        this.color = color;
        this.rgbValue = color.getRGB();
        Subjects = new ArrayList<>();
        Auftrage = new ArrayList<>();
        init();
    }

    /**
     * Constructor for saving purpose
     */
    public MainSubject() {
        super();
        this.name = "";
        this.color = null;
        this.rgbValue = 0;
        this.hasAssign = false;
        Subjects = new ArrayList<>();
        stdgui = new StundenGUI(this);
        Auftrage = new ArrayList<>();
    }

    /**
     * Removes itself from the Guis
     *
     * @return false if there are still Subjects in the Lab Plan
     */
    public boolean removeMe() {
        if (Subjects.size() > 0) {
            return false;
        } else {
            Main.inst.gui.StundenPanel.remove(stdgui.content);
            Main.inst.gui.StundenPanel.revalidate();
            Main.inst.gui.StundenPanel.repaint();
            return true;
        }
    }

    /**
     * Initialasation. Adds itself to the GUIs
     */
    public void init() {
        stdgui = new StundenGUI(this);
        Main.inst.gui.StundenPanel.add(stdgui.content);
        Main.inst.gui.StundenPanel.revalidate();
        Main.inst.gui.StundenPanel.repaint();
        updateHasAssign();
    }

    /**
     * updates, if the Subject has an Assignment
     */
    public void updateHasAssign() {
        setHasAssign(Auftrage.size() > 0);
    }

    public void addSubject(Subject s) {
        Subjects.add(s);
    }

    public void removeSubject(Subject s) {
        Subjects.remove(s);
    }

    @Override
    public String toString() {
        return "" + this.name;
    }

    public MainSubject clone(String t) {
        return new MainSubject(name, new Color(rgbValue));
    }

    @Override
    public boolean equals(Object s) {
        if (s instanceof MainSubject) {
            MainSubject f = ((MainSubject) s);
            return f.getName().equals(name) && f.getColor().equals(color);
        } else {
            return false;
        }
    }

    /**
     * Calculation for the Attention level Color
     *
     * @return Colro which represents the Attentin level
     */
    public Color getDrawAttColor() {
        Color ret = Color.green;
        if (Auftrage.size() == 0)
            ret = Color.gray;
        for (Auftrag a : getAuftrage()) {
            if (a.needAttention() == 1) {
                ret = Color.red;
            } else if (a.needAttention() == 2) {
                if (!ret.equals(Color.red))
                    ret = Color.orange;
            }
        }
        return ret;
    }

    public Subject getAsSubject() {
        return new Subject("", this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        color = new Color(rgbValue);
        return color;
    }

    /**
     * sets The rgbValue and updates the Color. Saving purpose
     *
     * @param rgbValue The Rdb Value on which this Subject should be set to
     */
    public void setRgbValue(int rgbValue) {
        this.rgbValue = rgbValue;
        this.setColor(new Color(rgbValue));
        for (Subject s : Subjects) {
            s.updateColor();
        }
    }

    public void addAuftrag(Auftrag a) {
        Auftrage.add(a);
        updateHasAssign();
    }

    public void removeAuftrag(Auftrag a) {
        Auftrage.remove(a);
        updateHasAssign();
    }

    public int getRgbValue() {
        return rgbValue;
    }

    public void setColor(Color color) {
        this.color = color;
        this.rgbValue = color.getRGB();
    }

    public void setHasAssign(boolean hasAssign) {
        this.hasAssign = hasAssign;
        for (Subject s : Subjects) {
            s.setHasAssign(hasAssign);
        }
    }

    public int getLessonsDone() {
        return lessonsDone;
    }

    public void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }

    public ArrayList<Auftrag> getAuftrage() {
        return Auftrage;
    }
}
