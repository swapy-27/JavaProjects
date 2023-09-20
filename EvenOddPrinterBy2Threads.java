package smallproject;

public class EvenOddPrinterBy2Threads  implements   Runnable{

    private static int i = 1;

    Object object;

    EvenOddPrinterBy2Threads(Object lock){
        this.object = lock;
    }

    public static void main(String[] args) {
        //thread 1 -- odd
        //thread 2 -- even
        Object lock = new Object();
        Thread thread1 = new Thread(new EvenOddPrinterBy2Threads(lock));
        Thread thread2 = new Thread(new EvenOddPrinterBy2Threads(lock));
        thread1.setName("odd");
        thread2.setName("even");
        thread1.start();
        thread2.start();

    }
    // a classic case of deadlock has appeared
    @Override
    public void run() {
        while(i <=100){

            if(i%2!=0  && Thread.currentThread().getName().equals("odd")){

                synchronized (object){
                    //odd
                    System.out.println("Odd value : - "+i);
                    i++;
                }

            }

            if (i%2==0  && Thread.currentThread().getName().equals("even")){

                synchronized (object) {
                    System.out.println("Even value : - " + i);
                    i++;
                }
            }

        }
    }
}
