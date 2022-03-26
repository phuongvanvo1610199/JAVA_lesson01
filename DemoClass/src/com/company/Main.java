package com.company;

import sun.misc.SignalHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Scanner ip = new Scanner(System.in);

    public static void main(String[] args) {
        // write your code here
        List<SinhVien> dataSinhVien = new ArrayList<>();


        System.out.println("=================================");
        dataSinhVien.stream().forEach(stu -> System.out.println(stu));
        while (true) {

            System.out.println("MENU");
            System.out.println("1.Nhập:");
            System.out.println("2.Tìm kiếm theo tên:");
            System.out.println("3.Sắp xếp theo điểm tăng hoặc giảm:");
            System.out.print("Nhập lựa chọn: ");
            int luaChon = ip.nextInt();

            switch (luaChon) {
                case 1:
                    System.out.println("NHẬP THÔNG TIN SINH VIÊN");
                    System.out.print("Nhập số lượng sinh viên:");
                    int n = ip.nextInt();
                    for (int i = 0; i < n; i++) {
                        System.out.println("thứ " + i + ":");
                        SinhVien sinhVien = new SinhVien();
                        sinhVien.nhap();
                        dataSinhVien.add(sinhVien);
                    }
                    break;
                case 2:
                    System.out.println("TÌM KIẾM SINH VIÊN THEO TÊN");
                    System.out.println("Nhập tên sinh viên cần tìm: ");
                    String findName = ip.next();
                    findStuWithName(findName, dataSinhVien);
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        }
    }

    //    Tìm kiếm theo tên: findStuWithName
//    INPUT : String findName
//    OUTPUT: Thông tin sinh viên nếu có
//    DKC: Danh sách sinh viên
    public static void findStuWithName(String findName, List<SinhVien> dataSinhVien) {
        if (!dataSinhVien.isEmpty()) {
            boolean flag = true;
            for (SinhVien ele : dataSinhVien) {
                if (findName.equalsIgnoreCase(ele.getTen())) {
                    System.out.println(ele);
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
            if (flag == false) {
                System.out.println("Not Found !!");
            }
        } else {
            System.out.println("Empty List !!!");
        }
    }

    //    Sắp xếp theo điểm
//    INPUT: lựa chọn tăng hay giảm
//    OUTPUT: sắp xếp danh sách tăng hoặc giảm
//    DKC: danh sách sinh viên
    public static void sortStudentByDTB(boolean flag, List<SinhVien> data) {
        List<SinhVien> sortedList = data;
        if (!Objects.isNull(data)) {
            // Asc
            if (flag) {
                sortedList.sort((SinhVien s1, SinhVien s2) -> (int) (s1.getDiemTB() - s2.getDiemTB()));
            } else {
                // Descending
                sortedList.sort((SinhVien s1, SinhVien s2) -> (int) (s2.getDiemTB() - s1.getDiemTB()));
            }
            sortedList.stream().forEach(student -> System.out.println(student));
        } else {
            System.out.println("Empty List");
        }
    }
}


