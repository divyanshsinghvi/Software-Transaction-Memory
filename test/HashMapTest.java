
class HashThread extends Thread {
    HashMap<Integer> a;

    public HashThread (HashMap<Integer> a) {
        this.a = a;
       // a.printMap();
    }

    public void run() {
        boolean condition = false;
        do {
            Transaction tx = new Transaction();
            int temp = a.getItem("AA", tx);
            if(temp==-1)
                continue;
            a.putItem("AA",temp+1,tx);
            condition = tx.commit();
        }while(!condition);
        a.printMap();
    }

}


public class HashMapTest {

    public static void main(String args[]) throws InterruptedException {
        HashMap<Integer> hashMap = new HashMap<>();

        Transaction tx = new Transaction();
        boolean condition;
        do {
            hashMap.putItem("AA", 3, tx);
            condition  = tx.commit();
        }while(!condition);
        hashMap.printMap();
        System.out.println("--------------");
        //Map Initialized
        int n =500;
        HashThread[] threads = new HashThread[n];
        for(int i=0;i<n;i++){
            threads[i] = new HashThread(hashMap);
//           threads[i].sleep(100);
        }

        System.out.println("I am here");
        for(int i=0;i<n;i++)
            threads[i].start();

    }

}
