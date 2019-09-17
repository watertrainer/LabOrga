import javax.swing.*;
import java.awt.*;

public class StundenGUI {
    public JPanel content;
    public JLabel lessonDone;
    public JLabel lessons;
    public JProgressBar progressBar1;
    public JLabel Fach;
    private MainSubject ms;

    StundenGUI(MainSubject s) {
        this.ms = s;
        $$$setupUI$$$();
        content.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        content.setOpaque(true);
        content.setBorder(BorderFactory.createLineBorder(ms.getColor(), 3));
        lessonDone.setText(Main.inst.getMaxPerSubject() + "");
        Fach.setText(ms.getName());
        Fach.setBackground(ms.getColor());
        Fach.setOpaque(true);
        if (((ms.getColor().getBlue() + ms.getColor().getRed() + ms.getColor().getGreen()) / 3) < 128)
            Fach.setForeground(Color.white);
    }

    private void createUIComponents() {
        content = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                progressBar1.setValue(ms.getLessonsDone());
                lessons.setText(ms.getLessonsDone() + "");
            }
        };
        progressBar1 = new JProgressBar();
        progressBar1.setMinimum(0);
        progressBar1.setMaximum(Main.inst.getMaxPerSubject());
        progressBar1.setBorderPainted(true);
        progressBar1.setBorder(BorderFactory.createLineBorder(ms.getColor(), 2));
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
        content.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        lessonDone = new JLabel();
        lessonDone.setText("Label");
        content.add(lessonDone, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lessons = new JLabel();
        lessons.setText("Label");
        content.add(lessons, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        content.add(progressBar1, new com.intellij.uiDesigner.core.GridConstraints(2, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        content.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        content.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Fach = new JLabel();
        Fach.setText("Label");
        content.add(Fach, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return content;
    }
}
