package algorithm;

import java.util.Random;

public class Calendar {
    SpecificClass[][][] timetable;
    SpecificClass[][] input2D;
    int roomNum;

    Calendar(Classroom[] roomList, SpecificClass[][] sClass) {
        this.roomNum = roomList.length;
        timetable = new SpecificClass[roomNum][5][20]; //  room, weekday, half-hour
        this.input2D = sClass;
    }

    public void randomInit() {
        // assume the instance has been created and classrooms are assigned already
        Random rand = new Random();
        int subjectNum = input2D.length;// length of sClass column
        int classNum = input2D[0].length; // get the length of sClass row
        SpecificClass tempClass;
        int maxSessionNum;
        int cohortNum;
        double classDuration;
        int roomID;
        int preWeekDay = 0; // previous class weekday
        int currentWeekday;
        int numOfSlot; // if duration == 1.5, numOfSlot = 3
        for (int i = 0; i < subjectNum; i++) {
            maxSessionNum = input2D[i][0].getSubject().getNumOfSession();
            cohortNum = input2D[i][0].getSubject().getNumOfCohort();
            for (int j = 0; j < cohortNum; j++) {
                preWeekDay = 0;
                for (int k = 0; k < maxSessionNum; k++) {
                    if (input2D[i][k*maxSessionNum + j] == null) {
                        break;
                    }else{
                        classDuration = input2D[i][k*maxSessionNum + j].getDuration();
                        roomID = input2D[i][k*maxSessionNum + j].getClassroom().getId();
                        numOfSlot = (int) (classDuration / 0.5);
                        currentWeekday = (int)Math.floor(rand.nextDouble()*2 + preWeekDay + 1);
                        if (currentWeekday >= 5) {
                            currentWeekday --;
                        }else {
                            preWeekDay = currentWeekday;
                        }
                        currentWeekday -- ;
                        // above random choose a weekday
                        // below random choose a begin time slot
                        int timeSlotPoiner = 0;
                        int putIn = 0;
                        while(timeSlotPoiner < 16) {
                            for (int sln = 0; sln < numOfSlot; sln++) {
                                if(timetable[roomID][currentWeekday][timeSlotPoiner+sln] != null) {
                                    break;
                                }else {
                                    putIn++;
                                }
                            }
                            if (putIn == numOfSlot) {
                                for (int sln = 0; sln < numOfSlot; sln++) {
                                    timetable[roomID][currentWeekday][timeSlotPoiner+sln] =
                                            input2D[i][k*maxSessionNum + j];
                                }
                                break;
                            }else {
                                timeSlotPoiner += numOfSlot - 1;
                            }
                        }
                        if (timeSlotPoiner == 16) {
                            System.out.println("Fail to find suitable slot");
                        }
                    }
                }
            }
        }
    }

    public void printOut() {
        for(int k = 0; k < roomNum; k++) {
            for(int i = 0; i < 5; i++) {
                for (int j = 0; j < 20; j++) {
                    if (timetable[k][i][j] == null) {
                        System.out.print(" ");
                    }else {
                        System.out.println("classroom:" + k + " "+" cohort:"+timetable[k][i][j].getCohortNo()+" "+
                                timetable[k][i][j].getSubject().getName() + " session:" +
                                timetable[k][i][j].getSession()
                                + " Weekday:" + i + " slot:" + j + " ");
                    }
                }
                System.out.println();
            }
        }
    }
}
