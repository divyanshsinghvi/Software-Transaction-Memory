public final class STMSkipNode<T>   {
    public TVar<String> key;
    public TVar<T> val;
    public TVar<Integer> pos;

    public TVar<STMSkipNode<T>> up;       // up link
    public TVar<STMSkipNode<T>> down;     // down link
    public TVar<STMSkipNode<T>> left;     // left link
    public TVar<STMSkipNode<T>> right;    // right link

    public static String negInf = "-oo";  // -inf key value
    public static String posInf = "+oo";  // +inf key value


    public STMSkipNode(String key, T value){
        this.key = new TVar<>();
        this.val = new TVar<>();
        this.key.value = key;
        this.val.value = value;


        up= new TVar<>();
        down=  new TVar<>();
        left= new TVar<>();
        right = new TVar<>();

//        up.value = new STMSkipNode<>(null, null);
//        down.value = new STMSkipNode<>(null,null);
//        right.value =new STMSkipNode<>(null,null);
//        left.value = new STMSkipNode<>(null,null);

    }

    public T getValue(Transaction tx)
    {
        return (T)tx.read(val);
    }

    public String getKey(Transaction tx)
    {
        return (String)tx.read(key);
    }

    public T setValue(T valz, Transaction tx) {
        TVar oldValue = new TVar();
        oldValue.value = (T) tx.read(val);
        TVar newValue = new TVar();
        newValue.value = valz;
        tx.write(val, newValue);
        return (T) oldValue.value;
    }

//    public boolean equals(Object o,Transaction tx)
//    {
//        TVar<STMSkipNode> ent = new TVar<>();
//        try {
//            ent.value = o;    // Test if o is a SkipListEntry...
//        }
//        catch (ClassCastException ex) {
//            return false;
//        }
//        return (ent.value == tx.read(key)) && (ent.value == val);
//    }

    public String toString(Transaction tx)
    {
        return "(" + tx.read(key) + "," + tx.read(val) + ")";
    }
}