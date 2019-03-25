package algorithm;

public class Scheduler {
    public static void main(String[] args) {
        // build classroom array
        Classroom cohort = new Classroom("cohort", "2.501", 50, ClassType.CBL);
        Classroom lecture = new Classroom("Lec", "2.501", 150, ClassType.LEC);
        Classroom lab = new Classroom("lab", "2.501", 50, ClassType.LAB);

        Classroom[] roomList = new Classroom[3];
        roomList[0] = cohort;
        roomList[1] = lecture;
        roomList[2] = lab;

        // build class 3-d mat
        SpecificClass[][][] calendar = new SpecificClass[3][5][20]; //room, weekday, time slot
        System.out.println(calendar[0][0][0]==null);

        // init subject
        GenericClass coh1 = new GenericClass(ClassType.CBL, 2, lecture);
        GenericClass coh2 = new GenericClass(ClassType.CBL, 2, lecture);
        GenericClass lec = new GenericClass(ClassType.LEC, 2, lecture);
        GenericClass[] arrange = new GenericClass[3];
        arrange[0] = coh1;
        arrange[1] = coh2;
        arrange[2] = lec;
        Subject cse = new Subject("cse", 50005, SubjectType.CORE, null, 50, 3, arrange);

    }

    //TODO: a function that input is list of subject and output is 2-d mat of SpecificClass (x:subject; y:session)

    //TODO: function that input is sClass mat and output is randomly generated caldendar

    //TODO: function that input is calendar and print it out
}


