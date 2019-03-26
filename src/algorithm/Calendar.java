package algorithm;

public class Calendar {
    SpecificClass[][][] timetable;

    Calendar(Classroom[] roomList) {
        int roomNum = roomList.length;
        timetable = new SpecificClass[5][20][roomNum]; // weekday, half-hour, room
    }
}
