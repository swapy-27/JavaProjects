package smallproject.ConcurrentQueue;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * Thread Safe Queue
 * Thread Pool internal implementation has been done with this
 * Batch processing is implemented fastly and safe way with this-- u have multiple data uploaded at same time then
 *          it can be used.
 *
 *
 *
 *
 * **/


public class  ConcurrentQueue{
   private static ArrayList<Integer> q = new ArrayList<>();
    private static List<Thread> threads = new ArrayList<>();
    //enqueue
    private static final Lock mutex = new ReentrantLock();
    private static void enqueue(int i ){
        mutex.lock();
        q.add(i);
        mutex.unlock();
    }

    //dequeue
    private static Integer dequeue(){
        if(q.isEmpty()){
            throw  new NullPointerException("queue is empty");
        }
        int val = q.get(0);
        mutex.lock();
        q.remove(0);
        mutex.unlock();
        return val;
    }

    public static void main(String[] args) throws InterruptedException {
        for(int  i =0 ; i<100000 ; i++){
          Thread thread=   new Thread(()->{
                enqueue(1);

            });


          thread.start();
          threads.add(thread);
        }

        for(int  i =0 ; i<100000 ; i++){
            Thread thread=   new Thread(()->{
                dequeue();

            });


            thread.start();
            threads.add(thread);
        }
        waitForThreads(threads);
        System.out.println(q.size());
    }


    public static void waitForThreads(List<Thread> threads)  {
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread got interrupted while waiting");
            }
        });

    };
}