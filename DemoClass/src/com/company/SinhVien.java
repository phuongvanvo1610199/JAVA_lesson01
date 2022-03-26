package com.company;

import java.util.Scanner;

public class SinhVien {
    private String ten;
    private String lop;
    private double diemTB;
    public static Scanner ip = new Scanner(System.in);
    public SinhVien() {
    }

    public SinhVien(String ten, String lop, double diemTB) {
        this.ten = ten;
        this.lop = lop;
        this.diemTB = diemTB;
    }

    public String getTen() {
        return ten;
    }

    public String getLop() {
        return lop;
    }

    public double getDiemTB() {
        return diemTB;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public void setDiemTB(double diemTB) {
        this.diemTB = diemTB;
    }

    public void nhap(){
        System.out.println("Nhập tên: ");
        ten = ip.next();
        System.out.println("Nhập lớp: ");
        lop = ip.next();
        System.out.println("Nhập điểm: ");
        diemTB = ip.nextDouble();
    }

    public void show(){
        System.out.println("Tên: "+this.ten);
        System.out.println("Tên: "+this.lop);
        System.out.println("Tên: "+this.diemTB);
    }
    @Override
    public String toString() {
        return "SinhVien{" +
                "ten='" + ten + '\'' +
                ", lop='" + lop + '\'' +
                ", diemTB=" + diemTB +
                '}';
    }
}
