package algorithm;

import java.util.ArrayList;

enum Pillar {
    Freshmore, ASD, EPD, ESD, ISTD
}

public class StudentGroup {
    private int id;
    private String name;
    private Pillar pillar;
    private int term;
    private int cohort = 0; // if this is 0, means do not belong to any cohort
    private ArrayList<Subject> subjects;
    private int size;
    private ArrayList<SpecificClass> sClassSet;

    StudentGroup(){

    }
    StudentGroup(int id, String name, Pillar pillar, int term,
                 int cohort, ArrayList<Subject> subjects, int size) {
        this.id = id;
        this.name = name;
        this.pillar = pillar;
        this.term = term;
        this.cohort = cohort;
        this.subjects = subjects;
        this.size = size;
        this.sClassSet = new ArrayList<>();
    }

    public void setsClassSet(SpecificClass[][][] input3D) {
        int subjectNum = input3D.length;
        int cohortNum;
        int sessionNum;
        for (int i = 0; i < subjectNum; i++) {
            if (this.subjects.contains(input3D[i][0][0].getSubject())) {
                cohortNum = input3D[i].length;
                for (int j = 0; j < cohortNum; j++) {
                    sessionNum = input3D[i][j].length;
                    if (input3D[i][j][0] == null) {
                        if (input3D[i][j][sessionNum-1].getCohortNo().contains(this.cohort)) {// because of lecture, some slots may be empty
                            for (int k = 0; k < sessionNum; k++) {
                                sClassSet.add(input3D[i][j][k]);
                            }
                        }else {
                            continue;
                        }
                    }else {
                        if (input3D[i][j][0].getCohortNo().contains(this.cohort)) {
                            for (int k = 0; k < sessionNum; k++) {
                                sClassSet.add(input3D[i][j][k]);
                            }
                        }else {
                            continue;
                        }
                    }
                }
            }else {
                continue;
            }
        }
    }

    // pre-condition: assume after scheduling
    // If detects one conflict, result++
    public int checkConflict() {
        int[][] timeTable = new int[5][20];
        int startTime;
        int timeSlotNum;
        int weekday;
        int result = 0;
        for (SpecificClass c: sClassSet) {
            weekday = c.getWeekday();
            startTime = c.getStartTime();
            timeSlotNum = (int)(c.getDuration()/0.5);

            for (int sln = 0; sln < timeSlotNum; sln++) {
                if (weekday != -1 && timeTable[weekday][startTime + sln] == 0) { //empty in slot
                    timeTable[weekday][startTime + sln] = 1;
                }else {
                    if (c.getClassroom() == null) {
                        c.printInfoWithoutRoom();
                    }else if (c == null) {
                        System.out.println("No sClass");
                    }else {
                        c.printInfo();
                    }
                    result++;
                    break;
                }
            }
        }
        return result;
    }
    public ArrayList<SpecificClass> getsClassSet() {
        return sClassSet;
    }
}
