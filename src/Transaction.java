import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Collections;
public class Transaction {
    static TransactionID tid = new TransactionID();
    int transactionId;
    int readStamp,writeStamp;
    Map<TVar,TVar> readMap, writeMap;
    Map<TVar,TVar> globalLocal;
    static AtomicInteger globalClock = new AtomicInteger(2);
    static AtomicInteger globaltid = new AtomicInteger(0);
    Transaction(){
        readMap = Collections.synchronizedMap(new HashMap<>());
        writeMap = Collections.synchronizedMap(new HashMap<>());
        globalLocal = new ConcurrentHashMap<>();
        transactionId = globaltid.incrementAndGet();
        readStamp = globalClock.intValue();
    }

    public Object read(TVar x){

          if (writeMap.get(x) != null) { // If local copy exist
              return writeMap.get(x).value;
          }
          else{
                int v1 = x.stamp.intValue();
                Object result = x.value;
                int v2 = x.stamp.intValue();
                if(v1!=v2 || v1>readStamp || (x.lock.intValue()!=0 && x.lock.intValue()!=transactionId)){
                    return Abort();
                }
                TVar z = new TVar(x);
                readMap.put(x,z);
                v2 = x.stamp.intValue();
                if(v1!=v2)
                {
                  readMap.remove(x,z);
                  return Abort();
                }
                return result;
          }

//          else if (x.lock.intValue() != 0 || x.stamp.intValue() > readStamp) { //If local copy does not exist check validity
//              return Abort();
//          } else {
//              TVar z = new TVar(x);
//              readMap.put(x, z);
//          }
//          return x.value;
    }

    public TVar sCopy(TVar x){
        if(writeMap.get(x)!=null){ // If local copy exist
            return writeMap.get(x);
        }
        else if(x.lock.intValue() != 0 || x.stamp.intValue() > readStamp ){ //If local copy does not exist check validity
            Abort();
        }
        else{
            TVar z = new TVar(x);
            readMap.put(x,z);
        }
        return x;
    }

    public void write(TVar x,TVar y){
            writeMap.put(x,y);
    }

    public boolean commit(){
        for(Map.Entry<TVar,TVar> entry: writeMap.entrySet())
        {
            if(!entry.getKey().lock.compareAndSet(0,transactionId) && !(entry.getKey().lock.intValue()==transactionId))
            {
                for(Map.Entry<TVar,TVar> entry1: writeMap.entrySet())
                {
                    entry1.getKey().lock.compareAndSet(transactionId,0);
                }
                return false;
            }
        }

        writeStamp=globalClock.incrementAndGet();
        for(Map.Entry<TVar,TVar> entry: readMap.entrySet())
        {
            if((entry.getKey().lock.intValue() != 0 && entry.getKey().lock.intValue() != transactionId) || entry.getKey().stamp.intValue() > readStamp)
            {
                for(Map.Entry<TVar,TVar> entry1: writeMap.entrySet())
                {
                    entry1.getKey().lock.set(0);
                    //entry1.getKey().lock.compareAndSet(transactionId,0);
                }
                return false;
            }
        }
        for(Map.Entry<TVar,TVar> entry: writeMap.entrySet()) {
            entry.getKey().value = entry.getValue().value;
        }

        for(Map.Entry<TVar,TVar> entry: writeMap.entrySet())
        {
//            if(writeStamp > readStamp)
            entry.getKey().stamp.set(writeStamp);
             entry.getKey().lock.set(0);
        }
            return true;

    }
    public int Abort(){
        return -1;
    }

}
