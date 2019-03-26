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

        // init subject
        GenericClass coh1 = new GenericClass(ClassType.CBL, 1.5, lecture);
        GenericClass coh2 = new GenericClass(ClassType.CBL, 1.5, lecture);
        GenericClass lec = new GenericClass(ClassType.LEC, 2, lecture);
        GenericClass[] arrange = new GenericClass[3];
        arrange[0] = coh1;
        arrange[1] = coh2;
        arrange[2] = lec;
        Subject cse = new Subject("cse", 50005, SubjectType.CORE, null, 50, 3, arrange);

        SpecificClass[][] ss = init(new Subject[]{cse}, 3);
        System.out.println(ss[0][0].getDuration());

    }

    //TODO: a function that input is list of subject and output is 2-d mat of SpecificClass (x:subject; y:session)
    public static SpecificClass[][] init(Subject[] subjects, int sessionNum) {
        GenericClass[] gClassSet;
        SpecificClass sClass;
        int subjectNum = subjects.length;
        int maxSessionNum = sessionNum;

        SpecificClass[][] result = new SpecificClass[subjectNum][maxSessionNum];

        for (int i = 0; i < subjectNum; i++) {
            gClassSet = subjects[i].getClassComponent();
            for (int j = 0; j < sessionNum; j++) {
                sClass = new SpecificClass(gClassSet[j], j);
                result[i][j] = sClass;
            }
        }

        return result;
    }

    //TODO: function that input is sClass mat and output is randomly generated calendar


    //TODO: function that input is calendar and print it out
}


