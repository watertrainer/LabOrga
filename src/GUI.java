import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class GUI extends JFrame {
    public JTabbedPane tabbedPane1;
    public JPanel panel1;
    public JButton setiCreaSub;
    public JPanel cnMn;
    public JPanel cnDn;
    public JPanel cnMt;
    public JPanel cnDon;
    public JPanel cnFr;
    public JButton wähleZielDateiButton;
    public JPanel AuftragPanel;
    public JButton neuerLabAuftragButton;
    public JPanel StundenPanel;
    public JProgressBar progressBar1;
    public JButton löscheFachButton;
    public JButton löscheFachAusPlanButton;
    public JLabel MonLab;
    public JLabel DienLab;
    public JLabel MitLab;
    public JLabel DonLab;
    public JLabel FreiLab;
    public JPanel PlanPanel;
    public Subject y;
    public JLabel a;
    boolean sort = false;

    public GUI() {
        Main c = null;

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
                    Main.inst.first = false;
                    bw.write(g.toJson(Main.inst));
                    bw.close();
                    BufferedWriter bws = new BufferedWriter(new FileWriter(System.getenv("TEMP") + "\\File.tmp"));
                    bws.write(Main.inst.getFileChooser().getSelectedFile().toPath().toString());
                    bws.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });


        setiCreaSub.addActionListener(e -> {
            JFrame f = new JFrame();
            f.setContentPane(new CreateSubjectDialog(f).content);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.pack();
            f.setVisible(true);
        });
        wähleZielDateiButton.addActionListener(e -> Main.inst.getFileChooser().showOpenDialog(Main.inst.gui));

        AuftragPanel.setLayout(new BoxLayout(AuftragPanel, BoxLayout.Y_AXIS));
        JButton btn = new JButton();
        btn.setText("Sortieren: Tage");
        Main.inst.getaGuis().sort(Comparator.comparingLong(a -> a.getA().getRemaining()));
        for (AuftragGUI a : Main.inst.getaGuis()) {
            AuftragPanel.remove(a.content);
            AuftragPanel.add(a.content);

        }
        btn.addActionListener(e -> {
            sort = !sort;
            if (sort) {
                btn.setText("Sortieren: Tage");
                Main.inst.getaGuis().sort(Comparator.comparingLong(a -> a.getA().getRemaining()));
                for (AuftragGUI a : Main.inst.getaGuis()) {
                    AuftragPanel.remove(a.content);
                    AuftragPanel.add(a.content);

                }

            } else {
                btn.setText("Sortieren: Fach");
                Main.inst.getaGuis().sort((a, b) -> Long.compare(a.getA().getRemaining(), b.getA().getRemaining()));
                Main.inst.getaGuis().sort(Comparator.comparing(a -> a.getA().getSubject().getName()));
                for (AuftragGUI a : Main.inst.getaGuis()) {
                    AuftragPanel.remove(a.content);
                    AuftragPanel.add(a.content);

                }
            }
        });
        AuftragPanel.add(btn);
        StundenPanel.setLayout(new BoxLayout(StundenPanel, BoxLayout.Y_AXIS));

        neuerLabAuftragButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateAuftrag f = new CreateAuftrag();
                f.pack();
                f.setVisible(true);
            }
        });
        Main.inst.init();
        löscheFachButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainSubject w = (((MainSubject) JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection", JOptionPane.DEFAULT_OPTION, null, Main.inst.getaSubjects().toArray(), "0")));
                if (Main.inst.removeMainSub(w.getName())) {
                    JOptionPane.showMessageDialog(Main.inst.gui, "Erfolgreich gelöscht");
                } else {
                    JOptionPane.showMessageDialog(Main.inst.gui, "Dieses Fach ist noch in deinem Stundenplan zu finden");
                }


            }
        });
        löscheFachAusPlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Day w = (((Day) JOptionPane.showInputDialog(null, "Wähle einen Tag aus", "Selection", JOptionPane.DEFAULT_OPTION, null, Main.inst.getaDays().toArray(), "0")));
                Subject s = (((Subject) JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection", JOptionPane.DEFAULT_OPTION, null, w.getSubjects().toArray(), "0")));
                w.removeSubject(s);
            }
        });
    }


    public static void main(String[] args) {
        GUI frame = new GUI();
        frame.setContentPane(frame.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getAuftragPanel() {
        return AuftragPanel;
    }

    private void createUIComponents() {
        if (Main.inst.first) {
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
        AuftragPanel = new JPanel();
        this.progressBar1 = new JProgressBar();
        if (Main.inst.first) {
            int g = Integer.parseInt(JOptionPane.showInputDialog("Wieviele Lab Stunden musst du INSGESAMT machen?"));
            int f = Integer.parseInt(JOptionPane.showInputDialog("Wieviele Lab Stunden musst du pro Fach machen?"));
            Main.inst.setMaxIngs(g);
            Main.inst.setMaxPerSubject(f);
        }
        progressBar1.setMaximum(Main.inst.getMaxIngs());
        progressBar1.setMinimum(0);
        progressBar1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        progressBar1.setBorderPainted(true);
        progressBar1.setBackground(Color.white);
        progressBar1.setForeground(Color.green);
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
        StundenPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                progressBar1.setValue(Main.inst.getLessonsDone());
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
        panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setPreferredSize(new Dimension(900, 500));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        tabbedPane1.addTab("LabPlan", panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, BorderLayout.CENTER);
        PlanPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(PlanPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MonLab = new JLabel();
        MonLab.setText("Montag");
        PlanPanel.add(MonLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        PlanPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(cnMn);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        DienLab = new JLabel();
        DienLab.setText("Dienstag");
        panel4.add(DienLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel4.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setViewportView(cnDn);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel5, new com.intellij.uiDesigner.core.GridConstraints(0, 4, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        FreiLab = new JLabel();
        FreiLab.setText("Freitag");
        panel5.add(FreiLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel5.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane3.setViewportView(cnFr);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        MitLab = new JLabel();
        MitLab.setText("Mittwoch");
        panel6.add(MitLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel6.add(scrollPane4, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane4.setViewportView(cnMt);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        DonLab = new JLabel();
        DonLab.setText("Donnerstag");
        panel7.add(DonLab, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane5 = new JScrollPane();
        panel7.add(scrollPane5, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane5.setViewportView(cnDon);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("LabAufträge", panel8);
        final JScrollPane scrollPane6 = new JScrollPane();
        panel8.add(scrollPane6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane6.setViewportView(AuftragPanel);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("LabStunden", panel9);
        final JScrollPane scrollPane7 = new JScrollPane();
        panel9.add(scrollPane7, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        StundenPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane7.setViewportView(StundenPanel);
        StundenPanel.add(progressBar1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        StundenPanel.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("LabHeft", panel10);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Einstellungen", panel11);
        setiCreaSub = new JButton();
        setiCreaSub.setText("Neues Fach");
        panel11.add(setiCreaSub, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel11.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        wähleZielDateiButton = new JButton();
        wähleZielDateiButton.setText("Wähle Ziel Datei");
        panel11.add(wähleZielDateiButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        neuerLabAuftragButton = new JButton();
        neuerLabAuftragButton.setText("Neuer LabAuftrag");
        panel11.add(neuerLabAuftragButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel11.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        löscheFachButton = new JButton();
        löscheFachButton.setText("Lösche Fach");
        panel11.add(löscheFachButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        löscheFachAusPlanButton = new JButton();
        löscheFachAusPlanButton.setText("Lösche Fach aus Plan");
        panel11.add(löscheFachAusPlanButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
