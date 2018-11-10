    class MyThreads extends Thread {
        TVar2p a;
        public MyThreads (TVar2p a) {
            this.a = a;
        }

        public void run() {
            //TVar<Integer> a = new TVar<>();
            //a.value = 3;
            boolean condition = false;

                 TwoPhaseLocking tx = new TwoPhaseLocking();
                 int temp = -1;
                 //tx.Write(a,3);
                 while(!condition){
                 try {

                     while(!condition) {
                         while(temp+1==0) {
                             temp = tx.Read(a);
                         }
                         condition = tx.Write(a, temp + 100);
                     }
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 try {
                     tx.commit();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 }
             System.out.println(a.oldVersion);
                // System.out.println(a.value);


        }
    }

    public class PhaseLockingTest {

        public static void main(String args[]) throws InterruptedException {
            TVar2p a = new TVar2p();
            a.oldVersion=3;
            MyThreads[] threads = new MyThreads[10];
            int n = 5;
            for(int i=0;i<n;i++){
                 threads[i] = new MyThreads(a);
            }

            for(int i=0;i<n;i++) {
                threads[i].start();
            //    threads[i].join();
             //   System.out.println(a.oldVersion);
            }

        }
}



