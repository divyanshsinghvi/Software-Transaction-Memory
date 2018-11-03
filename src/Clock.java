import java.math.BigInteger;

public class Clock {
    int count;
    Clock(){
        count = 0;
    }

    public int getGlobalCount(){
        return count;
    }

    public void incGlobalCount(){
        count++;
    }


}
