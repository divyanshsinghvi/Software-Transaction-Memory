public class STMArray {
    private int length;
    public TVar array[];
    public STMArray(int length){
        this.length = length;
        array = new TVar[length];
        for(int i=0;i<length;i++){
            array[i] = new TVar();
            array[i].value = 0;
        }
    }

    synchronized public void addItem(Integer x,int k,Transaction tx){
            TVar item = new TVar();
            item.value = x;
            tx.write(array[k], item);
    }

    synchronized  public Integer getItem(int i,Transaction tx){
        Integer val = (Integer) tx.read(array[i]);
        return val;
    }

}
