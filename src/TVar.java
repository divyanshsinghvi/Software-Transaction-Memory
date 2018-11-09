import java.util.HashMap;
import java.util.List;


public class TVar<T> {
    T value;
    int stamp;
    int version;
    int lock;

    TVar(){
        value = null;
        stamp = 0;
    }

    TVar(TVar x){
        this.value = (T)x.value;
        this.stamp = x.stamp;
        this.version = x.version;
        this.lock = x.lock;

    }
}