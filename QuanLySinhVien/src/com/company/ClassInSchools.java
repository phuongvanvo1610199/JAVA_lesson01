package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassInSchools {
    //    private Students students;
    private String className;
    private Integer idClass;
    private List<Students> data = new ArrayList<>();
    public static Scanner ip = new Scanner(System.in);

    public ClassInSchools() {
    }

    public ClassInSchools(String className, Integer idClass, List<Students> data) {
        this.className = className;
        this.idClass = idClass;
        this.data = data;
    }

    public String getClassName() {
        return className;
    }

    public Integer getIdClass() {
        return idClass;
    }

    public List<Students> getData() {
        return data;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setIdClass(Integer idClass) {
        this.idClass = idClass;
    }

    public void setData(List<Students> data) {
        this.data = data;
    }

    public void writeClass() {
        System.out.print("Enter class's ID:");
        idClass = Integer.parseInt(ip.nextLine());
        System.out.print("Enter Class's Name:");
        className = ip.nextLine();
        System.out.print("Enter number Student:");
        int numberStu = ip.nextInt();
        for (int i = 0; i < numberStu; i++) {
            Students students = new Students();
            students.writeStu();
            data.add(students);
        }
    }

    public void showClass() {
        System.out.println("Class's Name: " + this.className);
        System.out.println("Class's Id: " + this.idClass);
        System.out.println("Number Of Student: " + this.data.size());
    }
}
