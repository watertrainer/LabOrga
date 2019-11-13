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
     * @param dead    Short for deadline for this assignment
     * @param lessons Number of lessons available
     * @param des     Short for the description of this assignment
     * @param sub     Short for the subject of this assignment
     */
    Auftrag(Date dead, int lessons, String des, MainSubject sub) {
        this.deadline = dead;
        this.lessons = lessons;
        setLessonsDone(0);
        this.description = des;
        this.subject = sub;
        this.subjects = sub.getName();
        init();
    }

    /**
     * Initialization of this assignment
     */
    void init() {
        this.subject = Main.inst.getMainSubject(this.subjects);
        getSubject().addAuftrag(this);
        augui = new AuftragGUI(this, getSubject());
        Main.inst.getaGuis().add(augui);
        Main.inst.gui.getAuftragPanel().add(augui.content);
        Main.inst.gui.getAuftragPanel().revalidate();
        Main.inst.gui.getAuftragPanel().repaint();
    }

    int getLessonsDone() {
        return this.lessonsDone;
    }

    void setLessonsDone(int lessons) {
        this.lessonsDone = lessons;
    }

    /**
     * Calculates the number of days remaining for this assignment.
     *
     * @return A long representation of the number of days remaining
     */
    long getRemaining() {
        return (this.deadline.getTime() - new Date(System.currentTimeMillis()).getTime()) / (24 * 60 * 60 * 1000);
    }

    MainSubject getSubject() {
        return this.subject;
    }

    String getDescription() {
        return this.description;
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
        return this.augui;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auftrag auftrag = (Auftrag) o;
        if (getLessons() != auftrag.getLessons()) return false;
        if (getLessonsDone() != auftrag.getLessonsDone()) return false;
        if (!this.deadline.equals(auftrag.deadline)) return false;
        if (!getDescription().equals(auftrag.getDescription())) return false;
        if (!this.subjects.equals(auftrag.subjects)) return false;
        return getSubject().equals(auftrag.getSubject());
    }

    int getLessons() {
        return this.lessons;
    }
}