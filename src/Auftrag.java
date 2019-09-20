import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.Objects;

public class Auftrag {
    /**
     * The Date until which the Assignment has to be done
     */
    @Expose
    private Date deadline;
    /**
     * Lessons which are avaiable for this assignment
     */
    @Expose
    private int lessons;
    /**
     * Lessons which are already done
     */
    @Expose
    private int lessonsDone;
    /**
     * Description of this Assignment
     */
    @Expose
    private String description;
    /**
     * The String representation of the Main Subject. Saving purposes.
     */
    @Expose
    private String subjects;
    /**
     * The Main Subject.
     * @see MainSubject
     */
    private MainSubject subject;
    /**
     * The GUI for this Assignment
     * @see AuftragGUI
     */
    private AuftragGUI augui;

    /**
     * @param dead    Deadline for this Assignment
     * @param lessons Number of lessons avaiable
     * @param des     short Description of this assignment
     * @param sub     Subject of this Assignment
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
     * lesson of this Assignment is done
     */
    public void lessonDone() {
        lessonsDone++;
    }

    /**
     * A number of lessons for this Assignment is done
     *
     * @param i the number of lessons
     */
    public void lessonsDone(int i) {
        lessonsDone += i;
    }

    /**
     * Initsialation of this Assignment
     */
    public void init() {
        subject = Main.inst.getMainSubject(subjects);
        subject.addAuftrag(this);
        augui = new AuftragGUI(this, subject);
        Main.inst.getaGuis().add(augui);

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

    /**
     * Calculates the number of das which are remaining till the deadline of this Assignment.
     *
     * @return A long representation of the number of Days remaining
     */
    public long getRemaining() {
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

    /**
     * Calculates the Attention level
     *
     * @return short representation of the Attention level
     */
    public short needAttention() {
        if (getRemaining() < 3) return 1;
        else if (getRemaining() < 7) return 2;
        else return 3;
    }

    public AuftragGUI getAugui() {
        return augui;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auftrag auftrag = (Auftrag) o;
        if (getLessons() != auftrag.getLessons()) return false;
        if (getLessonsDone() != auftrag.getLessonsDone()) return false;
        if (!deadline.equals(auftrag.deadline)) return false;
        if (!getDescription().equals(auftrag.getDescription())) return false;
        if (!subjects.equals(auftrag.subjects)) return false;
        return getSubject().equals(auftrag.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(deadline, lessons, lessonsDone, description, subjects, subject, augui);
    }


    public int getLessons() {
        return lessons;
    }
}
