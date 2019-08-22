import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import jdk.nashorn.internal.parser.JSONParser;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class Main
{
    public transient static Main inst;
    @Expose
    private HashMap<String,MainSubject> aSubjects;
    @Expose
    private HashMap<String,Day> aDays; //todo convretToHashMap
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

    public void setaSubjects(HashMap<String,MainSubject> aSubjects) {
        this.aSubjects = aSubjects;
    }

    public void setaDays(HashMap<String,Day> aDays) {
        this.aDays = aDays;
    }

    public void setaAss(ArrayList<Auftrag> aAss) {
        this.aAss = aAss;
    }

    public transient GUI gui;
    private transient JFileChooser f;


    public Main() {

        inst = this;
        aSubjects = new HashMap<String, MainSubject>();
        aDays = new HashMap<String, Day>();
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
        for (MainSubject s : aSubjects.values()) {

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
    }
    public Collection<MainSubject> getaSubjects() {
        return aSubjects.values();
    }

    public Collection<Day> getaDays() {
        return aDays.values();
    }

    public ArrayList<Auftrag> getaAss() {
        return aAss;
    }
    public MainSubject getMainSubject(String s){
        return aSubjects.get(s);
    }
    public Day getDay(String s){
        return aDays.get(s);
    }
    public void addMainSubject(MainSubject s){
        aSubjects.put(s.getName(),s);
    }
    public void addDay(Day s){
        aDays.put(s.getDay(),s);
    }
    public void addSubject(Subject s){
        for(MainSubject m:aSubjects.values()){
            if(m.equals(s.getMainSub()))
                m.addSubject(s);
        }
    }
    public boolean removeMainSub(String s){
        MainSubject m = getMainSubject(s);
        if(m.removeMe()){
            aSubjects.remove(s);
            return true;
        }
        else{
            return false;
        }
    }
    public JFileChooser getFileChooser() {
        return f;
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
