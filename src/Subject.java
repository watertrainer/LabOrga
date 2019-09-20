import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.TimeZone;

//todo convert to JPanel
public class Subject {
    /**
     * The content Pane for the Subject in the Content Pane of the Day
     */
    public JPanel content;
    /**
     * Name of the Teacher which has Lab
     */
    @Expose
    private String teacher;
    /**
     * Name of the Subject. Saved externally for saving purpose.
     */
    @Expose
    private String subjectSt;
    /**
     * The Main Subject this is associated to
     */
    private MainSubject MainSub;
    /**
     * The Label of this Subject in the Content Pane
     */
    private JLabel label;
    /**
     * Is the Subject enabled. Future Feature
     */
    private boolean enabled;
    /**
     * Has the SUbject an Assignment
     */
    private boolean hasAssign;

    /**
     * Constructor
     *
     * @param Teacher The Teacher which has Lab
     * @param MainSub The Main Subject
     */
    Subject(String Teacher, MainSubject MainSub) {
        this.teacher = Teacher;
        this.MainSub = MainSub;
        $$$setupUI$$$();
        subjectSt = MainSub.getName();
        hasAssign = true;
        MainSub.addSubject(this);
        MainSub.updateHasAssign();
    }

    /**
     * Consturctor for Gson
     */
    public Subject() {
        super();
        this.teacher = "";
        $$$setupUI$$$();
        hasAssign = true;
    }

    /**
     * init Method, called from The Day
     */
    public void init() {
        if (Main.inst.first) {
            subjectSt = MainSub.getName();
        }
        MainSub = Main.inst.getMainSubject(subjectSt);
    }

    /**
     * says the Subject, that an Assignment is added. Currently unused
     */
    public void addAuftrag() {
        setHasAssign(true);
    }

    /**
     * adds itself to the jPanle
     *
     * @param p THe Panel to which the SUbject should add itself to
     */
    public void addToGUI(JPanel p) {
        this.updateColor();
        // p.add(this, cc.xy(1,row));
        p.add(this.content);
    }

    /**
     * to String
     *
     * @return returns the Text on the JLabel
     */
    @Override
    public String toString() {
        return label.getText();
    }

    /**
     * Clone the Subject
     * Currently unused
     *
     * @param t The teacher the new Subject should have
     * @return The new Subject
     */
    public Subject clone(String t) {
        return new Subject(t, getMainSub());
    }

    /**
     * Equals Method
     *
     * @param s The Object to Compare itself to
     * @return boolean, if the Object is equal to The Subject. Only comppares the Teacher name and Main Subject
     */
    @Override
    public boolean equals(Object s) {
        if (s instanceof Subject) {
            Subject f = ((Subject) s);
            return f.teacher.equals(teacher) && f.getMainSub().equals(getMainSub());
        } else {
            return false;
        }
    }

    public String getSubjectSt() {
        return subjectSt;
    }

    public void setSubjectSt(String subjectSt) {
        this.subjectSt = subjectSt;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
        if (hasAssign)
            label.setText(getMainSub().getName() + ", " + teacher + " | LAB Auftrag Vorhanden");
        else {
            label.setText(getMainSub().getName() + ", " + teacher + " | kein LAB Auftrag Vorhanden");
        }
    }

    public String getName() {
        return getMainSub().getName();
    }

    public Color getColor() {
        return MainSub.getColor();
    }

    /**
     * updates the color of the JLabel to the Color of the Main Sub
     */
    public void updateColor() {
        if (((MainSub.getColor().getBlue() + MainSub.getColor().getRed() + MainSub.getColor().getGreen()) / 3) < 128)
            label.setForeground(Color.white);
        label.setBackground(MainSub.getColor());
    }

    public void setHasAssign(boolean hasAssign) {
        this.hasAssign = hasAssign;
        if (hasAssign)
            label.setText(getMainSub().getName() + ", " + teacher + " | LAB Auftrag Vorhanden");
        else {
            label.setText(getMainSub().getName() + ", " + teacher + " | kein LAB Auftrag Vorhanden");
        }
    }

    public MainSubject getMainSub() {
        return MainSub;
    }

    private void createUIComponents() {
        label = new JLabel();
        content = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                    g.setColor(MainSub.getDrawAttColor());
                    g.fillOval(content.getWidth() - 30, 0, content.getHeight(), content.getHeight());

            }
        };
        label.setOpaque(true);
        content.setAlignmentY(1);
        content.setAlignmentX(Box.LEFT_ALIGNMENT);
        content.setMaximumSize(new Dimension(Short.MAX_VALUE, 25));
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        content.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        label.setText("Label");
        content.add(label, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return content;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public MainSubject getMainSubject() {
        return MainSub;
    }
}
