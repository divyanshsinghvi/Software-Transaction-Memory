public class STMArray<T> {
    public int length;
    public TVar array[];
    public STMArray(int length){
        this.length = length;
        array = new TVar[length];
        for(int i=0;i<length;i++){
            array[i] = new TVar();
        }
    }

    public void addItem(T x,int k,Transaction tx){
            TVar item = new TVar();
            item.value = x;
            tx.write(array[k], item);
    }

    public Object getItem(int i,Transaction tx){
        return tx.read(array[i]);
    }

}
