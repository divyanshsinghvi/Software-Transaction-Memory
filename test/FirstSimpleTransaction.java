class MyThread extends Thread {
    TVar<Integer> a;
    public MyThread (TVar<Integer> a) {
        this.a = a;
    }

    public void run() {
        Transaction tx  = new Transaction();
        //TVar<Integer> a = new TVar<>();
        //a.value = 3;
        do {
            int temp = (int)tx.read(a) + 1;
            TVar<Integer> z= new TVar<>(a);
            z.value=temp;
            tx.write(a,z);
            System.out.println(a.value+" "+a.stamp);
        }while (!tx.commit());
        System.out.println(a.value);

    }
}

public class FirstSimpleTransaction {

    public void transaction(){
    }

    public static void main(String args[]){
        TVar<Integer> a = new TVar<>();
        a.value=3;
        for(int i=0;i<3;i++){
            MyThread temp = new MyThread(a);
            temp.start();
       }

    }
}

