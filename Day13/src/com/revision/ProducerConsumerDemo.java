package com.revision;

import java.util.LinkedList;
import java.util.Queue;

class SharedBuffer {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 5;

    // Producer method
    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == CAPACITY) {
            System.out.println("Buffer full, producer waiting...");
            wait();  // release lock and wait
        }
        queue.add(item);
        System.out.println("Produced: " + item + ", Buffer size: " + queue.size());
        notifyAll();  // wake up consumer
    }

    // Consumer method
    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("Buffer empty, consumer waiting...");
            wait();  // release lock and wait
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item + ", Buffer size: " + queue.size());
        notifyAll();  // wake up producer
        return item;
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();

        // Producer thread
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(500); // simulate time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    buffer.consume();
                    Thread.sleep(1000); // simulate time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}