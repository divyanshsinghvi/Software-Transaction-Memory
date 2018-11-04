import java.util.HashMap;
import java.util.List;


public class TVar<T> {
    Object value;
    int stamp;
    int version;
    int lock;

    TVar(){
        stamp = 0;
    }

    TVar(TVar x){
        this.value = x.value;
        this.stamp = x.stamp;
        this.version = x.version;
        this.lock = x.lock;

    }
}