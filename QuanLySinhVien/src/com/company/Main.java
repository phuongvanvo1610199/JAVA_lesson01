package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String username = "Admin";
    public static final String password = "123";
    public static Scanner ip = new Scanner(System.in);

    public static void main(String[] args) {
        boolean flag = false;

        List<ClassInSchools> dataSchool = new ArrayList<>();
        System.out.println("Enter Number of Class In School: ");
        int n = Integer.parseInt(ip.nextLine());
        ClassInSchools classInSchools;
        for (int i = 0; i < n; i++) {
            classInSchools = new ClassInSchools();
            classInSchools.writeClass();
            dataSchool.add(classInSchools);
        }

        System.out.println("======================");
        for (int i = 0; i < dataSchool.size(); i++) {
            dataSchool.get(i).showClass();
        }
        do {
            System.out.println("== APP FOR SERVICE'S SCHOOL ==");
            System.out.println("LOGIN:");
            System.out.print("Enter username: ");
            String inputUS = ip.nextLine();
            System.out.print("Enter password: ");
            String inputPW = ip.nextLine();
            if (inputUS.equalsIgnoreCase(username) && inputPW.equalsIgnoreCase(password)) {
                flag = true;
            } else {
                System.out.println("Username or password is wrong !! ");
            }
        }
        while (flag == false);
        System.out.println("****************");
        System.out.println("LOGIN ACCESS !!!");
        System.out.println("****************");
        boolean flag1 = true;
        while (flag1) {
            System.out.println("MENU:");
            System.out.println("1.Xem thong tin hoc sinh cua 1 lop");
            System.out.println("2.Tim kiem thong tin hoc sinh theo ten, mshs");
            System.out.println("3.Them hoc sinh cho 1 lop");
            System.out.println("4.Sap xep thu tu hoc sinh cho 1 lop theo diem");
            System.out.println("5.Thoat chuong trinh");
            System.out.println("Enter choose: ");
            int choose = ip.nextInt();
            ip.nextLine();
            switch (choose) {
                case 1:
                    System.out.println("=====================");
                    System.out.println("Xem thong tin hoc sinh cua 1 lop bat ky:");
                    System.out.print("Enter Class's Id: ");
                    int findId = Integer.parseInt(ip.nextLine());
                    showInfoStuInAClass(findId, dataSchool);
                    break;
                case 2:
                    System.out.println("Tìm kiếm học sinh theo mssv va ten:");
                    System.out.print("Enter Student's id: ");
                    int mssv = Integer.parseInt(ip.nextLine());
                    System.out.print("Enter Student's name: ");
                    String ten = ip.nextLine();
                    findInfoStuWithIdAndName(mssv, ten, dataSchool);
                    break;
                case 3:
                    System.out.print("Enter Class's id: ");
                    int idLop = ip.nextInt();
                    ip.nextLine();
                    addStuIntoClass(idLop, dataSchool);
                    System.out.println("Them thanh cong");
                    break;
                case 4:
                    boolean fla = true;
                    int change;
                    do {
                        System.out.print("Nhap lua chon: (1->Tang||2->Giam): ");
                        change = ip.nextInt();
                        if (change == 1 || change == 2) {
                            if (change == 1) {
                                fla = true;
                            } else {
                                fla = false;
                            }
                        } else {
                            System.out.println("Lua chon sai nhap lai: ");
                        }
                    } while (change != 1 && change != 2);
                    sortStudentByDTB(fla, dataSchool);
                    System.out.println();
                    break;
                case 5:
                    flag1 = false;
                    break;
                default:
                    break;
            }
        }
    }

    //    YÊU CẦU 1
    public static void showInfoStuInAClass(int findId, List<ClassInSchools> dataSchool) {
        for (int i = 0; i < dataSchool.size(); i++) {
            if (findId == dataSchool.get(i).getIdClass()) {
                System.out.println("Thong tin hoc sinh cua lop:");
                for (int j = 0; j < dataSchool.get(i).getData().size(); j++) {
                    System.out.println("Hoc sinh thu "+ (j+1)+": ");
                    dataSchool.get(i).getData().get(j).showStu();
                    System.out.println("===================================");
                }
            }
        }

    }

    //    YÊU CẦU 2
    public static void findInfoStuWithIdAndName(int mssv, String ten, List<ClassInSchools> dataSchool) {
        for (int i = 0; i < dataSchool.size(); i++) {
            for (int j = 0; j < dataSchool.get(i).getData().size(); j++) {
                if (dataSchool.get(i).getData().get(j).getId() == mssv && dataSchool.get(i).getData().get(j).getName().equalsIgnoreCase(ten)) {
                    dataSchool.get(i).getData().get(j).showStu();
                }
            }
        }
    }

    //    YÊU CÂU 3
    public static void addStuIntoClass(int idLop, List<ClassInSchools> dataSchool) {
        Students students;
        for (int i = 0; i < dataSchool.size(); i++) {
            if (idLop == dataSchool.get(i).getIdClass()) {
                students = new Students();
                students.writeStu();
                dataSchool.get(i).getData().add(students);
            }
        }
    }

    public static void sortStudentByDTB(boolean flag, List<ClassInSchools> data) {
        List<ClassInSchools> sortedList = data;
        if (!sortedList.isEmpty()) {
            // Asc
            for (int i = 0; i < sortedList.size(); i++) {
                if (!sortedList.get(i).getData().isEmpty()) {
                    if (flag) {
                        sortedList.get(i).getData().sort((Students s1, Students s2) -> (int) (s1.getPoint() - s2.getPoint()));
                    } else {
                        // Descending
                        sortedList.get(i).getData().sort((Students s1, Students s2) -> (int) (s2.getPoint() - s1.getPoint()));
                    }
                    sortedList.stream().forEach(student -> System.out.println(student));
                } else {
                    System.out.println("Lop thu " + i + " khong ton tai sinh vien nao !");
                }
            }

        } else {
            System.out.println("Khong ton tai lop nao");
        }
    }
}