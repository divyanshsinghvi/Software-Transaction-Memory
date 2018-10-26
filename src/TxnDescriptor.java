import java.util.HashSet;
import java.util.Stack;

public class TxnDescriptor {
    private Stack<Ref> checkPoint;

    public TxnDescriptor(){
        checkPoint = new Stack<>();
    }

}
