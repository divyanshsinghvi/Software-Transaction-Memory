import java.util.HashMap;
import java.util.Map;

public class Transaction implements Runnable{
    int transactionId;
    int readStamp,writeStamp;
    HashMap<TVar,TVar> readMap, writeMap;
    static Clock globalClock = new Clock();

    Transaction(){
        readMap = new HashMap();
        writeMap = new HashMap();
        transactionId = getId();
        readStamp =  globalClock.getGlobalCount();
    }

    public TVar read(TVar x){
        if(writeMap.get(x)!=null){ // If local copy exist
            return writeMap.get(x);
        }
        else if(x.lock == 1 || x.stamp > readStamp ){ //If local copy does not exist check validity
            Abort();
        }
        else{
            TVar z = new TVar(x);
            readMap.put(x,z);
        }
    }

    public void write(TVar x,TVar y){
        if (writeMap.get(x)!=null){
            writeMap.put(x,y);
        }
        else{
            writeMap.put(x,y);
        }
    }

    public void commit(){
        for(Map.Entry<TVar,TVar> entry: writeMap.entrySet())
        {
            entry.getKey().lock = transactionId;
        }

        globalClock.incGlobalCount();
        writeStamp = globalClock.getGlobalCount();

        for(Map.Entry<TVar,TVar> entry: readMap.entrySet())
        {
            if(entry.getKey().lock != transactionId || entry.getKey().stamp > readStamp)
            {
                Abort();
            }
        }

        //Two points
    }


}
