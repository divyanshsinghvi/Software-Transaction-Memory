public class STMSkipList<T> {
    private SkipListBasic<TVar<T>> skipList;

    public STMSkipList(){
        skipList = new SkipListBasic();
    }

    public void putItem(String key, T value,Transaction tx){
        TVar<T> temp = new TVar<>();
        temp.value = value;
        if(!skipList.isPresent(key))
            skipList.put(key,temp);
        tx.write(tx.sCopy(skipList.get(key)),temp);
    }

    public T getItem(String key,Transaction tx){
        return (T)tx.read(skipList.get(key));
    }

    public void removeItem(){

    }

    public void prinList(){
        skipList.printHorizontal();

    }
}
