class TestThread extends Thread {
    STMArray<Integer> a;
    int initializeValue = 0;
    int myFinalValue;
    int index =0;

    public TestThread (STMArray<Integer> a,int initializeValue) {
        this.a = a;
        this.initializeValue = initializeValue;

    }
    public TestThread (STMArray<Integer> a) {
        this.a = a;
    }
    public void run() {
        boolean condition = false;
        do {
            Transaction tx = new Transaction();
            myFinalValue = (int)a.getItem(4, tx);
            if(myFinalValue == -1)
                continue;
            myFinalValue +=1;
            a.addItem(myFinalValue , 4, tx);
            condition = tx.commit();
        }while(!condition);
        System.out.println(a.array[4].value);
    }

}



public class TestArray {

    public static void main(String args[]){
        STMArray<Integer> testArray = new STMArray<>(10);

        for(int i=0;i<10;i++) {
            Transaction tx = new Transaction();
            boolean condition;
            do {
                testArray.addItem(0, i, tx);
                condition = tx.commit();
            } while (!condition);
        }

        for(int i=0;i<200;i++){
            TestThread temp = new TestThread(testArray);
            temp.start();
        }


    }



}
