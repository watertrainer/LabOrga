import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Day implements ActionListener {

    /**
     * An ArrayList of Subjects which are in this day
     */
    @Expose
    private ArrayList<Subject> subjects;

    /**
     * The JButton which should add an Subject to this day
     */
    private JButton btn;

    /**
     * Whether the day should draw itself in color
     */
    private boolean enabled;

    /**
     * String representation of this day. This String is also the key of this day in the aDays HashMap in Main.
     */
    @Expose
    private String day;

    /**
     * The JPanel of this day in the LabPlan Tab
     */
    private transient JPanel content;

    /**
     * Constructor
     *
     * @param day     The name of the day. It is also the key in the HashMap aDays in Main.
     * @param content The JPanel where the day is rendered to
     */
    Day(String day, JPanel content) {
        this.day = day;
        this.content = content;
        subjects = new ArrayList<>();
        if (Main.inst.isFirst())
            Main.inst.addDay(this);
        for (Subject subject : subjects) {
            subject.addToGUI(content);
        }
    }

    /**
     * Empty constructor for saving purpose
     */
    Day() {
        content = new JPanel();
        subjects = new ArrayList<>();
        day = "";
    }

    /**
     * Initialisation of this day
     */
    void init() {
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        btn = new JButton("+");
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE, 25));
        content.add(btn);
        btn.addActionListener(this);
        for (Subject s : subjects) {
            s.init();
            s.addToGUI(content);
            s.getMainSub().addSubject(s);
        }
    }

    /**
     * ActionPerformed for the button which adds an Subject to this day
     *
     * @param e The representation of the button press event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainSubject mS = (MainSubject) JOptionPane.showInputDialog(null, "Bitte w\u00e4hle ein Fach aus:", "Fach"
                , JOptionPane.QUESTION_MESSAGE, null, Main.inst.getaSubjects().toArray(), "0");
        if (mS != null) {
            Subject s = mS.getAsSubject();
            s.setTeacher(JOptionPane.showInputDialog(null, "Bitte gebe die unterrichtende Lehrkraft ein:", "Lehrer"
                    , JOptionPane.QUESTION_MESSAGE));
            if (!subjects.contains(s) && s.getTeacher() != null) {
                addSubject(s);
                s.addToGUI(content);
            }
        }
    }

    String getDay() {
        return day;
    }

    void addSubject(Subject subject) {
        subjects.add(subject);
    }

    ArrayList<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Removes a Subject from this day
     *
     * @param subject The Subject to be removed
     */
    void removeSubject(Subject subject) {
        try {
            if (!subjects.remove(subject)) throw new AssertionError();
            content.remove(subject.content);
            subject.getMainSubject().removeSubject(subject);
            content.revalidate();
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(null, "Das Fach konnte nicht entfernt werden.", "Fehler"
                    , JOptionPane.ERROR_MESSAGE);
            t.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return day;
    }

    public JPanel getContent() {
        return content;
    }

    public void setContent(JPanel content) {
        this.content = content;
    }

    public boolean isEnabled() {
        return enabled;
    }

    void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}