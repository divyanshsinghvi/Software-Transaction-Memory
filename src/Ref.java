enum State{
    SHARED,EXCLUSIVE;
}
public final class Ref<T>{
     //   RefVersion<T, Long> snaps;
         State state;

         public Ref(T value){
    //      snaps = RefVersion.get(value, 0L);
            state = State.SHARED;
        }



}
