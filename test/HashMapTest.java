
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
            if(!a.putItem("AA",temp+1,tx))
                continue;
            condition = tx.commit();
        }while(!condition);
        a.printMap();
    }

}

class HashThread2 extends Thread {
    HashMap<Integer> a;
    int value;

    public HashThread2 (HashMap<Integer> a,int value) {
        this.a = a;
        this.value = value;
        // a.printMap();
    }

    public void run() {
        boolean condition = false;
        do {
            Transaction tx = new Transaction();
            if(!a.putItem("AA"+value,value,tx))
                continue;
            condition = tx.commit();
        }while(!condition);
      //  a.printMap();
    }

}



public class HashMapTest {

    public static void test1(HashMap<Integer> hashMap){
        Transaction tx = new Transaction();
        boolean condition = false;
        do {
            if(!hashMap.putItem("AA", 3, tx))
                continue;
            condition  = tx.commit();
        }while(!condition);
        //hashMap.printMap();
        //System.out.println("--------------");
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

    public static void test2(HashMap<Integer> hashMap) throws InterruptedException {
        Transaction tx = new Transaction();
        boolean condition = false;
        do {
            if(!hashMap.putItem("AA", 3, tx))
                continue;
            condition  = tx.commit();
        }while(!condition);
        //hashMap.printMap();
        //System.out.println("--------------");
        //Map Initialized
        int n =200;
        HashThread2[] threads = new HashThread2[n];
        for(int i=0;i<n;i++){
            threads[i] = new HashThread2(hashMap,i);
//           threads[i].sleep(100);
        }

        System.out.println("I am here");
        for(int i=0;i<n;i++)
            threads[i].start();

    }

    public static void main(String args[]) throws InterruptedException {
        HashMap<Integer> hashMap = new HashMap<>();
        test2(hashMap);
        Thread.sleep(300);
        hashMap.printMap();

    }

}
