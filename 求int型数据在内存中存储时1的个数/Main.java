package ��int���������ڴ��д洢ʱ1�ĸ���;

import java.util.*;
import java.io.*;

    public class Main{
    
    public static void countOne(int num){
        int count=0;
        while(num!=0){
            if(num%2==1) count++;
            num/=2;
        }
        System.out.print(count);
    }
    
    public static void main(String[] args){
        Scanner scan=new Scanner(System.in);
        if(scan.hasNextInt()){
            countOne(scan.nextInt());
        }
    }

}
