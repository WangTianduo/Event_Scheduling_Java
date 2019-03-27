package algorithm;

public class Scheduler {

    private static Classroom[] roomList;
    public static void main(String[] args) {
        // build classroom array
        Classroom cohort1 = new Classroom("cohort", "2.501", 50, ClassType.CBL, 0);
        Classroom cohort2 = new Classroom("cohort", "2.501", 50, ClassType.CBL, 1);
        Classroom cohort3 = new Classroom("cohort", "2.501", 50, ClassType.CBL, 2);
        Classroom lecture = new Classroom("Lec", "2.501", 150, ClassType.LEC, 1);
        Classroom lab = new Classroom("lab", "2.501", 50, ClassType.LAB, 2);

        roomList = new Classroom[3];
        roomList[0] = cohort1;
        roomList[1] = cohort2;
        roomList[2] = cohort3;

        // build class 3-d mat
        SpecificClass[][][] calendar = new SpecificClass[3][5][20]; //room, weekday, time slot

        // init subject
        GenericClass coh1 = new GenericClass(ClassType.CBL, 1.5, null);
        GenericClass coh2 = new GenericClass(ClassType.CBL, 1.5, null);
        GenericClass lec = new GenericClass(ClassType.LEC, 2, null);
        GenericClass[] arrange = new GenericClass[3];
        arrange[0] = coh1;
        arrange[1] = coh2;
        arrange[2] = lec;
        Subject cse = new Subject("cse", 50005, SubjectType.CORE, null, 50, 3, arrange);
        Subject esc = new Subject("esc", 50005, SubjectType.CORE, null, 50, 3, arrange);
        Subject prob = new Subject("prob", 50005, SubjectType.CORE, null, 50, 3, arrange);

        SpecificClass[][] ss = init(new Subject[]{cse, esc, prob}, 3, 3);
//        System.out.println(ss[0][8].getDuration());
        randomGen(ss);

    }

    //TODO: a function that input is list of subject and output is 2-d mat of SpecificClass (x:subject; y:session)
    public static SpecificClass[][] init(Subject[] subjects, int sessionNum, int cohortNum) {
        GenericClass[] gClassSet;
        SpecificClass sClass;
        int cohortNo;
        int subjectNum = subjects.length;
        int maxSessionNum = sessionNum;
        int maxCohortNum = cohortNum;

        SpecificClass[][] result = new SpecificClass[subjectNum][maxSessionNum*maxCohortNum];
        //order: coh1sess1, coh2sess1, coh3sess1, ...
        for (int i = 0; i < subjectNum; i++) {
            gClassSet = subjects[i].getClassComponent();
            cohortNo = subjects[i].getNumOfCohort();
            for (int j = 0; j < maxSessionNum; j++) {
                for (int k = 0; k < cohortNo; k++) {
                    sClass = new SpecificClass(gClassSet[j], j, k, subjects[i], roomList[k]);
                    result[i][j*maxSessionNum+k] = sClass;
                }
            }
        }

        return result;
    }

    //TODO: function that input is sClass mat and output is randomly generated calendar
    public static Calendar randomGen(SpecificClass[][] sClassSet) {
        Calendar calendar = new Calendar(roomList, sClassSet);
        calendar.randomInit();
        calendar.printOut();
        return calendar;
    }

    //TODO: function that input is calendar and print it out
}


