import java.util.Iterator;
import java.util.Random;

public class SkipList<T>  {
    TVar<STMSkipNode<T>> head, tail;
    TVar<Integer> height;
    TVar<Integer> nodesNo;
    public Random r;

    public SkipList() {
        TVar<STMSkipNode<T>> p1, p2;
        p1 = new TVar<>();
        p2 = new TVar<>();
        p1.value = new STMSkipNode<T>(STMSkipNode.negInf, null);
        p2.value = new STMSkipNode<T>(STMSkipNode.posInf, null);

        head = p1;
        tail = p2;

        p1.value.right = p2;
        p2.value.left = p1;
        height = new TVar<>();
        nodesNo = new TVar<>();
        nodesNo.value = 0;
        height.value = 0;
        r = new Random();

    }

    public STMSkipNode findEntry(String k, Transaction tx) {
        TVar<STMSkipNode<T>> p = new TVar<>();
        p.value = (STMSkipNode<T>) tx.read(head);
        while (true) {
//           Search RIGHT until you find a LARGER entry

            while (p.value.right.value.key.value != STMSkipNode.posInf && p.value.right.value.key.value.compareTo(k) <= 0) {
                p = p.value.right;         // Move to right
            }

            if (p.value.down != null) {
                p = p.value.down;          // Go downwards
            } else {
                break;       // We reached the LOWEST level... Exit...
            }
        }
        return (p.value);         // Note: p.key <= k
    }
}

//    public Object get (String k)
//    {
//        STMSkipNode p;
//        p = findEntry(k);
//        if ( k.equals( p.getKey() ) )
//            return(p.value);
//        else
//            return(null);
//    }
//
//
//    public T put (String k, T v)
//    {
//        STMSkipNode p, q;
//        int i;
//        p = findEntry(k);
//
//        if ( k.equals( p.getKey() ) )
//        {
//            T old = (T) p.value;
//            p.value = v;
//            return(old);
//        }
//
//        q = new STMSkipNode(k, v);
//        q.left = p;
//        q.right = p.right;
//        p.right.left = q;
//        p.right = q;
//        i = 0;                   // Current level = 0
//        while ( r.nextDouble() < 0.5 )
//        {
//
//            if ( i >= height )
//            {
//                STMSkipNode p1, p2;
//                height = height + 1;
//                p1 = new STMSkipNode(STMSkipNode.negInf,null);
//                p2 = new STMSkipNode(STMSkipNode.posInf,null);
//                p1.right = p2;
//                p1.down  = head;
//                p2.left = p1;
//                p2.down = tail;
//
//                head.up = p1;
//                tail.up = p2;
//
//                head = p1;
//                tail = p2;
//            }
//
//
//            while ( p.up == null )
//            {
//                p = p.left;
//            }
//            p = p.up;
//
//            STMSkipNode e;
//
//            e = new STMSkipNode(k, null);  // Don't need the value...
//
//   	/* ---------------------------------------
//   	   Initialize links of e
//   	   --------------------------------------- */
//            e.left = p;
//            e.right = p.right;
//            e.down = q;
//
//   	/* ---------------------------------------
//   	   Change the neighboring links..
//   	   --------------------------------------- */
//            p.right.left = e;
//            p.right = e;
//            q.up = e;
//
//            q = e;		// Set q up for the next iteration
//
//            i = i + 1;	// Current level increased by 1
//
//        }
//
//        nodesNo = nodesNo + 1;
//
//        return(null);   // No old value
//    }
//
//    public void delete(String key){
//        STMSkipNode p = findEntry(key);
//
//        if (p.key != key)
//            return;     // Not found, don't remove
//
//   /* ------------------------------------------------------------
//      We are at level 0
//      Travel up the tower and link the left and right neighbors
//      ------------------------------------------------------------ */
//        while ( p != null )
//        {
//            p.left.right = p.right;
//            p.right.left = p.left;
//            p = p.up;
//        }
//    }
//
//    public void printList(){
//
//    }
//
//    public void printHorizontal()
//    {
//        String s = "";
//        int i;
//
//        STMSkipNode p;
//
//     /* ----------------------------------
//	Record the position of each entry
//	---------------------------------- */
//        p = head;
//
//        while ( p.down != null )
//        {
//            p = p.down;
//        }
//
//        i = 0;
//        while ( p != null )
//        {
//            p.pos = i++;
//            p = p.right;
//        }
//
//     /* -------------------
//	Print...
//	------------------- */
//        p = head;
//
//        while ( p != null )
//        {
//            s = getOneRow( p );
//            System.out.println(s);
//
//            p = p.down;
//        }
//    }
//
//    public String getOneRow( STMSkipNode p )
//    {
//        String s;
//        int a, b, i;
//
//        a = 0;
//
//        s = "" + p.key;
//        p = p.right;
//
//
//        while ( p != null )
//        {
//            STMSkipNode q;
//
//            q = p;
//            while (q.down != null)
//                q = q.down;
//            b = q.pos;
//
//            s = s + " <-";
//
//
//            for (i = a+1; i < b; i++)
//                s = s + "--------";
//
//            s = s + "> " + p.key;
//
//            a = b;
//
//            p = p.right;
//        }
//
//        return(s);
//    }
//
//
//    @Override
//    public Iterator iterator() {
//        return null;
//    }
//}
