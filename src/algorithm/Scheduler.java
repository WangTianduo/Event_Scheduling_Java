package algorithm;

import java.util.ArrayList;
import java.util.Random;

public class Scheduler {

    private static Classroom[] classrooms;
    private static RoomList roomList;
    public static ArrayList<Subject> subjects;
    private static ArrayList<StudentGroup> studentGroupSet;

    public static void main(String[] args) {

        importDatabase();

        Chromosome c1 = new Chromosome(3, 3, 3);
        Chromosome c2 = new Chromosome(3, 3, 3);
        
        rate(c1);
        rate(c2);
    }

    private static void importDatabase() {

        //---------------------------------------\\
        // import classrooms info
        Classroom cohort1 = new Classroom("cohort1", "2.501", 50, ClassType.CBL, 0);
        Classroom cohort2 = new Classroom("cohort2", "2.501", 50, ClassType.CBL, 1);
        Classroom cohort3 = new Classroom("cohort3", "2.501", 50, ClassType.CBL, 2);
        Classroom lecture = new Classroom("Lec", "2.501", 150, ClassType.LEC, 1);
        Classroom lab = new Classroom("lab", "2.501", 50, ClassType.LAB, 2);
        classrooms = new Classroom[4];
        classrooms[0] = cohort1;
        classrooms[1] = cohort2;
        classrooms[2] = lecture;
        classrooms[3] = lab;
        roomList = new RoomList(classrooms, 2, 3);

        //---------------------------------------\\
        // import subjects
        GenericClass coh1 = new GenericClass(ClassType.CBL, 1.5, null);
        GenericClass coh2 = new GenericClass(ClassType.CBL, 2, null);

        ArrayList<Integer> cohorts = new ArrayList<>();
        cohorts.add(0);
        cohorts.add(1);
        cohorts.add(2);

        GenericClass lec1 = new GenericClass(ClassType.LEC, 2, null, cohorts);
        GenericClass lec2 = new GenericClass(ClassType.LEC, 1.5, null, cohorts);

        GenericClass[] ESCarr = new GenericClass[3];
        ESCarr[0] = coh1;
        ESCarr[1] = coh1;
        ESCarr[2] = coh2;

        GenericClass[] CSEarr = new GenericClass[3];
        CSEarr[0] = coh1;
        CSEarr[1] = coh1;
        CSEarr[2] = lec1;

        GenericClass[] probArr = new GenericClass[3];
        probArr[0] = lec2;
        probArr[1] = lec2;
        probArr[2] = coh2;

        Subject cse = new Subject("cse", 50005, SubjectType.CORE,
                null, 50, 3, CSEarr);
        Subject esc = new Subject("esc", 50003, SubjectType.CORE,
                null, 50, 3, ESCarr);
        Subject prob = new Subject("prob", 50034, SubjectType.CORE,
                null, 50, 3, probArr);

        subjects = new ArrayList<>();
        subjects.add(cse);
        subjects.add(esc);
        subjects.add(prob);
        //---------------------------------------\\
        // import studentG
        studentGroupSet = new ArrayList<>();
        StudentGroup t5c1 = new StudentGroup(0, "t5c1", Pillar.ISTD, 5,
                0, subjects, 50);
        StudentGroup t5c2 = new StudentGroup(0, "t5c2", Pillar.ISTD, 5,
                1, subjects, 50);
        StudentGroup t5c3 = new StudentGroup(0, "t5c3", Pillar.ISTD, 5,
                2, subjects, 50);

        studentGroupSet.add(t5c1);
        studentGroupSet.add(t5c2);
        studentGroupSet.add(t5c3);

    }

    //TODO: a function that input is list of subject and output is 3-d mat of SpecificClass (x:subject; y:cohort; z:session)
    public static SpecificClass[][][] init(int MaxSessionNum, int MaxCohortNum) {
        GenericClass[] gClassSet;
        SpecificClass sClass;
        int cohortNum;
        int sessionNum;
        int subjectNum = subjects.size();
        int maxSessionNum = MaxSessionNum;
        int maxCohortNum = MaxCohortNum;

        SpecificClass[][][] result = new SpecificClass[subjectNum][maxCohortNum][maxSessionNum];
        //order: coh1sess1, coh2sess1, coh3sess1, ...
        for (int i = 0; i < subjectNum; i++) {
            gClassSet = subjects.get(i).getClassComponent();
            cohortNum = subjects.get(i).getNumOfCohort();
            sessionNum = gClassSet.length;
            for (int j = 0; j < cohortNum; j++) {
                for (int k = 0; k < sessionNum; k++) {
                    if (gClassSet[k].getClassType() == ClassType.LEC) {
                        if (j == 0) {
                            sClass = new SpecificClass(gClassSet[k], k, j, subjects.get(i), null);
                            result[i][j][k] = sClass;
                        }
                    }else {
                        sClass = new SpecificClass(gClassSet[k], k, j, subjects.get(i), null);
                        result[i][j][k] = sClass;
                    }
                }
            }
        }
        return result;
    }

    //TODO: function that input is sClass mat and output is randomly generated calendar
    public static Calendar randomGen(SpecificClass[][][] sClassSet) {
        Calendar calendar;
        while (true){
            calendar = new Calendar(roomList, sClassSet);
            boolean s = calendar.randomInit();
            if (s == true) {
                break;
            }
        }
//        calendar.printOut();
        return calendar;
    }

    //TODO: function that input is calendar and print it out
    public static void printChromosome(Chromosome c) {
        SpecificClass[][][] chromosome = c.getChromosome();
        for (int i = 0; i < chromosome.length; i++) {
            for (int j = 0; j < chromosome[0].length; j++) {
                for (int k = 0; k < chromosome[0][0].length; k++) {
                    if (chromosome[i][j][k] != null) {
                        if (chromosome[i][j][k].getClassroom() == null) {
                            chromosome[i][j][k].printInfoWithoutRoom();
                        }else {
                            chromosome[i][j][k].printInfo();
                        }
                    }
                }
            }
        }
    }

    //TODO：rating automatically
    public static void rate(Chromosome chromosome) { // need StudentGroupSet and ProfSet
        int conflictNum = 0;

        for (StudentGroup sg: studentGroupSet) {
            sg.setsClassSet(chromosome.getChromosome());
            conflictNum = sg.checkConflict();
        }
        System.out.println("The studentG conflict number is: " + conflictNum);
        chromosome.setScore(conflictNum);

        int sessionError = 0;
        SpecificClass[][][] temp = chromosome.getChromosome();
        for (int i = 0; i < chromosome.getXdim(); i++) {
            for (int j = 0; j < chromosome.getYdim(); j++) {
                for (int k = 0; k < chromosome.getZdim()-1; k++) {
                    if (temp[i][j][k] == null || temp[i][j][k+1] == null) {
//                        continue; // not complete
                        if (temp[i][j][k] == null && temp[i][j][k+1] == null) {// Lec + Lec + Coh
                            if (temp[i][0][1].getWeekday() >= temp[i][j][2].getWeekday()) {
                                sessionError++;
                            }
                            break;
                        }else if (temp[i][j][k] == null && temp[i][j][k+1] != null) { // Lec + Coh
                            if (temp[i][0][k].getWeekday() >= temp[i][j][k+1].getWeekday()) {
                                sessionError++;
                            }
                            break;
                        }else if (temp[i][j][k] != null && temp[i][j][k+1] == null) { // Coh + Lec
                            if (temp[i][j][k].getWeekday() >= temp[i][0][k+1].getWeekday()) {
                                sessionError++;
                            }
                            break;
                        }
                    }else {
                        if (temp[i][j][k].getWeekday() >= temp[i][j][k+1].getWeekday()) {
                            sessionError ++;
                        }
                    }
                }
            }
        }
        System.out.println("The session error is: " + sessionError);
    }
}

class Chromosome {
    private SpecificClass[][][] chromosome;
    private int subjectNum;
    private int cohortNum;
    private int sessionNum;
    private double score;

    Chromosome(int x, int y, int z) {
        this.chromosome = Scheduler.init(y, z); // y: cohort number; z: session number
        Scheduler.randomGen(this.chromosome);
        this.subjectNum = x;
        this.cohortNum = y;
        this.sessionNum = z;
        this.score = 0;
    }

    Chromosome(SpecificClass[][][] sClassSet, int x, int y, int z) {
        this.chromosome = sClassSet;
        this.subjectNum = x;
        this.cohortNum = y;
        this.sessionNum = z;
        this.score = 0;
    }
    public void setScore(double score) {
        this.score = score;
    }

    public SpecificClass[][][] getChromosome() {
        return chromosome;
    }

    public int getXdim(){
        return subjectNum;
    }

    public int getYdim() {
        return cohortNum;
    }

    public int getZdim() {
        return sessionNum;
    }

    public static Chromosome merge(Chromosome c1, Chromosome c2) {
        SpecificClass[][][] newChromosome = new SpecificClass[c1.getXdim()][c1.getYdim()][c1.getZdim()];
        SpecificClass[][][] c1set = c1.getChromosome();
        SpecificClass[][][] c2set = c2.getChromosome();
        Random r = new Random();
        for (int i = 0; i < c1.getXdim(); i++) {
            for (int j = 0; j < c1.getYdim(); j++) {
                for (int k = 0; k < c1.getZdim(); k++) {
                    if (r.nextDouble() > 0.5) {
                        newChromosome[i][j][k] = c1set[i][j][k];
                    }else {
                        newChromosome[i][j][k] = c2set[i][j][k];
                    }
                }
            }
        }
        return new Chromosome(newChromosome, c1.getXdim(), c1.getYdim(), c1.getZdim());
    }
}




