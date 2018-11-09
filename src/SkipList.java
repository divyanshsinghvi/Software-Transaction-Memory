import java.util.Iterator;
import java.util.Random;

public class SkipList<T> {
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
        tx.read(head);
        TVar<STMSkipNode<T>> p = head;
        while (true) {
//           Search RIGHT until you find a LARGER entry
            while (((STMSkipNode) tx.read(p.value.right)).key.value != STMSkipNode.posInf && ((String) ((STMSkipNode) tx.read(p.value.right)).key.value).compareTo(k) <= 0) {
                p = p.value.right;         // Move to right
            }

            if (tx.read(p.value.down) != null) {
                p = p.value.down;          // Go downwards
            } else {
                break;       // We reached the LOWEST level... Exit...
            }
        }
        return (p.value);         // Note: p.key <= k
    }

    public Object get(String k,Transaction tx) {
        TVar<STMSkipNode<T>> p = new TVar<>();
        p.value = findEntry(k,tx);
        if (k.equals(p.value.getKey(tx)))
            return (p.value);
        else
            return (null);
    }

    public T put (String k, T v,Transaction tx)
    {
        TVar<STMSkipNode<T>> p = new TVar<>();
        TVar<STMSkipNode<T>> q = new TVar<>();
        int i;
        p.value = findEntry(k,tx);

        if ( k.equals( p.value.getKey(tx) ) )
        {
            T old = (T) p.value;
            TVar<T> V = new TVar<>();
            V.value = v;
            tx.write(p,V);
            return(old);
        }

        q.value = new STMSkipNode(k, v);
        tx.write(q.value.left,p);
        tx.write(q.value.right, ((STMSkipNode) tx.read(p)).right);
        tx.write(((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).left,q);
        tx.write(((STMSkipNode) tx.read(p)).right,q);
        i = 0;                   // Current level = 0
        while ( r.nextDouble() < 0.5 )
        {

            if ( i >= ((Integer) tx.read(height)))
            {
                STMSkipNode p1, p2;
                TVar<Integer> hz = new TVar<>();
                hz.value = height.value + 1;

                tx.write(height, hz);
                p1 = new STMSkipNode(STMSkipNode.negInf,null);
                p2 = new STMSkipNode(STMSkipNode.posInf,null);
                p1.right.value = p2;
                p1.down.value  = tx.read(head);
                p2.left.value = p1;
                p2.down.value = tx.read(tail);

                TVar<STMSkipNode<T>> z1 = new TVar<>();
                z1.value = p1;
                tx.write(head.value.up,z1);
                TVar<STMSkipNode<T>> z2 = new TVar<>();
                z2.value = p2;
                tx.write(tail.value.up, z2);

                tx.write(head,z1);
                tail = p2;
            }


            while ( p.up == null )
            {
                p = p.left;
            }
            p = p.up;

            STMSkipNode e;

            e = new STMSkipNode(k, null);  // Don't need the value...

   	/* ---------------------------------------
   	   Initialize links of e
   	   --------------------------------------- */
            e.left = p;
            e.right = p.right;
            e.down = q;

   	/* ---------------------------------------
   	   Change the neighboring links..
   	   --------------------------------------- */
            p.right.left = e;
            p.right = e;
            q.up = e;

            q = e;		// Set q up for the next iteration

            i = i + 1;	// Current level increased by 1

        }

        nodesNo = nodesNo + 1;

        return(null);   // No old value
    }

    public void delete(String key){
        STMSkipNode p = findEntry(key);

        if (p.key != key)
            return;     // Not found, don't remove

   /* ------------------------------------------------------------
      We are at level 0
      Travel up the tower and link the left and right neighbors
      ------------------------------------------------------------ */
        while ( p != null )
        {
            p.left.right = p.right;
            p.right.left = p.left;
            p = p.up;
        }
    }
}
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
