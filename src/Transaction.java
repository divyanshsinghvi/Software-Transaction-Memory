import java.util.HashSet;

public class Transaction {
    int readStamp,writeStamp;
    HashSet<> readSet, writeSet;

    Transaction(){
        readSet = new HashSet();
        writeSet = new HashSet();
        readStamp =  getGlobalCount();
    }

    public void read(TVar x){
        if(x in writeSet){
            return writeSet[x];
        }
        else if(x.lock is locked || x.stamp > readStamp ){
            Abort();
        }
        else{
            readSet[x] = x.version;//version check
        }
    }

    public void write(TVar x){
        if (x in writeSet){
            writeSet[x] = x.version;
        }
        else{
            writeSet[x] = x.version;
        }
    }

    public void commit(){
        for(i in writeSet)
        {
            i.lock;
        }
        incGlobalClock();
        writeStamp = getGlobalCount();
        for(i in readSet)
        {
            if(map[i].lock != currentTransaction || map[i].stamp > readStamp)
            {
                Abort();
            }
        }

        //Two points
    }


}
