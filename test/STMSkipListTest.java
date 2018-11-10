
class SkipThread extends Thread {
    /*
    * Testing updates to single item using skiplist
     */
    STMSkipList<Integer> a;

    public SkipThread(STMSkipList<Integer> a, int i) {
        this.a = a;
    }

    public void run() {
        boolean condition = false;
        do {
            Transaction tx = new Transaction();
            int temp = a.getItem("AA", tx);
            if (temp == -1)
                continue;
            a.putItem("AA", temp + 1, tx);
            condition = tx.commit();
        } while (!condition);
    }

}


public class STMSkipListTest {

    public static void main(String args[]) throws InterruptedException {
        STMSkipList<Integer> skipList = new STMSkipList<>();

        Transaction tx = new Transaction();
        boolean condition;
        do {
            skipList.putItem("AA", 3, tx);
            condition = tx.commit();
        } while (!condition);

        //Map Initialized
        int n = 500;
        SkipThread[] threads = new SkipThread[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new SkipThread(skipList, i);
        }

        for (int i = 0; i < n; i++) {
            threads[i].start();
        }
        Thread.sleep(100);
        skipList.prinList();
    }

}
