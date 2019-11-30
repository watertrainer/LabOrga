import javax.swing.*;
import java.awt.*;

class AssignmentGUI {

    /**
     * {@link JButton} to increase the lessonsDone of the {@link Assignment}, {@link MainSubject} and lessonsDoneT in
     * {@link MainSubject}
     */
    public JButton plus;

    /**
     * {@link JLabel} to decrease the lessonsDone of the {@link Assignment}, {@link MainSubject} and lessonsDoneT in
     * {@link MainSubject}
     */
    public JButton minus;

    /**
     * {@link JLabel} which shows how many lessons are available for this {@link Assignment}
     */
    public JLabel lessons;

    /**
     * {@link JLabel} which shows how many lessons are done already for this {@link Assignment}
     */
    public JLabel lessonsDone;

    /**
     * {@link JLabel} which represents the corresponding {@link MainSubject}
     */
    public JLabel mSubject;

    /**
     * {@link JLabel} which shows how much time is remaining for the {@link Assignment}
     */
    public JLabel tillDeadline;

    /**
     * {@link JLabel} to mark the {@link Assignment} as done
     */
    public JButton assignmentDone;

    /**
     * {@link JLabel} that shows the description of the {@link Assignment}
     */
    public JLabel des;

    /**
     * The content pane of the {@link Assignment}. All other components are in here
     */
    public JPanel content;

    /**
     * The {@link Assignment} which is rendered in this class
     */
    private Assignment ass;

    /**
     * The {@link MainSubject} of the {@link Assignment}
     */
    private MainSubject mS;

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    }

    /**
     * Constructor
     */
    AssignmentGUI(Assignment ass, MainSubject mS) {
        this.ass = ass;
        this.mS = mS;
        $$$setupUI$$$();
        mSubject.setText(mS.getName());
        mSubject.setBackground(mS.getColor());
        mSubject.setOpaque(true);
        if ((ass.getmSubject().getColor().getBlue() + ass.getmSubject().getColor().getRed() + ass.getmSubject().getColor().getGreen()) / 3 < 128)
            mSubject.setForeground(Color.white);
        lessons.setText(String.valueOf(ass.getLessons()));
        des.setText(ass.getDescription());
        /*
          Called when an Lesson is done.
         */
        plus.addActionListener(e -> {
            if (!(ass.getLessonsDone() + 1 > ass.getLessons())) {
                ass.setLessonsDone(ass.getLessonsDone() + 1);
                this.mS.setLessonsDone(this.mS.getLessonsDone() + 1);
                Main.inst.setLessonsDoneT(Main.inst.getLessonsDoneT() + 1);
                Main.inst.getGui().repaint();
            }
        });
        /*
          Called when an Lessons should be removed
         */
        minus.addActionListener(e -> {
            if (!(ass.getLessonsDone() - 1 < 0)) {
                ass.setLessonsDone(ass.getLessonsDone() - 1);
                this.mS.setLessonsDone(this.mS.getLessonsDone() - 1);
                Main.inst.setLessonsDoneT(Main.inst.getLessonsDoneT() - 1);
                Main.inst.getGui().repaint();
            }
        });
        content.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        content.setOpaque(true);
        content.setBorder(BorderFactory.createLineBorder(this.mS.getColor(), 3));
        /*
          Called when the Assignment is done
         */
        assignmentDone.addActionListener(e -> {
            Main.inst.removeAssignment(ass);
            Main.inst.getGui().getAuftragPanel().remove(content);
            Main.inst.getGui().getAuftragPanel().revalidate();
            Main.inst.getGui().repaint();
        });
    }

    private void createUIComponents() {
        content = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = Math.round(ass.getRemaining());
                if (x > 5)
                    tillDeadline.setText(x + " Tage");
                else {
                    tillDeadline.setText(x + " Tage");
                    tillDeadline.setOpaque(true);
                    tillDeadline.setBackground(Color.red);
                    tillDeadline.setForeground(Color.white);
                    g.setColor(Color.red);
                    g.fillOval(content.getWidth() - 30, tillDeadline.getY(), 20, 20);
                }
                lessonsDone.setText(String.valueOf(ass.getLessonsDone()));
            }
        };
    }

    Assignment getAss() {
        return ass;
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
        content.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        content.setMaximumSize(new Dimension(2147483647, 104));
        minus = new JButton();
        minus.setText("-");
        content.add(minus, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        lessons = new JLabel();
        lessons.setText("Label");
        content.add(lessons, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lessonsDone = new JLabel();
        lessonsDone.setText("Label");
        content.add(lessonsDone, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mSubject = new JLabel();
        mSubject.setText("Label");
        content.add(mSubject, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        des = new JLabel();
        des.setText("Description");
        content.add(des, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tillDeadline = new JLabel();
        tillDeadline.setText("Label");
        content.add(tillDeadline, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        assignmentDone = new JButton();
        assignmentDone.setText("Fertig");
        content.add(assignmentDone, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        plus = new JButton();
        plus.setText("+");
        content.add(plus, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        content.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AssignmentGUI)) return false;
        AssignmentGUI assignmentGUI = (AssignmentGUI) obj;
        if (!ass.equals(assignmentGUI.ass)) return false;
        return mS.equals(assignmentGUI.mS);
    }

}