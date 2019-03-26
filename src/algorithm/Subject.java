package algorithm;

public class Subject {
    protected String name;
    protected int id;
    protected SubjectType type;
    protected Professor[] courseLead;
    protected int studNumPerCohort; // 50 students each class
    protected int numOfCohort;
    protected int totalEnrollNum;
    protected GenericClass[] classComponent;

    Subject(String name, int id, SubjectType type, Professor[] courseLead,
            int studNumPerCohort, int numOfCohort, GenericClass[] classComponent) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.courseLead = courseLead;
        this.studNumPerCohort = studNumPerCohort;
        this.numOfCohort = numOfCohort;
        this.totalEnrollNum = this.numOfCohort * this.studNumPerCohort;
        this.classComponent = classComponent;
    }

    public GenericClass[] getClassComponent() {
        return classComponent;
    }
}

enum SubjectType {
    CORE, ELECTIVE
}


enum ClassType {
    LEC, CBL, LAB
}

class GenericClass {
    protected double duration;
    protected ClassType classType;
    protected Classroom classroom = null;

    GenericClass(ClassType classType, double duration, Classroom classroom) {
        this.classType = classType;
        this.duration = duration;
        this.classroom = classroom;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public ClassType getClassType() {
        return classType;
    }

    public double getDuration() {
        return duration;
    }
}

class SpecificClass {
    private ClassType type;
    private double duration;
    private Classroom classroom;
    private int session;

    SpecificClass(GenericClass gclass, int session) {
        this.type = gclass.getClassType();
        this.duration = gclass.getDuration();
        this.classroom = gclass.getClassroom();
        this.session = session;
    }

    public int getSession() {
        return session;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public ClassType getType() {
        return type;
    }

    public double getDuration() {
        return duration;
    }
}
