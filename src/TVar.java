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
        x.lock = 1;
            this.value = (T) x.value;
            this.stamp = x.stamp;
            this.version = x.version;
            this.lock = x.lock;
             x.lock = 0;
    }
}