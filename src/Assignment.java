import com.google.gson.annotations.Expose;

import java.util.Date;

class Assignment {

    /**
     * The {@link Date} until which the assignment has to be done
     */
    @Expose
    private final Date deadline;

    /**
     * Lessons which are available for this assignment
     */
    @Expose
    private final int lessons;

    /**
     * Lessons which are already done
     */
    @Expose
    private int lessonsDone;

    /**
     * Description of this assignment
     */
    @Expose
    private final String description;

    /**
     * The {@link String} representing the {@link MainSubject} of this assignment for saving purposes
     */
    @Expose
    private final String mSubjectSt;

    /**
     * The {@link MainSubject} of this assignment
     */
    private MainSubject mSubject;

    /**
     * The {@link AssignmentGUI} for this assignment
     */
    private AssignmentGUI assGUI;

    /**
     * Constructor
     *
     * @param deadline    The deadline for this assignment
     * @param lessons     The number of lessons available for this assignment
     * @param description The description of this assignment
     * @param mainSubject The MainSubject of this assignment
     */
    Assignment(Date deadline, int lessons, String description, MainSubject mainSubject) {
        this.deadline = deadline;
        this.lessons = lessons;
        lessonsDone = 0;
        this.description = description;
        mSubject = mainSubject;
        mSubjectSt = mainSubject.getName();
        init();
    }

    /**
     * Initialization of this assignment
     */
    void init() {
        mSubject = Main.inst.getMainSubject(mSubjectSt);
        mSubject.addAssignment(this);
        assGUI = new AssignmentGUI(this, mSubject);
        Main.inst.getAassGUIs().add(assGUI);
        Main.inst.getGui().getAuftragPanel().add(assGUI.content);
        Main.inst.getGui().getAuftragPanel().revalidate();
        Main.inst.getGui().getAuftragPanel().repaint();
    }

    int getLessonsDone() {
        return lessonsDone;
    }

    void setLessonsDone(int lessonsDone) {
        this.lessonsDone = lessonsDone;
    }

    /**
     * Calculates the number of daays remaining for the assignment
     *
     * @return A long representing the number of days remaining
     */
    long getRemaining() {
        return (deadline.getTime() - new Date(System.currentTimeMillis()).getTime()) / (24 * 60 * 60 * 1000);
    }

    MainSubject getmSubject() {
        return mSubject;
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

    AssignmentGUI getAssGUI() {
        return assGUI;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Assignment)) return false;
        Assignment assignment = (Assignment) obj;
        if (lessons != assignment.lessons) return false;
        if (lessonsDone != assignment.lessonsDone) return false;
        if (!deadline.equals(assignment.deadline)) return false;
        if (!description.equals(assignment.description)) return false;
        if (!mSubjectSt.equals(assignment.mSubjectSt)) return false;
        return mSubject.equals(assignment.mSubject);
    }

    int getLessons() {
        return lessons;
    }
}