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
                    if (input3D[i][j][0].getCohortNo().contains(this.cohort)) {
                        sessionNum = input3D[i][j].length;
                        for (int k = 0; k < sessionNum; k++) {
                            sClassSet.add(input3D[i][j][k]);
                        }
                    }else {
                        continue;
                    }
                }
            }else {
                continue;
            }
        }
    }

}
