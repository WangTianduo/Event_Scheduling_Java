package algorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class Professor {
    private String name;
    private int id;
    private HashMap<String, ArrayList<StudentGroup>> courseTable; // key is the name of subject
    private ArrayList<SpecificClass> classSet; // is used to check conflict

    Professor(String name, int id) {
        this.id = id;
        this.name = name;
        this.courseTable = new HashMap<>();
        this.classSet = new ArrayList<>();
    }

    public void addSubject(String subjectName, StudentGroup sg) {
        if (courseTable.containsKey(subjectName)) {
            courseTable.get(subjectName).add(sg);
        }else {
            courseTable.put(subjectName, new ArrayList<StudentGroup>());
            courseTable.get(subjectName).add(sg);
        }
    }

    public void addSpecificClass(SpecificClass sClass) {
        if(classSet.contains(sClass)) {
            System.out.println("Error: Alr contain this sClass");
        }else {
            classSet.add(sClass);
        }
    }

    public void addSpecificClass(SpecificClass[] sClassSet) {
        for (SpecificClass c: sClassSet) {
            if (!classSet.contains(c)) {
                classSet.add(c);
            }else{
                System.out.println("Error: Alr contain this sClass");
            }
        }
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<SpecificClass> getClassSet() {
        return classSet;
    }

    public HashMap<String, ArrayList<StudentGroup>> getCourseTable() {
        return courseTable;
    }
}
