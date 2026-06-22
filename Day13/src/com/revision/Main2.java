package com.revision;


class MyRunnable1 implements Runnable {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Runnable thread: " + i);
        }
    }
}

public class Main2 {
    public static void main(String[] args) {
    	Thread t1 = new Thread(() -> {
    	    for (int i = 1; i <= 5; i++) {
    	        System.out.println("Lambda thread: " + i);
    	    }
    	});
    	t1.start();
    }
}
