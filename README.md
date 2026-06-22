🚀 Day 13 – Multithreading (Threads, Synchronization, Producer-Consumer)
==========================================================================

Aaj ka topic Multithreading hai – Java ka ek powerful feature jo aapko ek saath multiple tasks run karne deta hai. Backend development mein parallel processing, async APIs, aur high-performance systems ke liye yahi base hai.

Interview mein yeh topic bahut important hai – especially Producer-Consumer problem.

🔹 1. What is Multithreading?
================================

-> Multithreading ka matlab hai ek program ke andar multiple threads ek saath chalna.

-> Ek thread ek lightweight process hai – CPU ka ek independent execution path.

-> Single-threaded: 
   ----------------
   Ek kaam khatam, phir dusra start.

-> Multi-threaded: 
   ---------------
   Saare kaam ek saath chaltay hain (parallelism).

Why use multithreading?
========================

-> CPU core ka maximum use

-> Faster execution (especially I/O-heavy tasks)

-> Responsive user interfaces

🔹 2. Creating Threads (2 Ways)
================================

1. Extending Thread class (not recommended if you need to inherit another class)

        MyThread t1 = new MyThread();
        t1.start();  // start() calls run()

2. Implementing Runnable interface (✅ Recommended – Java supports multiple interface inheritance)

        Thread t1 = new Thread(new MyRunnable());
        t1.start();

🔸 Lambda (modern way)


Thread t1 = new Thread(() -> {
    for (int i = 1; i <= 5; i++) {
        System.out.println("Lambda thread: " + i);
    }
});
t1.start();

🔹 3. Thread Lifecycle & Important Methods
===========================================

States: NEW → RUNNABLE → BLOCKED/WAITING → TERMINATED

Method			|	Description
------------------------|------------------------------------------------
start()			|	Starts thread (calls run())
sleep(ms)		|	Pauses thread for given milliseconds
join()			|	Waits for thread to finish
yield()			|	Hints CPU to give chance to other threads
setPriority(int)	|	1 (MIN) to 10 (MAX) – not guaranteed
--------------------------------------------------------------------------

Thread t1 = new Thread(() -> {

    try { Thread.sleep(1000); } catch (InterruptedException e) {}
    
    System.out.println("Thread slept!");

});

t1.start();

t1.join();  // Main waits for t1 to finish

🔹 4. Race Condition & Synchronization (Critical)
==================================================

Jab multiple threads ek hi shared resource (variable/list) ko modify karte hain, toh data inconsistency ho jati hai – ise Race Condition kehte hain.
Isko solve karne ke liye synchronized keyword use karte hain.

Without Synchronization (❌ Problem)
-------------------------------------

class Counter {
    int count = 0;
    void increment() { count++; }  // Not thread-safe
}

// Multiple threads increment same counter → count will be less than expected.

With Synchronization (✅ Solution)
-----------------------------------

class Counter {
    int count = 0;
    
    // Synchronized method – only 1 thread can access at a time
    synchronized void increment() {
        count++;
    }
}

// OR use synchronized block (more efficient)
void increment() {
    synchronized(this) {
        count++;
    }
}

Note: 
-----
volatile keyword bhi use hota hai variable visibility ke liye (but synchronization ensures both visibility + atomicity).

🔹 5. Inter-thread Communication (wait, notify, notifyAll)
===========================================================

Yeh methods Object class ke hain – sirf synchronized block/method ke andar hi call ho sakte hain.

Method		|	Description
----------------|----------------------------------------------
wait()		|	Current thread releases lock and waits
notify()	|	Wakes up one waiting thread
notifyAll()	|	Wakes up all waiting threads
---------------------------------------------------------------

🔹 6. Deadlock Concept
========================

Deadlock tab hota hai jab two threads ek dusre ka lock wait kar rahe ho – dono koi bhi aage nahi badh paate.

Example: Thread1 locks Resource A and waits for B; Thread2 locks B and waits for A.
--------
Avoid deadlock: Always acquire locks in the same order.
---------------

🔹 7. Practice Task – Producer-Consumer
=========================================

Producer adds to queue, Consumer removes

Use wait() when full/empty

Use notifyAll() after producing/consuming

Example
-------

// Inside synchronized produce()

while (queue.size() == CAPACITY) wait();

queue.add(item);

notifyAll();

// Inside synchronized consume()

while (queue.isEmpty()) wait();

int item = queue.poll();

notifyAll();
