import com.google.gson.annotations.Expose;
import com.jgoodies.forms.layout.CellConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Day implements ActionListener {
    @Expose
    private ArrayList<Subject> Subjects;
    @Expose
    public boolean substi = false;
    private JButton btn;


    @Expose
    private String Day;

    private transient JPanel content;



    public Day(String Day, JPanel content) {
        this.Day = Day;
        this.content = content;
        Subjects = new ArrayList<Subject>();

        if(Main.inst.first)
        Main.inst.addDay(this);
        for(int i = 0;i<Subjects.size();i++){
            Subjects.get(i).addToGUI(content);
        }
    }
    public Day(){
        this.content = new JPanel();
        Subjects = new ArrayList<Subject>();
        this.Day = "";
    }
    public void init(){
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        btn = new JButton("+");
        btn.setMaximumSize(new Dimension(Short.MAX_VALUE,25));
        content.add(btn);
        btn.addActionListener(this::actionPerformed);
        for(Subject s :Subjects){
            s.init();
            s.addToGUI(content);
            s.getMainSub().addSubject(s);
        }
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        MainSubject w = (((MainSubject)JOptionPane.showInputDialog(null, "Wähle ein Fach aus", "Selection", JOptionPane.DEFAULT_OPTION, null, Main.inst.getaSubjects().toArray(), "0")));
        String t = JOptionPane.showInputDialog("Welcher Lehrer Unterrichtet");
        Subject h = w.getAsSubject();
        h.setTeacher(t);


        if(!Subjects.contains(h)){
            Subjects.add(h);
            h.addToGUI(content);
            System.out.println(Subjects);
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

    public void removeSubject(Subject s){
        Subjects.remove(s);
        content.remove(s.content);
        s.getMainSub().removeSubject(s);
        content.revalidate();
    }
    @Override
    public String toString(){
        return getDay();
    }

    public JPanel getContent(){
        return content;
    }
}
