import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TVar<T> {
    T value;
    AtomicInteger stamp;
    int version;
    AtomicInteger lock;

    TVar(){
        value = null;
        stamp = new AtomicInteger(0);
        lock = new AtomicInteger(0);
    }

    TVar(TVar x){
            value = (T) x.value;
            stamp = new AtomicInteger(x.stamp.intValue());
            version = x.version;
            lock = new AtomicInteger(0);
    }
}