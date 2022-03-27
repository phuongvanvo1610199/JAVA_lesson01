package com.company;

import java.util.Scanner;

public class Students {
    private Integer id;
    private String name;
    private Double point;
    public static Scanner ip = new Scanner(System.in);

    public Students() {
    }

    public Students(Integer id, String name, Double point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPoint() {
        return point;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public void writeStu() {
        System.out.print("Enter ID stu:");
        id = Integer.parseInt(ip.nextLine());
        System.out.print("Enter Name stu:");
        name = ip.nextLine();
        System.out.print("Enter Point stu:");
        point = ip.nextDouble();
        ip.nextLine();
    }

    public void showStu() {
        System.out.println("ID: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("Point: " + this.point);
    }

    @Override
    public String toString() {
        return "Students{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", point=" + point +
                '}';
    }
}
