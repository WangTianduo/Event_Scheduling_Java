package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class Calendar {
    private SpecificClass[][][] timetable;
    private SpecificClass[][][] input3D; // x: subject; y: cohort; z: session
    private int roomNum;
    private RoomList roomList;

    //roomList is a list with the order: cohort, lecture, lab
    int cohRoomStart = 0;
    int lecRoomStart;
    int labRoomStart;

    Calendar(RoomList roomList, SpecificClass[][][] sClass) {
        this.roomNum = roomList.getListNum();
        this.roomList = roomList;
        this.lecRoomStart = roomList.getLecRoomStart();
        this.labRoomStart = roomList.getLabRoomStart();
        timetable = new SpecificClass[roomNum][5][20]; //  room, weekday, half-hour
        this.input3D = sClass;
    }

    public void randomInit() {
        // assume the instance has been created and classrooms are assigned already
        Random rand = new Random();
        int subjectNum = input3D.length;// length of sClass column
        SpecificClass tempClass;
        int maxSessionNum;
        int sessionNum;
        int cohortNum;
        double classDuration;
        ClassType roomType;
        int roomID;
        int preWeekDay = -1; // previous session weekday
        int currentWeekday = 0;
        int numOfSlot; // if duration == 1.5, numOfSlot = 3
        for (int i = 0; i < subjectNum; i++) {
            sessionNum = input3D[i][0][0].getSubject().getClassComponent().length;
            cohortNum = input3D[i][0][0].getSubject().getNumOfCohort();
            for (int j = 0; j < cohortNum; j++) {
                preWeekDay = -1;
                for (int k = 0; k < sessionNum; k++) {
                    if (input3D[i][j][k] == null) {
                        preWeekDay = 2;
                        continue;
                    }else{
                        // ------------------------------------------------ \\
                        // Randomly choose a weekday
                        // Policy: Assume that possible session number is {2, 3}
                        currentWeekday = (int) Math.ceil(rand.nextDouble()*(5-sessionNum) + preWeekDay);
                        if (currentWeekday >= 5) {
                            currentWeekday --;
                        }else {
                            preWeekDay = currentWeekday;
                        }
                        // ------------------------------------------------ \\
                        // Randomly choose a classroom
                        // Policy: if the specific class has been assigned a classroom (case 1)
                        //         if has not been assigned, then get type and randomly choose (case 2)
                        int[] possibleRoomSelect;
                        if (input3D[i][j][k].getClassroom() != null) {
                            possibleRoomSelect = new int[]{input3D[i][j][k].getClassroom().getId()};
                        }else {
                            roomType = input3D[i][j][k].getType();
                            switch (roomType) {
                                case CBL:
                                    possibleRoomSelect = new int[lecRoomStart];
                                    for (int idx = 0; idx < lecRoomStart; idx++) {
                                        possibleRoomSelect[idx] = idx;
                                    }
                                    possibleRoomSelect = randomSort(possibleRoomSelect);
                                    break;
                                case LEC:
                                    possibleRoomSelect = new int[labRoomStart - lecRoomStart];
                                    for (int idx = 0; idx < labRoomStart - lecRoomStart; idx++) {
                                        possibleRoomSelect[idx] = idx+labRoomStart;
                                    }
                                    possibleRoomSelect = randomSort(possibleRoomSelect);
                                    break;
                                case LAB:
                                    possibleRoomSelect = new int[roomNum - labRoomStart];
                                    for (int idx = 0; idx < roomNum - labRoomStart; idx++) {
                                        possibleRoomSelect[idx] = idx + lecRoomStart;
                                    }
                                    possibleRoomSelect = randomSort(possibleRoomSelect);
                                    break;
                                    default: possibleRoomSelect = new int[0];
                                    break;
                            }
                        }
                        //select a room id
                        int possibleRoomPointer = 0; // cannot larger than the specific room number
                        roomID = possibleRoomSelect[possibleRoomPointer];

                        // below random choose a begin time slot
                        int timeSlotPoiner = 0;
                        int putIn = 0;
                        classDuration = input3D[i][j][k].getDuration();
                        numOfSlot = (int) (classDuration / 0.5);
                        while(timeSlotPoiner < 16) {
                            for (int sln = 0; sln < numOfSlot; sln++) {
                                if(timetable[roomID][currentWeekday][timeSlotPoiner+sln] != null) {
                                    break;
                                }else {
                                    putIn++;
                                }
                            }
                            if (putIn == numOfSlot) {
                                input3D[i][j][k].setTimeAndPos(currentWeekday, timeSlotPoiner, roomList.getRoomList()[roomID]);
                                for (int sln = 0; sln < numOfSlot; sln++) {
                                    timetable[roomID][currentWeekday][timeSlotPoiner+sln] =
                                            input3D[i][j][k];
                                }
                                break;
                            }else {
                                timeSlotPoiner += numOfSlot - 1;
                            }
                        }
                        if (timeSlotPoiner == 16) {
                            if (possibleRoomPointer == possibleRoomSelect.length) {
                                System.out.println("Fail to find suitable slot"); // 没有考虑换天的情况
                            }else {
                                roomID = possibleRoomSelect[++possibleRoomPointer];
                                timeSlotPoiner = 0;
                            }
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

    public static int[] randomSort(int[] ls) { // given a ls, randomly sort all the elements
        Random rr = new Random();
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> arrayList = new ArrayList<>();

        int randomInt;
        for (int i = 0; i < ls.length; i++) {
            randomInt = rr.nextInt(500);
            map.put(randomInt, ls[i]);
            arrayList.add(randomInt);
        }
        arrayList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        int[] result = new int[ls.length];
        for (int i = 0; i < ls.length; i++) {
            result[i] = map.get(arrayList.get(i));
        }
        return result;
    }
}
