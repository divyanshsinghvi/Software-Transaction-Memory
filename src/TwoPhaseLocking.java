import com.sun.source.tree.SynchronizedTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class TwoPhaseLocking {
//    If x is locked by T in exclusive mode, then T reads new-version.
//– If x is locked by T in shared mode, then T reads old-version.
//– If x is locked by another transaction in exclusive mode, then T aborts, releases all its locks and discards all its new-versions.
//– If x is locked by another transaction in shared mode, or is unlocked, then T sets a shared lock on x and reads old-version.

    Integer myId;
    List<TVar2p> myShared;
    List<TVar2p> myExclusive;

    public TwoPhaseLocking(){
        myId = ThreadLocalRandom.current().nextInt();
        myShared = new ArrayList<>();
        myExclusive = new ArrayList<>();
    }

    public Integer Read(TVar2p x) throws InterruptedException {
        System.out.println("read by ");
        if (x.lock.mode() == 1 && x.lock.isWriter(myId))
            return x.newVersion;
        if (x.lock.mode() == 2 && x.lock.isReader(myId))
            return x.oldVersion;

        if (x.lock.mode() == 1) {
            for (int i = 0; i < myExclusive.size(); i++) {
                TVar2p y = myExclusive.get(i);
                y.lock.unlockWrite(myId);
            }
            myExclusive.clear();


            for (int i = 0; i < myShared.size(); i++) {
                TVar2p y = myShared.get(i);
                y.lock.unlockRead(myId);
            }
            myShared.clear();
        return -1;
        }

        x.lock.lockRead(myId);
        myShared.add(x);
        return x.oldVersion;

    }

//    When T tries to write x:
//            – If x is locked by T in exclusive mode, then T overwrites new- version.
//– If x is locked by T in shared mode and no one else shares the lock, then T upgrades to an exclusive lock and writes new-version.
//– If x is locked by another transaction in any mode, then T aborts, ...
//            – If x is unlocked, then T sets an exclusive lock on x and writes new version.

    public  boolean  Write(TVar2p x, int value) throws InterruptedException {
        System.out.println("written by");
        if(x.lock.mode() == 1 && x.lock.isWriter(myId) ){
            x.newVersion = value;
            return true;
        }
        if(x.lock.mode() == 2 && x.lock.isReader(myId) && x.lock.Readers.size()==1){
            x.lock.unlockRead(myId);
            x.lock.lockWrite(myId);
            myShared.remove(x);
            myExclusive.add(x);
            x.newVersion = value;
            return true;
        }

        if(x.lock.mode() !=0 ){
            for (int i = 0; i < myExclusive.size(); i++) {
                TVar2p y = myExclusive.get(i);
                y.lock.unlockWrite(myId);
            }
            myExclusive.clear();


            for (int i = 0; i < myShared.size(); i++) {
                TVar2p y = myShared.get(i);
                y.lock.unlockRead(myId);
            }
            myShared.clear();
            return false;
        }

        x.lock.lockWrite(myId);
        myExclusive.add(x);
        x.newVersion = value;
        return true;


    }

    public void commit() throws InterruptedException {
        for(int i=0;i<myExclusive.size();i++){
            TVar2p y = myExclusive.get(i);
            y.oldVersion = y.newVersion;
            y.lock.unlockWrite(myId);
        }
        myExclusive.clear();
        for (int i = 0; i < myShared.size(); i++) {
            TVar2p y = myShared.get(i);
            y.lock.unlockRead(myId);
        }
        myShared.clear();
    }


}
