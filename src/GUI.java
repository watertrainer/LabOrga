import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Comparator;

public class GUI extends JFrame {
    /**
     * The Tabbed Pane so you can switch between Tabs
     */
    public JTabbedPane tabbedPane1;
    /**
     * THe contentPane
     */
    public JPanel contentPane;


    /**
     * Panel for all Settings related content
     */
    public JPanel settingsPanel;
    /**
     * Settings, opens a CreateSubject DIalog
     *
     * @see CreateSubject
     */
    public JButton setiCreaSub;
    /**
     * Button to sort the LabButton
     */
    //TODO
    public JButton sortPlanButton;
    /**
     * Toggles whether the color of Days which are not today is rendered
     */
    //TODO
    public JButton toggleEnabledInSubjects;
    /**
     * Should the Subjects be sorted
     */
    private boolean sort = false;
    /**
     * Button to open the JFIleChooser to choose a save FIle
     */
    public JButton chooeSaveFile;


    /**
     * Panel in which the Assignments are rendered.
     *
     * @see AuftragGUI
     */
    public JPanel assignemntPanel;
    /**
     * Settings, Button to open an create Assignment Dialog
     *
     * @see CreateAuftrag
     */
    public JButton newLabAssignmentButton;
    /**
     * Panel to render the lessons of each SUbject. Renders all MainSubjects
     *
     * @see MainSubject
     */


    public JPanel lessonPanel;
    /**
     * Progressbar for the lessons which need to be done insgesa,t
     */
    public JProgressBar lessonsDoneProgressBar;


    /**
     * Settings, Deletes a Main Subject
     *
     * @see MainSubject
     */
    public JButton deleteSubjectButton;
    /**
     * Settings, Deletes a Subject from the Lab Plan
     *
     * @see Subject
     */
    public JButton deleteSubjectFromPlanButton;


    /**
     * The JPanel of the LabPlan
     */
    public JPanel PlanPanel;

    /**
     * The content Pane of the Day Monday
     *
     * @see Day
     */
    public JPanel cnMn;
    /**
     * JLabel which shows Monday and is colored if it is Monday
     */

    public JLabel MonLab;
    /**
     * same as cnMn
     *
     * @see Day
     */
    public JPanel cnDn;
    /**
     * JLabel which shows Dienstag and is colored if it is Tuesday
     */
    public JLabel DienLab;
    /**
     * same as cnMn
     *
     * @see Day
     */
    public JPanel cnMt;
    /**
     * Same as MonLab
     */
    public JLabel MitLab;
    /**
     * same as cnMn
     *
     * @see Day
     */
    public JPanel cnDon;
    /**
     * same as cnMn
     *
     * @see Day
     */
    public JPanel cnFr;
    /**
     * Same as MonLab
     */
    public JLabel DonLab;
    /**
     * Same as MonLab
     */
    public JLabel FreiLab;


    /**
     * Constructor
     */
    public GUI() {
        Main c = null;
        /*
        reload the save File or create a new Instance
         */
        try {
            File f1 = new File(System.getenv("TEMP") + "\\File.tmp");
            if (f1.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(f1));
                File f = new File(br.readLine());
                br.close();
                if (f.exists()) {
                    Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    BufferedReader br1 = new BufferedReader(new FileReader(f));
                    c = g.fromJson(new FileReader(f), Main.class);
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
        c.gui = this;
        $$$setupUI$$$();
        /**
         * Called on close of the Window. Saves everything and exits
         */
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Gson g = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                File f = Main.inst.getFileChooser().getSelectedFile();
                if (f == null) {
                    JOptionPane.showMessageDialog(Main.inst.gui, "Bitte wähle eine Datei zum Speichern deiner Daten aus");
                    Main.inst.getFileChooser().showOpenDialog(Main.inst.gui);
                    return;
                }
                try {
                    f.createNewFile();
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    Main.inst.setFirst(false);
                    bw.write(g.toJson(Main.inst));
                    bw.close();
                    new File(System.getenv("TEMP")+"\\File.temp").createNewFile();

                    BufferedWriter bws = new BufferedWriter(new FileWriter(System.getenv("TEMP") + "\\File.tmp"));
                    bws.write(Main.inst.getFileChooser().getSelectedFile().toPath().toString());
                    bws.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * Settings Buttons
         */
        /*
         * creates a Subject.
         *
         */
        setiCreaSub.addActionListener(e -> {
            CreateSubject f = new CreateSubject();
            f.pack();
            f.setVisible(true);
        });
        /*
         * Choose a save File
         */
        chooeSaveFile.addActionListener(e -> Main.inst.getFileChooser().showOpenDialog(Main.inst.gui));
        /*
        Create a new Lab Assignment
         */
        newLabAssignmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAuftrag f = new CreateAuftrag();
                f.pack();
                f.setVisible(true);
            }
        });
        /*
        Delete a Main Subject
         */
        deleteSubjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainSubject w = (((MainSubject) JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection", JOptionPane.DEFAULT_OPTION, null, Main.inst.getaSubjects().toArray(), "0")));
                if (Main.inst.removeMainSubject(w.getName())) {
                    JOptionPane.showMessageDialog(Main.inst.gui, "Erfolgreich gelöscht");
                } else {
                    int chose = JOptionPane.showConfirmDialog(Main.inst.gui, "Dieses Fach ist noch in deinem Stundenplan zu finden");
                    if (chose == JOptionPane.YES_OPTION) {
                        Main.inst.removeMainSubjectAndAllContents(w.getName());
                    }
                }
            }
        });
        /*
        Delete a Subject from the Plan
         */
        deleteSubjectFromPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Day w = (((Day) JOptionPane.showInputDialog(null, "Wähle einen Tag aus", "Selection", JOptionPane.DEFAULT_OPTION, null, Main.inst.getaDays().toArray(), "0")));
                Subject s = (((Subject) JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection", JOptionPane.DEFAULT_OPTION, null, w.getSubjects().toArray(), "0")));
                w.removeSubject(s);
            }
        });


        /**
         * Assignment Panel
         */
        assignemntPanel.setLayout(new BoxLayout(assignemntPanel, BoxLayout.Y_AXIS));
        JButton btn = new JButton();
        btn.setText("Sortieren: Tage");
        Main.inst.getaGUIs().sort(Comparator.comparingLong(a -> a.getA().getRemaining()));
        for (AuftragGUI a : Main.inst.getaGUIs()) {
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
                Main.inst.getaGUIs().sort(Comparator.comparingLong(a -> a.getA().getRemaining()));
                for (AuftragGUI a : Main.inst.getaGUIs()) {
                    assignemntPanel.remove(a.content);
                    assignemntPanel.add(a.content);
                }
            } else {
                btn.setText("Sortieren: Fach");
                Main.inst.getaGUIs().sort(Comparator.comparingLong(a -> a.getA().getRemaining()));
                Main.inst.getaGUIs().sort(Comparator.comparing(a -> a.getA().getSubject().getName()));
                for (AuftragGUI a : Main.inst.getaGUIs()) {
                    assignemntPanel.remove(a.content);
                    assignemntPanel.add(a.content);
                }
            }
        });
        assignemntPanel.add(btn);
        /**
         * Lesson Panel
         */
        lessonPanel.setLayout(new BoxLayout(lessonPanel, BoxLayout.Y_AXIS));

        /**
         * Inits the Main
         */
        Main.inst.init();
    }

    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setContentPane(frame.contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getAuftragPanel() {
        return assignemntPanel;
    }

    /**
     * Asks the User for an Amount of Lessons he needs to do
     *
     * @return the Entered number of Lab lessons
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
     * @return THe number of lessons per Subject
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
            cnMn = new Day("Montag", new JPanel()).getContent();
            cnDn = new Day("Dienstag", new JPanel()).getContent();
            cnMt = new Day("Mittwoch", new JPanel()).getContent();
            cnDon = new Day("Donnerstag", new JPanel()).getContent();
            cnFr = new Day("Freitag", new JPanel()).getContent();
        } else {
            cnMn = Main.inst.getDay("Montag").getContent();
            cnDn = Main.inst.getDay("Dienstag").getContent();
            cnMt = Main.inst.getDay("Mittwoch").getContent();
            cnDon = Main.inst.getDay("Donnerstag").getContent();
            cnFr = Main.inst.getDay("Freitag").getContent();
        }
        assignemntPanel = new JPanel();
        this.lessonsDoneProgressBar = new JProgressBar();
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
        PlanPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                if (Main.inst.isCh())
                    return;
                switch (Main.inst.getD()) {
                    case 2:
                        MonLab.setOpaque(true);
                        MonLab.setBackground(Color.GREEN);
                        Main.inst.getDay("Montag").setEnabled(true);
                        break;
                    case 3:
                        MonLab.setOpaque(false);
                        DienLab.setOpaque(true);
                        DienLab.setBackground(Color.green);
                        break;
                    case 4:
                        DienLab.setOpaque(false);
                        MitLab.setOpaque(true);
                        MitLab.setBackground(Color.green);
                        break;
                    case 5:
                        MitLab.setOpaque(false);
                        DonLab.setOpaque(true);
                        DonLab.setBackground(Color.green);
                        break;
                    case 6:
                        DonLab.setOpaque(false);
                        FreiLab.setOpaque(true);
                        FreiLab.setBackground(Color.green);
                        break;
                    case 7:
                        FreiLab.setOpaque(false);
                        break;
                }
            }
        };
        lessonPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                lessonsDoneProgressBar.setValue(Main.inst.getLessonsDone());
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
        PlanPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(PlanPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MonLab = new JLabel();
        MonLab.setText("Montag");
        PlanPanel.add(MonLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        PlanPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(cnMn);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        DienLab = new JLabel();
        DienLab.setText("Dienstag");
        panel3.add(DienLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(cnDn);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FreiLab = new JLabel();
        FreiLab.setText("Freitag");
        panel4.add(FreiLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel4.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(cnFr);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MitLab = new JLabel();
        MitLab.setText("Mittwoch");
        panel5.add(MitLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel5.add(scrollPane4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane4.setViewportView(cnMt);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        DonLab = new JLabel();
        DonLab.setText("Donnerstag");
        panel6.add(DonLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        panel6.add(scrollPane5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane5.setViewportView(cnDon);
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
