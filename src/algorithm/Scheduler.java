package algorithm;

import java.util.ArrayList;

public class Scheduler {

    private static Classroom[] classrooms;
    private static RoomList roomList;
    public static void main(String[] args) {
        // build classroom array
        Classroom cohort1 = new Classroom("cohort", "2.501", 50, ClassType.CBL, 0);
        Classroom cohort2 = new Classroom("cohort", "2.501", 50, ClassType.CBL, 1);
        Classroom cohort3 = new Classroom("cohort", "2.501", 50, ClassType.CBL, 2);
        Classroom lecture = new Classroom("Lec", "2.501", 150, ClassType.LEC, 1);
        Classroom lab = new Classroom("lab", "2.501", 50, ClassType.LAB, 2);

        classrooms = new Classroom[4];
        classrooms[0] = cohort1;
        classrooms[1] = cohort2;
        classrooms[2] = lecture;
        classrooms[3] = lab;

        roomList = new RoomList(classrooms, 2, 3);

        // init subject
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

        Subject cse = new Subject("cse", 50005, SubjectType.CORE, null, 50, 3, CSEarr);
        Subject esc = new Subject("esc", 50005, SubjectType.CORE, null, 50, 3, ESCarr);
        Subject prob = new Subject("prob", 50005, SubjectType.CORE, null, 50, 3, probArr);

        SpecificClass[][][] ss = init(new Subject[]{/*cse, esc,*/ prob}, 3, 3);
//        System.out.println(ss[0][8].getDuration());
        randomGen(ss);

    }

    //TODO: a function that input is list of subject and output is 3-d mat of SpecificClass (x:subject; y:cohort; z:session)
    public static SpecificClass[][][] init(Subject[] subjects, int MaxSessionNum, int MaxCohortNum) {
        GenericClass[] gClassSet;
        SpecificClass sClass;
        int cohortNum;
        int sessionNum;
        int subjectNum = subjects.length;
        int maxSessionNum = MaxSessionNum;
        int maxCohortNum = MaxCohortNum;

        SpecificClass[][][] result = new SpecificClass[subjectNum][maxCohortNum][maxSessionNum];
        int sClassNum = 0;
        //order: coh1sess1, coh2sess1, coh3sess1, ...
        for (int i = 0; i < subjectNum; i++) {
            gClassSet = subjects[i].getClassComponent();
            cohortNum = subjects[i].getNumOfCohort();
            sessionNum = gClassSet.length;
            for (int j = 0; j < cohortNum; j++) {
                for (int k = 0; k < sessionNum; k++) {
                    if (gClassSet[k].getClassType() == ClassType.LEC) {
                        if (j == 0) {
                            sClass = new SpecificClass(gClassSet[k], k, j, subjects[i], null);
                            result[i][j][k] = sClass;
                            sClassNum++;
                        }
                    }else {
                        sClass = new SpecificClass(gClassSet[k], k, j, subjects[i], null);
                        result[i][j][k] = sClass;
                        sClassNum++;
                    }
                }
            }
//            for (int j = 0; j < maxSessionNum; j++) {
//                for (int k = 0; k < cohortNo; k++) {
//                    sClass = new SpecificClass(gClassSet[j], j, k, subjects[i], roomList[k]);
//                    result[i][j*maxSessionNum+k] = sClass;
//                }
//            }
        }
        System.out.println("total sClass number is: " + sClassNum);
        return result;
    }

    //TODO: function that input is sClass mat and output is randomly generated calendar
    public static Calendar randomGen(SpecificClass[][][] sClassSet) {
        Calendar calendar = new Calendar(roomList, sClassSet);
        calendar.randomInit();
        calendar.printOut();
        return calendar;
    }

    //TODO: function that input is calendar and print it out
}


