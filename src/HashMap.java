import java.util.Map;

public class HashMap<T> {

    private java.util.HashMap<String, TVar<T>> hashMap;

    public HashMap(){
            hashMap = new java.util.HashMap<>();
    }

    public T getItem(String key, Transaction tx){
        if(hashMap.containsKey(key)){
            return (T)tx.read(hashMap.get(key));
        }else
            return null;

    }

    public void  putItem(String key, T value , Transaction tx){
        TVar<T> item = new TVar<T>();
        item.value = value;
        hashMap.putIfAbsent(key,item);
        tx.write(hashMap.get(key),item);
    }

    public void printMap(){
        for(Map.Entry<String,TVar<T>> k : hashMap.entrySet()){
            System.out.println(k.getKey()+" "+ ((Integer) k.getValue().value));
        }
    }






}
