class SkipThread2 extends Thread {
/*
* Testing SkipList with multiple insertions by multiple threads
 */

    STMSkipList<Integer> a;
    int val;

    public SkipThread2(STMSkipList<Integer> a, int i) {
        this.a = a;
        val = i;
    }

    public void run() {
        boolean condition = false;
        do {
            Transaction tx = new Transaction();
            a.putItem("AA" + val, val, tx);
            condition = tx.commit();
        } while (!condition);
    }

}


public class SkipListTest2 {

    public static void main(String args[]) throws InterruptedException {
        STMSkipList<Integer> skipList = new STMSkipList<>();

        //Map Initialized
        int n = 500;
        SkipThread2[] threads = new SkipThread2[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new SkipThread2(skipList, i);
        }

        for (int i = 0; i < n; i++) {
            threads[i].start();
        }
        skipList.prinList();
    }

}
