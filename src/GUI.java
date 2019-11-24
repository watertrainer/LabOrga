import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

public class GUI extends JFrame {

    /**
     * {@link JTabbedPane} to switch between tabs
     */
    public JTabbedPane tabbedPane1;

    /**
     * The content pane
     */
    public JPanel contentPane;

    /**
     * {@link JPanel} for all settings related content
     */
    public JPanel settingsPanel;

    /**
     * Located in {@link GUI#settingsPanel}. Opens a {@link CreateSubject} {@link Dialog}.
     */
    public JButton setiCreaSub;

    /**
     * {@link JButton} to sort the Lab Plan
     */
    //TODO
    public JButton sortPlanButton;

    /**
     * Toggles whether the {@link Color} of {@linkplain Day Days} which are not today is rendered
     */
    //TODO
    public JButton toggleEnabledInSubjects;

    /**
     * {@link JButton} to open the {@link JFileChooser} to choose the {@link File}
     */
    public JButton chooeSaveFile;

    /**
     * {@link JPanel} in which the {@linkplain Assignment Assignments} are rendered
     *
     * @see AssignmentGUI
     */
    public JPanel assignemntPanel;

    /**
     * Located in {@link GUI#settingsPanel}. {@link JButton} to open a {@link CreateAssignment} {@link Dialog}.
     */
    public JButton newLabAssignmentButton;

    /**
     * {@link JPanel} to render the lessons of every {@linkplain MainSubject subject}
     */
    public JPanel lessonPanel;

    /**
     * {@link JProgressBar} for the lessons which need to be done in total
     */
    public JProgressBar lessonsDoneProgressBar;

    /**
     * Located in {@link GUI#settingsPanel}. Deletes a {@link MainSubject}.
     */
    public JButton deleteSubjectButton;

    /**
     * Located in {@link GUI#settingsPanel}. Deletes a {@link Subject} from the Lab Plan.
     */
    public JButton deleteSubjectFromPlanButton;

    /**
     * The {@link JPanel} of the Lab Plan
     *
     * @see GUI#cpMon
     * @see GUI#cpTue
     * @see GUI#cpWed
     * @see GUI#cpThu
     * @see GUI#cpFri
     */
    public JPanel planPanel;

    /**
     * The content pane of the {@link Day} Monday
     *
     * @see GUI#monLab
     * @see GUI#cpTue
     * @see GUI#cpWed
     * @see GUI#cpThu
     * @see GUI#cpFri
     */
    public JPanel cpMon;

    /**
     * {@link JLabel} which shows "Montag" and is colored if it is Monday
     *
     * @see GUI#cpMon
     */
    public JLabel monLab;

    /**
     * The content pane of the {@link Day} Tuesday
     *
     * @see GUI#cpTue
     * @see GUI#cpMon
     * @see GUI#cpWed
     * @see GUI#cpThu
     * @see GUI#cpFri
     */
    public JPanel cpTue;

    /**
     * {@link JLabel} which shows "Dienstag" and is colored if it is Tuesday
     *
     * @see GUI#cpTue
     */
    public JLabel tueLab;

    /**
     * The content pane of the {@link Day} Wednesday
     *
     * @see GUI#wedLab
     * @see GUI#cpMon
     * @see GUI#cpTue
     * @see GUI#cpThu
     * @see GUI#cpFri
     */
    public JPanel cpWed;

    /**
     * {@link JLabel} which shows "Mittwoch" and is colored if it is Wednesday
     *
     * @see GUI#cpWed
     */
    public JLabel wedLab;

    /**
     * The content pane of the {@link Day} Thursday
     *
     * @see GUI#thuLab
     * @see GUI#cpMon
     * @see GUI#cpTue
     * @see GUI#cpWed
     * @see GUI#cpFri
     */
    public JPanel cpThu;

    /**
     * {@link JLabel} which shows "Donnerstag" and is colored if it is Thursday
     *
     * @see GUI#cpThu
     */
    public JLabel thuLab;

    /**
     * The content pane of the {@link Day} Friday
     *
     * @see GUI#friLab
     * @see GUI#cpMon
     * @see GUI#cpTue
     * @see GUI#cpWed
     * @see GUI#cpThu
     */
    public JPanel cpFri;

    /**
     * {@link JLabel} which shows "Freitag" and is colored if it is Friday
     *
     * @see GUI#cpFri
     */
    public JLabel friLab;

    /**
     * Whether the {@linkplain Assignment Assignments} should be sorted by {@link MainSubject}
     */
    private boolean sort;

    /**
     * Constructor
     */
    private GUI() {
        Main c = null;
        /*
        reload the save File or create a new Instance
         */
        try {
            File f1 = new File(System.getenv("TEMP") + "\\LabOrga.tmp");
            if (f1.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f1, StandardCharsets.UTF_8));
                File f = new File(br.readLine());
                br.close();
                if (f.exists()) {
                    Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    BufferedReader br1 = new BufferedReader(new FileReader(f, StandardCharsets.UTF_8));
                    c = g.fromJson(new FileReader(f, StandardCharsets.UTF_8), Main.class);
                    c.getFileChooser().setSelectedFile(f);
                    br1.close();
                } else {
                    c = new Main();
                }
            } else {
                c = new Main();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        c.setGui(this);
        $$$setupUI$$$();
        /*
          Called on close of the Window. Saves everything and exits
         */
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                File f = Main.inst.getFileChooser().getSelectedFile();
                if (f == null) {
                    JOptionPane.showMessageDialog(Main.inst.getGui()
                            , "Bitte wähle eine Datei zum Speichern deiner Daten aus");
                    Main.inst.getFileChooser().showOpenDialog(Main.inst.getGui());
                    f = Main.inst.getFileChooser().getSelectedFile();
                }
                try {
                    f.createNewFile();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f, StandardCharsets.UTF_8));
                    Main.inst.setFirst(false);
                    bw.write(g.toJson(Main.inst));
                    bw.close();
                    BufferedWriter bws = new BufferedWriter(new FileWriter(System.getenv("TEMP") + "\\LabOrga.tmp"
                            , StandardCharsets.UTF_8));
                    bws.write(Main.inst.getFileChooser().getSelectedFile().toPath().toString());
                    bws.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        /*
          Settings Buttons
          creates a Subject
         */
        setiCreaSub.addActionListener(e -> {
            CreateSubject f = new CreateSubject();
            f.pack();
            f.setVisible(true);
        });
        /*
         * Choose a save File
         */
        chooeSaveFile.addActionListener(e -> Main.inst.getFileChooser().showOpenDialog(Main.inst.getGui()));
        /*
        Create a new Lab Assignment
         */
        newLabAssignmentButton.addActionListener(e -> {
            CreateAssignment f = new CreateAssignment();
            f.pack();
            f.setVisible(true);
        });
        /*
        Delete a MainSubject
         */
        deleteSubjectButton.addActionListener(e -> {
            MainSubject w = (((MainSubject) JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection"
                    , JOptionPane.PLAIN_MESSAGE, null, Main.inst.getaMSubjects().toArray(), "0")));
            if (Main.inst.removeMainSubject(w.getName()))
                JOptionPane.showMessageDialog(Main.inst.getGui(), "Erfolgreich gelöscht");
            else {
                int chose = JOptionPane.showConfirmDialog(Main.inst.getGui()
                        , "Dieses Fach ist noch in deinem Stundenplan zu finden");
                if (chose == JOptionPane.YES_OPTION) Main.inst.removeMainSubjectAndAllContents(w.getName());
            }
        });
        /*
        Delete a Subject from the Plan
         */
        deleteSubjectFromPlanButton.addActionListener(e -> {
            Day w = (((Day) JOptionPane.showInputDialog(null, "Wähle einen Tag aus", "Selection"
                    , JOptionPane.PLAIN_MESSAGE, null, Main.inst.getaDays().toArray(), "0")));
            Subject s = (((Subject) JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection"
                    , JOptionPane.PLAIN_MESSAGE, null, w.getSubjects().toArray(), "0")));
            w.removeSubject(s);
        });
        /*
          Assignment Panel
         */
        assignemntPanel.setLayout(new BoxLayout(assignemntPanel, BoxLayout.PAGE_AXIS));
        JButton btn = new JButton();
        btn.setText("Sortieren: Tage");
        Main.inst.getAassGUIs().sort(Comparator.comparingLong(a -> a.getAss().getRemaining()));
        for (AssignmentGUI a : Main.inst.getAassGUIs()) {
            assignemntPanel.remove(a.content);
            assignemntPanel.add(a.content);
        }
        /*
         *sorts the Assignments
         */
        btn.addActionListener(e -> {
            sort = !sort;
            if (sort) {
                btn.setText("Sortieren: Tage");
                Main.inst.getAassGUIs().sort(Comparator.comparingLong(a -> a.getAss().getRemaining()));
                for (AssignmentGUI a : Main.inst.getAassGUIs()) {
                    assignemntPanel.remove(a.content);
                    assignemntPanel.add(a.content);
                }
            } else {
                btn.setText("Sortieren: Fach");
                Main.inst.getAassGUIs().sort(Comparator.comparingLong(a -> a.getAss().getRemaining()));
                Main.inst.getAassGUIs().sort(Comparator.comparing(a -> a.getAss().getmSubject().getName()));
                for (AssignmentGUI a : Main.inst.getAassGUIs()) {
                    assignemntPanel.remove(a.content);
                    assignemntPanel.add(a.content);
                }
            }
        });
        assignemntPanel.add(btn);
        /*
          Lesson Panel
         */
        lessonPanel.setLayout(new BoxLayout(lessonPanel, BoxLayout.PAGE_AXIS));
        /*
          Initializes the Main
         */
        Main.inst.init();
    }

    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setContentPane(frame.contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    JPanel getAuftragPanel() {
        return assignemntPanel;
    }

    /**
     * Asks the User for an Amount of Lessons he needs to do
     *
     * @return the entered number of Lab lessons
     */
    int insgStd() {
        int std, p;
        try {
            std = Integer.parseInt(JOptionPane.showInputDialog(null, "Wie viele Lab-Stunden musst du insgesamt machen?"
                    , "Insgesamte Lab-Stundenanzahl", JOptionPane.QUESTION_MESSAGE));
            if (std < 1) throw new IllegalArgumentException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dies ist keine sinnvolle Lab-Stunden Anzahl.", "Fehler"
                    , JOptionPane.ERROR_MESSAGE);
            p = JOptionPane.showConfirmDialog(null, "M\u00f6chtset du das Programm beenden?", "Programm beenden?"
                    , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (p == JOptionPane.YES_OPTION || p == JOptionPane.CLOSED_OPTION) System.exit(0);
            std = insgStd();
        }
        return std;
    }

    /**
     * Asks the user for the number of Lessons he needs to do.
     * Calcs if they make sense
     *
     * @param insgStd number of lessons he needs to do. If the input%ingsStd is 0, the input is accepted
     * @return THe number of lessons per MainSubject
     */
    int stdProFach(int insgStd) {
        int std, p;
        try {
            std = Integer.parseInt(JOptionPane.showInputDialog(null, "Wie viele Lab-Stunden musst du pro Fach machen?"
                    , "Stundenanzahl pro Fach", JOptionPane.QUESTION_MESSAGE));
            if (insgStd % std != 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Dies ist keine sinnvolle Lab-Stunden Anzahl.", "Fehler"
                    , JOptionPane.ERROR_MESSAGE);
            p = JOptionPane.showConfirmDialog(null, "M\u00f6chtset du das Programm beenden?", "Programm beenden?"
                    , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (p == JOptionPane.YES_OPTION || p == JOptionPane.CLOSED_OPTION) System.exit(0);
            std = stdProFach(insgStd);
        }
        return std;
    }

    /**
     * Initiates all UI components
     */
    private void createUIComponents() {
        if (Main.inst.isFirst()) {
            cpMon = new Day("Montag", new JPanel()).getContent();
            cpTue = new Day("Dienstag", new JPanel()).getContent();
            cpWed = new Day("Mittwoch", new JPanel()).getContent();
            cpThu = new Day("Donnerstag", new JPanel()).getContent();
            cpFri = new Day("Freitag", new JPanel()).getContent();
        } else {
            cpMon = Main.inst.getDay("Montag").getContent();
            cpTue = Main.inst.getDay("Dienstag").getContent();
            cpWed = Main.inst.getDay("Mittwoch").getContent();
            cpThu = Main.inst.getDay("Donnerstag").getContent();
            cpFri = Main.inst.getDay("Freitag").getContent();
        }
        assignemntPanel = new JPanel();
        lessonsDoneProgressBar = new JProgressBar();
        if (Main.inst.isFirst()) {
            int insgStd = insgStd();
            Main.inst.setMaxTotal(insgStd);
            Main.inst.setMaxPerSubject(stdProFach(insgStd));
        }
        lessonsDoneProgressBar.setMaximum(Main.inst.getMaxTotal());
        lessonsDoneProgressBar.setMinimum(0);
        lessonsDoneProgressBar.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        lessonsDoneProgressBar.setBorderPainted(true);
        lessonsDoneProgressBar.setBackground(Color.white);
        lessonsDoneProgressBar.setForeground(Color.green);
        //       progressBar1.setStringPainted(true);
        planPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                if (Main.inst.isCh())
                    return;
                switch (Main.inst.getD()) {
                    case 2:
                        monLab.setOpaque(true);
                        monLab.setBackground(Color.GREEN);
                        Main.inst.getDay("Montag").setEnabled(true);
                        break;
                    case 3:
                        monLab.setOpaque(false);
                        tueLab.setOpaque(true);
                        tueLab.setBackground(Color.green);
                        break;
                    case 4:
                        tueLab.setOpaque(false);
                        wedLab.setOpaque(true);
                        wedLab.setBackground(Color.green);
                        break;
                    case 5:
                        wedLab.setOpaque(false);
                        thuLab.setOpaque(true);
                        thuLab.setBackground(Color.green);
                        break;
                    case 6:
                        thuLab.setOpaque(false);
                        friLab.setOpaque(true);
                        friLab.setBackground(Color.green);
                        break;
                    case 7:
                        friLab.setOpaque(false);
                        break;
                }
            }
        };
        lessonPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                lessonsDoneProgressBar.setValue(Main.inst.getLessonsDoneT());
            }
        };
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
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.setPreferredSize(new Dimension(900, 500));
        tabbedPane1 = new JTabbedPane();
        contentPane.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("LabPlan", panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, BorderLayout.CENTER);
        planPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(planPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        monLab = new JLabel();
        monLab.setText("Montag");
        planPanel.add(monLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        planPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(cpMon);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        tueLab = new JLabel();
        tueLab.setText("Dienstag");
        panel3.add(tueLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(cpTue);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        friLab = new JLabel();
        friLab.setText("Freitag");
        panel4.add(friLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel4.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(cpFri);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        wedLab = new JLabel();
        wedLab.setText("Mittwoch");
        panel5.add(wedLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel5.add(scrollPane4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane4.setViewportView(cpWed);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        thuLab = new JLabel();
        thuLab.setText("Donnerstag");
        panel6.add(thuLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        panel6.add(scrollPane5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane5.setViewportView(cpThu);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("LabAufträge", panel7);
        final JScrollPane scrollPane6 = new JScrollPane();
        panel7.add(scrollPane6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane6.setViewportView(assignemntPanel);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("LabStunden", panel8);
        final JScrollPane scrollPane7 = new JScrollPane();
        panel8.add(scrollPane7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lessonPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane7.setViewportView(lessonPanel);
        lessonPanel.add(lessonsDoneProgressBar, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        lessonPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("LabHeft", panel9);
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Einstellungen", settingsPanel);
        setiCreaSub = new JButton();
        setiCreaSub.setText("Neues Fach");
        settingsPanel.add(setiCreaSub, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        settingsPanel.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        chooeSaveFile = new JButton();
        chooeSaveFile.setText("Wähle Ziel Datei");
        settingsPanel.add(chooeSaveFile, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        newLabAssignmentButton = new JButton();
        newLabAssignmentButton.setText("Neuer LabAuftrag");
        settingsPanel.add(newLabAssignmentButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        settingsPanel.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        deleteSubjectButton = new JButton();
        deleteSubjectButton.setText("Lösche Fach");
        settingsPanel.add(deleteSubjectButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteSubjectFromPlanButton = new JButton();
        deleteSubjectFromPlanButton.setText("Lösche Fach aus Plan");
        settingsPanel.add(deleteSubjectFromPlanButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sortPlanButton = new JButton();
        sortPlanButton.setText("Sortiere Plan nach:");
        settingsPanel.add(sortPlanButton, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        toggleEnabledInSubjects = new JButton();
        toggleEnabledInSubjects.setText("Zeige wenn nicht heute");
        settingsPanel.add(toggleEnabledInSubjects, new com.intellij.uiDesigner.core.GridConstraints(2, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}