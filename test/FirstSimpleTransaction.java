class MyThread extends Thread {
    TVar<Integer> a;
    public MyThread (TVar<Integer> a) {
        this.a = a;
        //System.out.println(a.value);
    }

    public void run() {
        boolean condition=false;
        do {
            condition = false;
            Transaction tx = new Transaction();
            int tem = (int) tx.read(a);
            if (tem == -1) {
                continue;
            }
            int temp = tem + 1;
            TVar<Integer> z= new TVar<>();
            z.value=temp;
            tx.write(a,z);
            //System.out.println(a.value);
            condition = tx.commit();
        }while (!condition);
        System.out.println(a.value);

    }
}

public class FirstSimpleTransaction {


    public static void main(String args[]) throws InterruptedException {
        TVar<Integer> a = new TVar<>();
        a.value=3;
        int nothread=107;
        MyThread temp[] = new MyThread[nothread];
        for(int i=0;i<nothread;i++){
            temp[i] = new MyThread(a);
       }

        for(int i=0;i<nothread;i++) {
            temp[i].start();
        }
        Thread.sleep(100);
        System.out.println(a.value);

    }
    }

