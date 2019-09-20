import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Day implements ActionListener {
    /**
     * An List of Subjects which are in this Day
     */
    @Expose
    private ArrayList<Subject> Subjects;
    /**
     * THe JButton which should add an Subject to this day
     */
    private JButton btn;
    /**
     * Should the day draw itself in Color
     */
    private boolean enabled;
    /**
     * String representation of this day. THis String is also the Key of this Da in the Days HashMap in Main.
     */
    @Expose
    private String Day;
    /**
     * The JPanel of this Day in the LabPlan Tab
     */
    private transient JPanel content;

    /**
     * Constructor
     *
     * @param Day The name of the Day. Is also the Key in the HashMap aDays
     * @param content The JPanel where the Day is rendered to
     */
    public Day(String Day, JPanel content) {
        this.Day = Day;
        this.content = content;
        Subjects = new ArrayList<>();
        if (Main.inst.first)
            Main.inst.addDay(this);
        for (int i = 0; i < Subjects.size(); i++) {
            Subjects.get(i).addToGUI(content);
        }
    }

    /**
     * Constructor
     */
    public Day() {
        this.content = new JPanel();
        Subjects = new ArrayList<>();
        this.Day = "";
    }

    /**
     * Initialisation of this class
     */
    public void init() {
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        btn = new JButton("+");
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE, 25));
        content.add(btn);
        btn.addActionListener(this);
        for (Subject s : Subjects) {
            s.init();
            s.addToGUI(content);
            s.getMainSub().addSubject(s);
        }
    }

    /**
     * actionPerformed for the button which adds an Subject to this Day
     *
     * @param e The Represantion of the button press Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainSubject mS = (MainSubject) JOptionPane.showInputDialog(null, "Bitte w\u00e4hle ein Fach aus:", "Fach"
                , JOptionPane.QUESTION_MESSAGE, null, Main.inst.getaSubjects().toArray(), "0");
        if (mS != null) {
            Subject s = mS.getAsSubject();
            s.setTeacher(JOptionPane.showInputDialog(null, "Bitte gebe die unterrichtende Lehrkraft ein:", "Lehrer"
                    , JOptionPane.QUESTION_MESSAGE));
            if (!getSubjects().contains(s) && s.getTeacher() != null) {
                addSubject(s);
                s.addToGUI(getContent());
            }
        }
    }

    public void setContent(JPanel content) {
        this.content = content;
    }

    public String getDay() {
        return Day;
    }

    public void addSubject(Subject s) {
        Subjects.add(s);
    }

    public ArrayList<Subject> getSubjects() {
        return Subjects;
    }

    /**
     * Remove an Subject from this Day
     *
     * @param subject The Subject to be removed
     */
    public void removeSubject(Subject subject) {
        try {
            if (!getSubjects().remove(subject)) throw new AssertionError();
            getContent().remove(subject.content);
            subject.getMainSubject().removeSubject(subject);
            getContent().revalidate();
        } catch (Throwable t) {
            JOptionPane.showMessageDialog(null, "Das Fach konnte nicht entfernt werden.", "Fehler"
                    , JOptionPane.ERROR_MESSAGE);
            t.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getDay();
    }

    public JPanel getContent() {
        return content;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
