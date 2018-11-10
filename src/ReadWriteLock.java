import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadWriteLock {

        private int readers       = 0;
        private int writers       = 0;
        private int writeRequests = 0;
        public Set<Integer> Readers;
        public Set<Integer> Writers;
        private boolean locked;

        public ReadWriteLock(){
            Readers = new HashSet<>();
            Writers = new HashSet<>();
        }
        public synchronized  boolean isLocked(){
            return readers != 0 || writers != 0;
        }

        public synchronized int mode(){
            if(writers!=0)
                return 1;
            if(readers!=0)
                return 2;
            return 0;
        }

        public synchronized boolean isReader(int id){
            if(Readers.contains(id))
                return true;
            return false;
        }

        public synchronized boolean isWriter(int id){
        if(Writers.contains(id))
            return true;
        return false;
    }

        public synchronized void lockRead(int id) throws InterruptedException{
//            lockWrite(id);
            while(writers > 0 || writeRequests > 0){
                wait();
            }
            Readers.add(id);
            readers++;
        }

        public synchronized void unlockRead(int id) throws InterruptedException {
//                unlockWrite(id);
                        Readers.remove(id);
            readers--;
            notifyAll();
        }

        public synchronized void lockWrite(int id) throws InterruptedException{
            writeRequests++;

            while(readers > 0 || writers > 0){
                wait();
            }

            Writers.add(id);
            writeRequests--;
            writers++;
        }

        public synchronized void unlockWrite(int id) throws InterruptedException{
            writers--;
            Writers.remove(id);
            notifyAll();
        }
}

