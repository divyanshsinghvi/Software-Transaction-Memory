
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;


class MyQueue extends Thread {
    TVar<Queue<Integer>> q1;
    TVar<Queue<Integer>> q2;
    public MyQueue (TVar<Queue<Integer>> a,TVar<Queue<Integer>> b) {
        this.q1 = a;
        this.q2 = b;
        //System.out.println(a.value);
    }

    public void printQueue(){
        int x = q1.value.size() + q2.value.size();
        System.out.println(x);
    }

    public void run() {
        boolean condition=false;
        do {
            Transaction tx = new Transaction();
                Object d = tx.read(q1);
                if (d instanceof Integer)
                    continue;
            Queue<Integer> temp = (Queue<Integer>)d;// tx.read(q1);
            int head;
            try {
                head = temp.remove();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
                continue;
            }

            TVar<Queue<Integer>> tempQueue = new TVar<>(q2);

            tempQueue.value.add(head);
            tx.write(q2,tempQueue);
            condition = tx.commit();
        }while (!condition);
        printQueue();
        //printQueue(this.q2.value);

    }
}

public class Composability {
    public static void main(String[] args){
        TVar<Queue<Integer>> queue1 = new TVar<>();
        TVar<Queue<Integer>> queue2 = new TVar<>();
        queue1.value = new LinkedList<>();
        queue2.value = new LinkedList<>();
        MyQueue[] threads = new MyQueue[200];
        queue1.value.add(3);
        queue2.value.add(4);
        for(int i=0;i<2;i++){
            threads[i] = new MyQueue(queue1,queue2);
        }

        for(int i=0;i<2;i++){
            threads[i].start();
        }


    }



}
