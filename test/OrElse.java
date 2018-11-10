/*
*   Way orELse can be implemented using our mechanism
*/

class ElseThread extends Thread{
    TVar<Integer> x;

    public ElseThread(TVar x){
        this.x = x;
    }


    public void run() {
        boolean condition1 = false, condition2 = false;
        do {
            Transaction tx = new Transaction();
            //statement 1
            tx.read(x);
            condition1 = tx.commit();

            //statement 2
            if (!condition1) {
                Transaction t2 = new Transaction();
                TVar<Integer> temp = new TVar<>();
                temp.value = 4;
                tx.write(x, temp);
                condition2 = t2.commit();
                if(condition2)
                    break;
            }else
                break;
        } while (true);
    }
}
public class OrElse {

public static void main(String[] args){
    TVar<Integer> x = new TVar<>();
    x.value = 2;
    int n =10;
    ElseThread[] threads = new ElseThread[n];
    for(int i=0;i<n;i++){
        threads[i] = new ElseThread(x);
    }

    for(int i=0;i<n;i++)
        threads[i].start();
}

}
