package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner ip = new Scanner(System.in);
        int a[] = new int[10];
        for (int i = 0 ; i < a.length; i++){
            System.out.print("enter ele" + i +": ");
            a[i] = ip.nextInt();
        }

        System.out.println("===============================");
        for(int ele : a){
            System.out.println(ele);
        }
    }
}
