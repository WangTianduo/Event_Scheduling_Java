package algorithm;

enum Pillar {
    Freshmore, ASD, EPD, ESD, ISTD
}

public class StudentGroup {
    private int id;
    private String name;
    private Pillar pillar;
    private int term;
    private int cohort = 0; // if this is 0, means do not belong to any cohort
    private Subject[] subjects;
    private int size;

    StudentGroup(int id, String name, Pillar pillar, int term,
                 int cohort, Subject[] subjects, int size) {
        this.id = id;
        this.name = name;
        this.pillar = pillar;
        this.term = term;
        this.cohort = cohort;
        this.subjects = subjects;
        this.size = size;
    }
}
