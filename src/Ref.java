
public final class Ref<T>{
        RefVersion<T, Long> snaps;

        public Ref(T value){
                snaps = RefVersion.get(value, 0L);
        }
}