import java.util.concurrent.atomic.AtomicInteger;

public class TVar2p {
    int newVersion;
    int oldVersion;
    ReadWriteLock lock; // ex=1,shared =2

    TVar2p(){
        lock = new ReadWriteLock();
        newVersion = 0;
        oldVersion = 0;
    }
}
