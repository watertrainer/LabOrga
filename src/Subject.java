import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.awt.*;

//TODO convert to JPanel
class Subject {

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
    private MainSubject mainSubject;

    /**
     * The Label of this Subject in the content pane
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
     * @param teacher     The Teacher which has Lab
     * @param mainSubject The MainSubject
     */
    Subject(String teacher, MainSubject mainSubject) {
        this.teacher = teacher;
        this.mainSubject = mainSubject;
        $$$setupUI$$$();
        subjectSt = mainSubject.getName();
        hasAssign = true;
        mainSubject.addSubject(this);
        mainSubject.updateHasAssign();
    }

    /**
     * Consturctor for Gson
     */
    Subject() {
        teacher = "";
        $$$setupUI$$$();
        hasAssign = true;
    }

    /**
     * init Method, called from The Day
     */
    void init() {
        if (Main.inst.isFirst()) subjectSt = mainSubject.getName();
        mainSubject = Main.inst.getMainSubject(subjectSt);
        mainSubject.addSubject(this);
    }

    /**
     * says the Subject, that an Assignment is added. Currently unused
     */
    public void addAuftrag() {
        setHasAssign(true);
    }

    /**
     * Adds itself to the {@link JPanel}
     *
     * @param p The Panel to which the Subject should add itself to
     */
    void addToGUI(JPanel p) {
        updateColor();
        // p.add(this, cc.xy(1,row));
        p.add(content);
    }

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
        return new Subject(t, mainSubject);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Subject)) return false;
        Subject subject = (Subject) obj;
        if (enabled != subject.enabled) return false;
        if (hasAssign != subject.hasAssign) return false;
        if (!teacher.equals(subject.teacher)) return false;
        if (!subjectSt.equals(subject.subjectSt)) return false;
        return mainSubject.equals(subject.mainSubject);
    }

    public String getSubjectSt() {
        return subjectSt;
    }

    public void setSubjectSt(String subjectSt) {
        this.subjectSt = subjectSt;
    }

    String getTeacher() {
        return teacher;
    }

    void setTeacher(String teacher) {
        this.teacher = teacher;
        if (hasAssign) label.setText(mainSubject.getName() + ", " + teacher + " | Lab Auftrag vorhanden");
        else label.setText(mainSubject.getName() + ", " + teacher + " | kein Lab Auftrag vorhanden");
    }

    public String getName() {
        return mainSubject.getName();
    }

    public Color getColor() {
        return mainSubject.getColor();
    }

    /**
     * Updates the {@link Color} of the {@link JLabel} to the {@link Color} of the {@link MainSubject}
     */
    void updateColor() {
        if ((mainSubject.getColor().getBlue() + mainSubject.getColor().getRed() + mainSubject.getColor().getGreen()) / 3 < 128)
            label.setForeground(Color.white);
        label.setBackground(mainSubject.getColor());
    }

    void setHasAssign(boolean hasAssign) {
        this.hasAssign = hasAssign;
        if (hasAssign) label.setText(mainSubject.getName() + ", " + teacher + " | Lab Auftrag vorhanden");
        else label.setText(mainSubject.getName() + ", " + teacher + " | kein Lab Auftrag vorhanden");
    }

    MainSubject getMainSubject() {
        return mainSubject;
    }

    private void createUIComponents() {
        label = new JLabel();
        content = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(mainSubject.getDrawAttColor());
                g.fillOval(content.getWidth() - 30, 0, content.getHeight(), content.getHeight());
            }
        };
        label.setOpaque(true);
        content.setAlignmentY(1);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);
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

}