public class FirstSimpleTransaction {
    public static void main(String args[]){
        Transaction tx  = new Transaction();
        TVar<Integer> a = new TVar<>();
        a.value = 3;
        do {
            a.value= (int)a.value + 1;
        }while (!tx.commit());
    }

}
