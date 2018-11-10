import java.util.HashMap;
import java.util.Map;

public class Transaction {
    static TransactionID tid = new TransactionID();
    int transactionId;
    int readStamp,writeStamp;
    HashMap<TVar,TVar> readMap, writeMap;
    HashMap<TVar,TVar> globalLocal;
    static Clock globalClock = new Clock();

    Transaction(){
        readMap = new HashMap();
        writeMap = new HashMap();
        globalLocal = new HashMap();

        // transactionId = getId();
        transactionId = tid.getGlobalCount();
        tid.incGlobalCount();
        readStamp =  globalClock.getGlobalCount();
    }

    public Object read(TVar x){
        if(writeMap.get(x)!=null){ // If local copy exist
            return writeMap.get(x).value;
        }
        else if(x.lock != 0 || x.stamp > readStamp ){ //If local copy does not exist check validity
            Abort();
        }
        else{
            TVar z = new TVar(x);
            readMap.put(x,z);
        }
        return x.value;
    }

    public TVar sCopy(TVar x){
        if(writeMap.get(x)!=null){ // If local copy exist
            return writeMap.get(x);
        }
        else if(x.lock != 0 || x.stamp > readStamp ){ //If local copy does not exist check validity
            Abort();
        }
        else{
            TVar z = new TVar(x);
            readMap.put(x,z);
        }
        return x;
    }

    public void write(TVar x,TVar y){
//        if (writeMap.get(x)!=null){
            writeMap.put(x,y);
//        }
//        else{
//            writeMap.put(x,y);
//        }
    }

    public boolean commit( ){
            for (Map.Entry<TVar, TVar> entry : writeMap.entrySet()) {
                if (entry.getKey().lock == 0)
                    entry.getKey().lock = transactionId;
                else {
                    for (Map.Entry<TVar, TVar> entry1 : writeMap.entrySet()) {
                        if (entry1.getKey().lock == transactionId) {
                            entry1.getKey().lock = 0;
                        }
                    }
                    return false;
                }
            }

            globalClock.incGlobalCount();
            writeStamp = globalClock.getGlobalCount();
            for (Map.Entry<TVar, TVar> entry : readMap.entrySet()) {
                //System.out.println("Lock and transactional Id "+readStamp+" "+entry.getKey().stamp);

                if (entry.getKey().lock != transactionId || entry.getKey().stamp > readStamp) {
                    for (Map.Entry<TVar, TVar> entry1 : writeMap.entrySet()) {
                        if (entry1.getKey().lock == transactionId) {
                            entry1.getKey().lock = 0;
                        }
                    }
                    return false;
                    //return Abort();
                }
            }
            for (Map.Entry<TVar, TVar> entry : writeMap.entrySet()) {
                entry.getKey().value = entry.getValue().value;
            }

            for (Map.Entry<TVar, TVar> entry : writeMap.entrySet()) {
                entry.getKey().stamp = writeStamp;
                entry.getKey().lock = 0;
            }
            return true;
        }

    public boolean Abort(){
        return false;
    }

}
