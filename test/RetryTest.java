
class RetryHashThread extends Thread {
        HashMap<Integer> a;
        public RetryHashThread(HashMap<Integer> a) {
            this.a = a;
        }

        public void run() {
            boolean condition = false;
            do {
                Transaction tx = new Transaction();
                int temp = a.getItem("AA", tx);
                if (temp == -1)
                    continue;
                if(tx.transactionId == 8)
                {
                    if(!tx.retry())
                        continue;
                }
                a.putItem("AA", temp + 1, tx);
                condition = tx.commit();
            } while (!condition);
            a.printMap();
        }

    }


    public class RetryTest {

        public static void main(String args[]) throws InterruptedException {
            HashMap<Integer> hashMap = new HashMap<>();

            Transaction tx = new Transaction();
            boolean condition;
            do {
                hashMap.putItem("AA", 3, tx);
                condition = tx.commit();
            } while (!condition);
            hashMap.printMap();
            System.out.println("--------------");
            //Map Initialized
            int n = 500;
            RetryHashThread[] threads = new RetryHashThread[n];
            for (int i = 0; i < n; i++) {
                threads[i] = new RetryHashThread(hashMap);
//           threads[i].sleep(100);
            }

            System.out.println("I am here");
            for (int i = 0; i < n; i++)
                threads[i].start();

        }

    }
