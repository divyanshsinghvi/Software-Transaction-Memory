class MyThread extends Thread {
    TVar<Integer> a;
    public MyThread (TVar<Integer> a) {
        this.a = a;
    }

    public void run() {
        boolean condition=true;
        do {
            Transaction tx = new Transaction();
            int tem = (int) tx.read(a);
            if (tem == -1) {
                continue;
            }
            int temp = (int)tem + 1;
            TVar<Integer> z= new TVar<>();
            z.value=temp;
            tx.write(a,z);
            condition = tx.commit();
        }while (!condition);
        System.out.println(a.value);

    }
}

public class FirstSimpleTransaction {

    public void transaction(){
    }

    public static void main(String args[]){
        TVar<Integer> a = new TVar<>();
        a.value=3;
        for(int i=0;i<200;i++){
            MyThread temp = new MyThread(a);
            temp.start();
       }

    }
}

