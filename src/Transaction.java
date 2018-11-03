import java.util.HashMap;
import java.util.Map;

public class Transaction {
    int transactionId;
    int readStamp,writeStamp;
    HashMap<TVar,TVar> readMap, writeMap;

    Transaction(){
        readMap = new HashMap();
        writeMap = new HashMap();
        readStamp =  getGlobalCount();
        transactionId = getId();
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
        for(Map.Entry<TVar,TVar> entry:writeMap) {
            entry.getKey().lock = 1;
        }
        incGlobalClock();
        writeStamp = getGlobalCount();
        for()
        {
            if(map[i].lock != currentTransaction || map[i].stamp > readStamp)
            {
                Abort();
            }
        }

        //Two points
    }


}
