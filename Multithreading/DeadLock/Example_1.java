package smallproject.Multithreading.DeadLock;

//Threads might go or not go into the state of deadlock --- depends if on same time both release a lock and try to acquire on new resource
public class Example_1 {
    public static void main(String[] args) {
        Object res1 = new Object();
        Object res2 = new Object();
        Thread t1 = new Thread(()->{
            synchronized (res1){
                System.out.println("thread 1 is holding resource 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            synchronized (res2){
                System.out.println("thread 1 is holding resource 2");
            }

        });
        Thread t2 = new Thread(()->{
            synchronized (res2){
                System.out.println("thread 2 is holding resource 2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            synchronized (res1){
                System.out.println("thread 2 is holding resource 1");
            }
        });

        t1.start();
        t2.start();
    }
}
