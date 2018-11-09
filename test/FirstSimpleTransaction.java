class MyThread extends Thread {
    TVar<Integer> a;
    public MyThread (TVar<Integer> a) {
        this.a = a;
    }

    public void run() {
        //TVar<Integer> a = new TVar<>();
        //a.value = 3;
        boolean condition=true;
        do {
            Transaction tx  = new Transaction();
            int temp = (int)tx.read(a) + 1;
            TVar<Integer> z= new TVar<>(a);
            z.value=temp;
            tx.write(a,z);
           // System.out.println(a.value);
            condition = tx.commit();
           // System.out.println(a.value);

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
        for(int i=0;i<20;i++){
            MyThread temp = new MyThread(a);
            temp.start();
       }

    }
}

