package smallproject.Multithreading.DeadLock;



import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

    class RecordData {
        int[] attrs = new int[3];
    }

    class Record {
        RecordData data = new RecordData();
        Lock lock = new ReentrantLock();
    }

    class Database {
        Record[] records = new Record[3];
    }

    public class Example_2 {
        private static final int NUM_RECORDS = 3;
        private static final int NUM_CONN = 6;
        private static final Database db = new Database();
        private static final Random random = new Random();

        public static void acquireLock(String txn, int recIdx) {
            System.out.println("txn " + txn + ": wants to acquire lock on record: " + recIdx);
            db.records[recIdx].lock.lock();
            System.out.println("txn " + txn + ": acquired lock on record: " + recIdx);
        }

        public static void releaseLock(String txn, int recIdx) {
            db.records[recIdx].lock.unlock();
            System.out.println("txn " + txn + ": released lock on record: " + recIdx);
        }

        public static void initDB() {
            for (int i = 0; i < NUM_RECORDS; i++) {
                db.records[i] = new Record();
                db.records[i].data.attrs[0] = i; // id
                db.records[i].data.attrs[1] = random.nextInt(20) + 10; // age
            }
        }

        public static void main(String[] args) {
            initDB();

            for (int i = 0; i < NUM_CONN; i++) {
                char txn = (char) (i + 'A');
                Thread thread = new Thread(() -> {
                    while (true) {
                        int rec1 = random.nextInt(NUM_RECORDS);
                        int rec2 = random.nextInt(NUM_RECORDS);

                        if (rec1 == rec2) {
                            continue;
                        }

//                        if (rec1 > rec2) {
//                            int t = rec1;
//                            rec1 = rec2;
//                            rec2 = t;
//                        }

                        acquireLock(String.valueOf(txn), rec1);
                        acquireLock(String.valueOf(txn), rec2);

                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        releaseLock(String.valueOf(txn), rec2);
                        releaseLock(String.valueOf(txn), rec1);

                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

                thread.start();
            }
        }
    }

