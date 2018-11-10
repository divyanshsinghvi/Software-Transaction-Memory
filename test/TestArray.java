class TestThread extends Thread {
    STMArray a;
    int initializeValue = 0;
    int myFinalValue=0;
    int index =0;
    public Object commitBool;

    public TestThread (STMArray a,int initializeValue) {
        this.a = a;
        this.initializeValue = initializeValue;

    }
    public TestThread (STMArray a,int initializeValue,int index) {
        this.a = a;
        this.initializeValue = initializeValue;
        this.index = index;

    }

    public void run() {
        boolean condition = true;
        do {
            Transaction tx = new Transaction();
            myFinalValue = a.getItem(4, tx);
            a.addItem(myFinalValue + 1, 4, tx);
            condition = tx.commit();
        }while(!condition);
       System.out.println(a.array[4].value);
    }

}



public class TestArray {

    public static void main(String args[]){
        STMArray testArray = new STMArray(10);



        for(int i=0;i<200;i++){
            TestThread temp = new TestThread(testArray, -1);
            temp.start();
        }

//        for(int i=0;i<10;i++){
//            Transaction tx = new Transaction();
//            System.out.println(testArray.getItem(i,tx));
//        }
    }



}
