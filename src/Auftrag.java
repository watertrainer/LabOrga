import com.google.gson.annotations.Expose;

import java.util.Date;

class Auftrag {

    /**
     * The {@link Date} until which the assignment has to be done
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
     * The {@link String} representing the {@link MainSubject} of this assignment for saving purposes
     */
    @Expose
    private String subjectS;

    /**
     * The {@link MainSubject} of this assignment
     */
    private MainSubject subject;

    /**
     * The {@link AuftragGUI} for this assignment
     */
    private AuftragGUI assGUI;

    /**
     * Constructor
     *
     * @param deadline    The deadline for this assignment
     * @param lessons     The number of lessons available for this assignment
     * @param description The description of this assignment
     * @param mainSubject The MainSubject of this assignment
     */
    Auftrag(Date deadline, int lessons, String description, MainSubject mainSubject) {
        this.deadline = deadline;
        this.lessons = lessons;
        lessonsDone = 0;
        this.description = description;
        subject = mainSubject;
        subjectS = mainSubject.getName();
        init();
    }

    /**
     * Initialization of this assignment
     */
    void init() {
        subject = Main.inst.getMainSubject(subjectS);
        subject.addAuftrag(this);
        assGUI = new AuftragGUI(this, subject);
        Main.inst.getaGUIs().add(assGUI);
        Main.inst.gui.getAuftragPanel().add(assGUI.content);
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
     * Calculates the number of days remaining for the assignment
     *
     * @return A long representing the number of days remaining
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
     * Calculates the attention level for the assignment
     *
     * @return A short representing the attention level
     */
    short needAttention() {
        if (getRemaining() < 3) return 1;
        else if (getRemaining() < 7) return 2;
        else return 3;
    }

    AuftragGUI getAssGUI() {
        return assGUI;
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
        if (!subjectS.equals(auftrag.subjectS)) return false;
        return subject.equals(auftrag.subject);
    }

    int getLessons() {
        return lessons;
    }
}