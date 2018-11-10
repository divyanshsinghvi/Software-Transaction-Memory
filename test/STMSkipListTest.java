
class SkipThread extends Thread {
    STMSkipList<Integer> a;
    int val;
    public SkipThread (STMSkipList<Integer> a,int i) {
        this.a = a;
        val = i;
        // a.printMap();
    }

    public void run() {
        boolean condition = false;
        do {
            Transaction tx = new Transaction();
           // String key = "AA"+val;
            int temp = a.getItem("AA", tx);
            if(temp==-1)
                continue;
            a.putItem("AA",temp+1,tx);
            condition = tx.commit();
        }while(!condition);
    }

}


public class STMSkipListTest {

    public static void main(String args[]) throws InterruptedException {
        STMSkipList<Integer> skipList = new STMSkipList<>();

        Transaction tx = new Transaction();
        boolean condition;
        do {
            skipList.putItem("AA", 3, tx);
            condition  = tx.commit();
        }while(!condition);
        System.out.println("--------------");
        //Map Initialized
        int n =500;
        SkipThread[] threads = new SkipThread[n];
        for(int i=0;i<n;i++){
            threads[i] = new SkipThread(skipList,i);
//           threads[i].sleep(100);
        }

        System.out.println("I am here");
        for(int i=0;i<n;i++) {
            threads[i].start();
        }
        skipList.prinList();
    }

}
