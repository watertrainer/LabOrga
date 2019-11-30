import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Day implements ActionListener {

    /**
     * An {@link ArrayList} of {@linkplain Subject Subjects} which are in this day
     */
    @Expose
    private final ArrayList<Subject> subjects;

    /**
     * The {@link JButton} to add a {@link Subject} to this day
     */
    private JButton btn;

    /**
     * Whether this day should draw itself in color
     */
    private boolean enabled;

    /**
     * {@link String} representation of this day. This {@link String} is also the key of this day in the
     * {@link java.util.HashMap} aDays in {@link Main}.
     */
    @Expose
    private final String daySt;

    /**
     * The {@link JPanel} of this day in the Lab Plan Tab
     */
    private transient JPanel content;

    /**
     * Constructor
     *
     * @param daySt    The name of the day
     * @param content The JPanel where the day is rendered to
     */
    Day(String daySt, JPanel content) {
        this.daySt = daySt;
        this.content = content;
        subjects = new ArrayList<>();
        if (Main.inst.isFirst()) Main.inst.addDay(this);
        for (Subject subject : subjects) subject.addToGUI(content);
    }

    /**
     * Constructor for saving purpose
     */
    Day() {
        content = new JPanel();
        subjects = new ArrayList<>();
        daySt = "";
    }

    /**
     * Initialization of the day
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
        }
    }

    /**
     * ActionPerformed for the {@linkplain Day#btn JButton btn} which adds a {@link Subject} to {@link Day#subjects} of
     * that day
     *
     * @param e The representation of the button press event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainSubject mS = (MainSubject) JOptionPane.showInputDialog(null, "Bitte w\u00e4hle ein Fach aus:"
                , "Fach hinzuf\u00fcgen", JOptionPane.QUESTION_MESSAGE, null, Main.inst.getaMSubjects().toArray(), "0");
        if (mS != null) {
            Subject s = mS.getAsSubject();
            s.setTeacher(JOptionPane.showInputDialog(null, "Bitte gebe die unterrichtende Lehrkraft ein:", "Lehrkraft"
                    , JOptionPane.QUESTION_MESSAGE));
            if (!subjects.contains(s) && s.getTeacher() != null) {
                addSubject(s);
                s.addToGUI(content);
            }
        }
    }

    String getDaySt() {
        return daySt;
    }

    /**
     * Adds the {@link Subject} to {@link Day#subjects} of the day
     *
     * @param subject The Subject to be added
     */
    void addSubject(Subject subject) {
        subjects.add(subject);
    }

    ArrayList<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Removes the {@link Subject} from {@link Day#subjects} of the day
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
        }
    }

    @Override
    public String toString() {
        return daySt;
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