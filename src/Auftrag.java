import com.google.gson.annotations.Expose;

import java.util.Date;

class Auftrag {

    /**
     * The date until which the assignment has to be done
     */
    @Expose
    private Date deadline;

    /**
     * Lessons which are available for this assignment
     */
    @Expose
    private int lessons;

    /**
     * Lessons which are already done
     */
    @Expose
    private int lessonsDone;

    /**
     * Description of this assignment
     */
    @Expose
    private String description;

    /**
     * The string representation of the Main Subject for saving purposes
     */
    @Expose
    private String subjects;

    /**
     * The Main Subject of this assignment
     *
     * @see MainSubject
     */
    private MainSubject subject;

    /**
     * The GUI for this assignment
     *
     * @see AuftragGUI
     */
    private AuftragGUI augui;

    /**
     * @param dead    Deadline for this assignment
     * @param lessons Number of lessons available for this assignment
     * @param des     Description of this assignment
     * @param sub     Main Subject of this assignment
     */
    Auftrag(Date dead, int lessons, String des, MainSubject sub) {
        deadline = dead;
        this.lessons = lessons;
        lessonsDone = 0;
        description = des;
        subject = sub;
        subjects = sub.getName();
        init();
    }

    /**
     * Initialization of this assignment
     */
    void init() {
        subject = Main.inst.getMainSubject(subjects);
        subject.addAuftrag(this);
        augui = new AuftragGUI(this, subject);
        Main.inst.getaGuis().add(augui);
        Main.inst.gui.getAuftragPanel().add(augui.content);
        Main.inst.gui.getAuftragPanel().revalidate();
        Main.inst.gui.getAuftragPanel().repaint();
    }

    int getLessonsDone() {
        return lessonsDone;
    }

    void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }

    /**
     * Calculates the number of days remaining for this assignment
     *
     * @return A long time representation of the number of days remaining
     */
    long getRemaining() {
        return (deadline.getTime() - new Date(System.currentTimeMillis()).getTime()) / (24 * 60 * 60 * 1000);
    }

    MainSubject getSubject() {
        return subject;
    }

    String getDescription() {
        return description;
    }

    /**
     * Calculates the attention level
     *
     * @return Short representation of the attention level
     */
    short needAttention() {
        if (getRemaining() < 3) return 1;
        else if (getRemaining() < 7) return 2;
        else return 3;
    }

    AuftragGUI getAugui() {
        return augui;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Auftrag auftrag = (Auftrag) obj;
        if (lessons != auftrag.lessons) return false;
        if (lessonsDone != auftrag.lessonsDone) return false;
        if (!deadline.equals(auftrag.deadline)) return false;
        if (!description.equals(auftrag.description)) return false;
        if (!subjects.equals(auftrag.subjects)) return false;
        return subject.equals(auftrag.subject);
    }

    int getLessons() {
        return lessons;
    }
}