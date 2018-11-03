import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TransactionID {
    int count;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();
    Lock readLock = lock.readLock();
    TransactionID(){
        count = 1;
    }

    public int getGlobalCount(){
        try {
            readLock.lock();
            return count;
        } finally {
            readLock.unlock();
        }
    }

    public void incGlobalCount(){
        try {
            writeLock.lock();
            count++;
        } finally {
            writeLock.unlock();
        }
    }


}
