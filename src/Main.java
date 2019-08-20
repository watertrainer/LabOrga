import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import jdk.nashorn.internal.parser.JSONParser;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Date;

public class Main
{
    public transient static Main inst;
    @Expose
    private ArrayList<MainSubject> aSubjects;
    @Expose
    private ArrayList<Day> aDays;
    @Expose
    private ArrayList<Auftrag> aAss;
    @Expose
    public boolean first;
    @Expose
    private int lessonsDone;
    @Expose
    private int maxPerSubject;
    @Expose
    private int maxIngs;

    public void setaSubjects(ArrayList<MainSubject> aSubjects) {
        this.aSubjects = aSubjects;
    }

    public void setaDays(ArrayList<Day> aDays) {
        this.aDays = aDays;
    }

    public void setaAss(ArrayList<Auftrag> aAss) {
        this.aAss = aAss;
    }

    public transient GUI gui;
    private transient JFileChooser f;


    public Main() {

        inst = this;
        aSubjects = new ArrayList<MainSubject>();
        aDays = new ArrayList<Day>();
        aAss = new ArrayList<Auftrag>();
        f=new JFileChooser();
        BufferedReader br = null;
        first = true;


        Thread t = new Thread(() -> {
            while(true){
                if(Main.inst.gui !=null)
                if(Main.inst.gui.panel1!=null)
                Main.inst.gui.panel1.repaint();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public void init()
    {
        for (Day d : getaDays()) {
            d.init();
        }
        for(Auftrag a:aAss){
            a.init();
        }
        for(MainSubject s :aSubjects){
            s.init();
        }
    }
    public int indexOfDay(String s){
            for(int i = 0;i< aDays.size();i++){
                if(aDays.get(i).getDay().equals(s))
                    return i;
            }
            return -1;
    }
    public void addAuftrag(Auftrag a){
        aAss.add(a);
        a.getSubject().addAuftrag();
    }
    public ArrayList<MainSubject> getaSubjects() {
        return aSubjects;
    }

    public ArrayList<Day> getaDays() {
        return aDays;
    }

    public ArrayList<Auftrag> getaAss() {
        return aAss;
    }
    public MainSubject getMainSubject(String s){
        for(MainSubject m :aSubjects){
            if(m.getName().equals(s))
                return m;
        }
        return null;
    }
    public void addMainSubject(MainSubject s){
        aSubjects.add(s);
    }
    public void addSubject(Subject s){
        for(MainSubject m:aSubjects){
            if(m.equals(s.getMainSub()))
                m.addSubject(s);
        }
    }

    public JFileChooser getFileChooser() {
        return f;
    }

    public void addDay(Day s){
        aDays.add(s);
    }

    public int getLessonsDone() {
        return lessonsDone;
    }

    public void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }

    public int getMaxPerSubject() {
        return maxPerSubject;
    }

    public void setMaxPerSubject(int maxPerSubject) {
        this.maxPerSubject = maxPerSubject;
    }

    public int getMaxIngs() {
        return maxIngs;
    }

    public void setMaxIngs(int maxIngs) {
        this.maxIngs = maxIngs;
    }
}
