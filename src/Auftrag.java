import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.util.Date;

public class Auftrag {
    @Expose
    private Date deadline;
    @Expose
    private int lessons;

    public int getLessons() {
        return lessons;
    }

    @Expose
    private int lessonsDone;
    @Expose
    private String description;
    @Expose
    private String subjects;
    private MainSubject subject;
    private AuftragGUI augui;

    public Auftrag(Date dead,int lessons,String des,MainSubject sub){
        deadline = dead;
        this.lessons = lessons;
        lessonsDone = 0;
        description = des;
        subject = sub;
        subjects = sub.getName();
        init();

    }
    public void lessonDone(){
        lessonsDone++;
    }
    public void lessonsDone(int i){
        lessonsDone += lessonsDone;
    }

    public void init(){
        System.out.println("Hello1");
        subject = Main.inst.getMainSubject(subjects);
        subject.addAuftrag(this);
        augui = new AuftragGUI(this,subject);

        Main.inst.gui.getAuftragPanel().add(augui.content);
        Main.inst.gui.getAuftragPanel().revalidate();
        Main.inst.gui.getAuftragPanel().repaint();
    }

    public int getLessonsDone() {
        return lessonsDone;
    }

    public void setLessonsDone(int lessons) {
        this.lessonsDone = lessons;
    }

    public long getRemaining(){
        Date d = new Date(System.currentTimeMillis());
        long diff = deadline.getTime() - d.getTime();
        return diff / (24 * 60 * 60 * 1000);
    }

    public MainSubject getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }
    public boolean needAttention(){
        return  getRemaining() <5;
    }
    public AuftragGUI getAugui() {
        return augui;
    }
}
