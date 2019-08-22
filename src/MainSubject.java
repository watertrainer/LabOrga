import com.google.gson.annotations.Expose;
import com.jgoodies.forms.layout.CellConstraints;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainSubject{
    @Expose
    private String name;
    private Color color;
    @Expose
    private boolean hasAssign = false;
    @Expose
    private int rgbValue;
    @Expose
    private int lessonsDone;
    private ArrayList<Auftrag> Auftrage;
    private ArrayList<Subject> Subjects;
    private StundenGUI stdgui;


    public MainSubject(String Name, Color color) {
        super();
        this.name = Name;
        this.color = color;
        this.rgbValue = color.getRGB();
        Subjects = new ArrayList<Subject>();
        Auftrage = new ArrayList<Auftrag>();
        init();

    }
    public MainSubject(){
        super();
        this.name = "";
        this.color = null;
        this.rgbValue = 0;
        this.hasAssign = false;
        Subjects = new ArrayList<Subject>();
        stdgui = new StundenGUI(this);
        Auftrage = new ArrayList<Auftrag>();

    }
    public boolean removeMe(){
        if(Subjects.size()>0){
            return false;
        }else{
            Main.inst.gui.StundenPanel.remove(stdgui.content);
            Main.inst.gui.StundenPanel.revalidate();
            Main.inst.gui.StundenPanel.repaint();
            return true;
        }
    }
    public void init(){
        stdgui = new StundenGUI(this);

        Main.inst.gui.StundenPanel.add(stdgui.content);
        Main.inst.gui.StundenPanel.revalidate();
        Main.inst.gui.StundenPanel.repaint();
        updateHasAssign();
    }
    public void updateHasAssign(){
        setHasAssign(Auftrage.size()>0);
    }
    public void addSubject(Subject s){
        Subjects.add(s);
    }
    public void removeSubject(Subject s){
        Subjects.remove(s);
    }
    @Override
    public String toString(){
        return ""+this.name;
    }
    public MainSubject clone(String t){
        return new MainSubject(name,new Color(rgbValue));
    }


    @Override
    public boolean equals(Object s){
        if(s instanceof MainSubject){
           MainSubject f = ((MainSubject)s);
        return f.getName().equals(name)&&f.getColor().equals(color);
        }
        else{
            return false;
        }

    }


    public Subject getAsSubject(){
        return new Subject("",this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Color getColor()
    {
        color = new Color(rgbValue);
        return color;
    }
    public void setRgbValue(int rgbValue) {
        this.rgbValue = rgbValue;
        this.setColor(new Color(rgbValue));
        for (Subject s: Subjects
             ) {
            s.updateColor();
        }

    }
    public void addAuftrag(Auftrag a){
        Auftrage.add(a);
        updateHasAssign();
    }
    public void removeAuftrag(Auftrag a){
        Auftrage.remove(a);
        updateHasAssign();
    }
    public int getRgbValue() {
        return rgbValue;
    }
    public void setColor(Color color) {
        this.color = color;
        this.rgbValue = color.getRGB();
        }
    public void setHasAssign(boolean hasAssign) {

        this.hasAssign = hasAssign;
        for(Subject s:Subjects){
            s.setHasAssign(hasAssign);
            System.out.println("Hello");
        }


    }
    public int getLessonsDone() {
        return lessonsDone;
    }

    public void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }




}
