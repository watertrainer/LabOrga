import com.google.gson.annotations.Expose;
import com.jgoodies.forms.layout.CellConstraints;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Subject extends JLabel {
    @Expose
    private String teacher;
    @Expose
    private String subjectSt;
    private MainSubject MainSub;

    private boolean hasAssign;

    public Subject(String Teacher, MainSubject MainSub) {
        super();
        this.teacher = Teacher;
        this.MainSub = MainSub;
        subjectSt = MainSub.getName();
        hasAssign = true;
        this.setBackground(MainSub.getColor());

        this.setOpaque(true);
        MainSub.addSubject(this);
        MainSub.updateHasAssign();

    }
    public Subject(){
        super();
        this.teacher = "";
        this.setOpaque(true);
        hasAssign = true;
    }

    public void init(){
        if(Main.inst.first){
            subjectSt = MainSub.getName();
        }
        MainSub = Main.inst.getMainSubject(subjectSt);
    }
    public void addAuftrag(){
        setHasAssign(true);
    }

    public void addToGUI(JPanel p, int row, CellConstraints cc){
        this.updateColor();
       // p.add(this, cc.xy(1,row));
        p.add(this);

    }
    @Override
    public String toString(){
        return ""+this.getMainSub().getName();
    }
    public Subject clone(String t){
        return new Subject(t, getMainSub());
    }


    @Override
    public boolean equals(Object s){
        if(s instanceof Subject){
           Subject f = ((Subject)s);
        return f.teacher.equals(teacher)&&f.getMainSub().equals(getMainSub());
        }
        else{
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
    }

    public String getName() {
        return getMainSub().getName();
    }

    public Color getColor() {
        return MainSub.getColor();
    }


    public void updateColor() {
        if(((MainSub.getColor().getBlue()+ MainSub.getColor().getRed()+ MainSub.getColor().getGreen())/3) < 128)
            this.setForeground(Color.white);
        this.setBackground(MainSub.getColor());
    }
    public void setHasAssign(boolean hasAssign) {
        this.hasAssign = hasAssign;
        System.out.println("Hello");
        if(hasAssign)
            this.setText(getMainSub().getName() +", "+ teacher +" |LAB Auftrag Vorhanden");
        else{
            this.setText(getMainSub().getName() +", "+ teacher +" |kein LAB Auftrag Vorhanden");
        }
    }

    public MainSubject getMainSub() {

        return MainSub;
    }
}
