package com.company;

import java.util.HashMap;

public class Main {
    static final int size = 10000000;
    static int parts = 1;
    static int remainder = size%parts;
    static final int h = size / parts;

    public static void main(String[] args) {
        firstMethod();
        secondMethod(remainder);
    }

    static void firstMethod(){
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println(System.currentTimeMillis() - a);
        System.out.println(arr[size/2]);

    }

    static void secondMethod(int remainder){
        if (parts == 1) {
            firstMethod();
            return;
        }
        if(remainder != 0){
            parts = parts+1;
        }
        float[] arr = new float[size];
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();
        HashMap<Integer, float[]> arrOfArr = new HashMap<>();
        for (int i = 0; i < parts; i++) {
            float[] arrCopy;
            if(remainder != 0 && i == parts-1){
                arrCopy = new float[remainder];
                System.arraycopy(arr, h*i, arrCopy, 0, remainder);
            }else {
                arrCopy = new float[h];
                System.arraycopy(arr, h*i, arrCopy, 0, h);
            }
            arrOfArr.put(i, arrCopy);
        }

        for (int i = 0; i < parts; i++) {
            float[] arrCopy = arrOfArr.get(i);
            int factor = i;
            Thread t = new Thread(() -> {
                for (int j = 0; j < h; j++) {
                    arrCopy[j] = (float)(arrCopy[j] * Math.sin(0.2f + (factor*h+j) / 5) * Math.cos(0.2f + (factor*h+j) / 5) * Math.cos(0.4f + (factor*h+j) / 2));
                    if(remainder != 0 && j == remainder-1 && factor == parts-1){
                        break;
                    }
                }
            });
            t.start();
        }


//        float[] arr1 = new float[h];
//        System.arraycopy(arr, 0, arr1, 0, h);
//        float[] arr2 = new float[h];
//        System.arraycopy(arr, h, arr2, 0, h);

//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < h; i++) {
//                arr1[i] = (float)(arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            }
//        });
//        Thread t2 = new Thread(() -> {
//            for (int i = h; i < size; i++) {
//                arr2[i - h] = (float)(arr2[i - h] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//            }
//        });
//
//        t1.start();
//        t2.start();

        for (int i = 0; i < parts; i++) {
            float[] arrCopy = arrOfArr.get(i);
            if(remainder != 0 && i == parts-1){
                System.arraycopy(arrCopy, 0, arr, i * h, remainder);
            }else {
                System.arraycopy(arrCopy, 0, arr, i * h, h);
            }
        }

//        System.arraycopy(arr1, 0, arr, 0, h);
//        System.arraycopy(arr2, 0, arr, h, h);
        System.out.println(System.currentTimeMillis() - a);
        System.out.println(arr[size/2]);
    }
}
