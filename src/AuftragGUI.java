import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuftragGUI {
    /**
     * Button to add a Lessons
     */
    public JButton plus;
    /**
     * Button to remove a lesson
     */
    public JButton minus;
    /**
     * JLabel which shows how many lessons are avaible
     */
    public JLabel lessons;
    /**
     * JLabel which shows how many lessons are alredy done
     */
    public JLabel lessonsDone;
    /**
     * JLabel which represents the corresponding MainSubject
     */
    public JLabel Fach;
    /**
     * JLabel which shows how much Time is remaining
     */
    public JLabel tillDeadline;
    /**
     * JButton to mark the Assignment as done
     */
    public JButton AuftragDone;
    /**
     *
     */
    public JLabel Des;
    public JPanel content;
    private Auftrag a;
    private MainSubject ms;

    AuftragGUI(Auftrag a, MainSubject m) {
        super();
        this.a = a;
        ms = m;
        $$$setupUI$$$();
        Fach.setText(m.getName());
        Fach.setBackground(m.getColor());
        Fach.setOpaque(true);
        if (((a.getSubject().getColor().getBlue() + a.getSubject().getColor().getRed() + a.getSubject().getColor().getGreen()) / 3) < 128)
            Fach.setForeground(Color.white);
        lessons.setText(a.getLessons() + "");
        Des.setText(a.getDescription());
        plus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(a.getLessonsDone() + 1 > a.getLessons())) {
                    a.setLessonsDone(a.getLessonsDone() + 1);
                    ms.setLessonsDone(ms.getLessonsDone() + 1);
                    Main.inst.setLessonsDone(Main.inst.getLessonsDone() + 1);
                }
            }
        });
        minus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!(a.getLessonsDone() - 1 < 0))
                    a.setLessonsDone(a.getLessonsDone() - 1);
                ms.setLessonsDone(ms.getLessonsDone() - 1);
                Main.inst.setLessonsDone(Main.inst.getLessonsDone() - 1);
            }
        });
        content.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        content.setOpaque(true);
        content.setBorder(BorderFactory.createLineBorder(ms.getColor(), 3));
        AuftragDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ms.removeAuftrag(a);
                Main.inst.getaAss().remove(a);
                a.finished();
                Main.inst.gui.getAuftragPanel().remove(content);
                Main.inst.gui.getAuftragPanel().revalidate();
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    }

    private void createUIComponents() {
        content = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                int x = Math.round(a.getRemaining());
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
                lessonsDone.setText(a.getLessonsDone() + "");
            }
        };
    }

    Auftrag getA() {
        return a;
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
        Fach = new JLabel();
        Fach.setText("Label");
        content.add(Fach, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 3, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Des = new JLabel();
        Des.setText("Description");
        content.add(Des, new com.intellij.uiDesigner.core.GridConstraints(3, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tillDeadline = new JLabel();
        tillDeadline.setText("Label");
        content.add(tillDeadline, new com.intellij.uiDesigner.core.GridConstraints(1, 2, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        AuftragDone = new JButton();
        AuftragDone.setText("Fertig");
        content.add(AuftragDone, new com.intellij.uiDesigner.core.GridConstraints(3, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuftragGUI that = (AuftragGUI) o;
        return getA().equals(that.getA());
    }

    @Override
    public int hashCode() {
        return getA().hashCode();
    }

}
