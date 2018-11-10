import java.util.Random;
/*
* Buggy Skip List Implementation*
*/
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
        TVar<STMSkipNode<T>> p = tx.sCopy(head);
        while (true) {
//           Search RIGHT until you find a LARGER entry
            while (((STMSkipNode) tx.read(p.value.right)).getKey(tx) != STMSkipNode.posInf && ((String) ((STMSkipNode) tx.read(p.value.right)).getKey(tx)).compareTo(k) <= 0) {
                p = tx.sCopy(p.value.right);         // Move to right
            }

            if (tx.read(p.value.down) != null) {
                p = tx.sCopy(p.value.down);          // Go downwards
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
            T old = (T) p.value.val;
            TVar<T>Z = new TVar();
            Z.value = v;
            p.value.val = Z;
            return(old);
        }

        q.value = new STMSkipNode(k, v);
        q.value.left = tx.sCopy(p);
        q.value.right = tx.sCopy(((STMSkipNode) tx.read(p)).right);
        //((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).left = q;
        //((STMSkipNode) tx.read(p)).right= q;
        TVar<STMSkipNode<T>> y = new TVar<>();
        TVar<STMSkipNode<T>> z = new TVar<>();

        y.value = new STMSkipNode<T>(((String) ((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).key.value), ((T) ((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).getValue(tx)));
        y.value.right = ((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).right;
        y.value.down = ((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).down;
        y.value.up = ((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).up;
        y.value.left = q;
        y.stamp = tx.sCopy(((STMSkipNode) tx.sCopy(p).value).right).stamp;
        y.lock = tx.sCopy(p).lock;

        z.value = new STMSkipNode<T>(((String) ((STMSkipNode) tx.read(p)).key.value), ((T) ((STMSkipNode) tx.read(p)).getValue(tx)));
        z.value.left = ((STMSkipNode) tx.read(p)).left;
        z.value.down = ((STMSkipNode) tx.read(p)).down;
        z.value.up = ((STMSkipNode) tx.read(p)).up;
        z.value.right = q;
        z.stamp = tx.sCopy(p).stamp;
        z.lock = tx.sCopy(p).lock;
        tx.write((((STMSkipNode) tx.read(p)).right),y);
        tx.write(p,z);
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
                head.value.up = z1;
                TVar<STMSkipNode<T>> z2 = new TVar<>();
                z2.value = p2;
                tail.value.up = z2;

                head=z1;
                tail=z2;
            }


            while ( tx.read(((STMSkipNode) tx.read(p)).up )== null )
            {
                tx.write(p, tx.sCopy(((STMSkipNode) tx.sCopy(p).value).left));
            }
            tx.write(p, tx.sCopy(((STMSkipNode) tx.sCopy(p).value).up));

            STMSkipNode e;

            e = new STMSkipNode(k, null);  // Don't need the value...

   	/* ---------------------------------------
   	   Initialize links of e
   	   --------------------------------------- */
            e.left.value = tx.read(p);
            e.right.value = tx.read(((STMSkipNode<T>) tx.read(p)).right);
            e.down.value = tx.read(q);

   	/* ---------------------------------------
   	   Change the neighboring links..
   	   --------------------------------------- */
   	        TVar<STMSkipNode<T>> E = new TVar<>();
   	        E.value = e;
            tx.write(((STMSkipNode) tx.read(((STMSkipNode) tx.read(p)).right)).left,E);
            tx.write(((STMSkipNode) tx.read(p)).right,E);
            tx.write(((STMSkipNode) tx.read(q)).up, E);

            tx.write(q,E);		// Set q up for the next iteration

            i = i + 1;	// Current level increased by 1

        }

        TVar<Integer>kl = new TVar<>(tx.sCopy(nodesNo));
        kl.value = kl.value + 1;
        tx.write(nodesNo ,kl);

        return(null);   // No old value
    }

    public void delete(String key,Transaction tx){
        STMSkipNode p = findEntry(key,tx);
        if (p.key.value != key)
            return;     // Not found, don't remove

        while ( p != null )
        {
            tx.write(((STMSkipNode) tx.read(p.left)).right , p.right);
            tx.write(((STMSkipNode) tx.read(p.right)).left,p.left);
            p = ((STMSkipNode) p.up.value);
        }
    }


    public void printList(){

    }

    public void printHorizontal()
    {
        String s = "";
        int i;

        STMSkipNode p;

     /* ----------------------------------
	Record the position of each entry
	---------------------------------- */
        p = head.value;

        while ( p.down != null )
        {
            p = ((STMSkipNode) p.down.value);
        }

        i = 0;
        while ( p != null )
        {
            p.pos.value = i++;
            p = ((STMSkipNode) p.right.value);
        }

     /* -------------------
	Print...
	------------------- */
        p = head.value;

        while ( p != null )
        {
            s = getOneRow( p );
            System.out.println(s);

            p = ((STMSkipNode) p.down.value);
        }
    }

    public String getOneRow( STMSkipNode p )
    {
        String s;
        int a, b, i;

        a = 0;

        s = "" + p.key.value;
        p = ((STMSkipNode) p.right.value);


        while ( p != null )
        {
            STMSkipNode q;

            q = p;
            while (q.down != null)
                q = ((STMSkipNode) q.down.value);
            b = ((Integer) q.pos.value);
            s = s + " <-";


            for (i = a+1; i < b; i++)
                s = s + "--------";

            s = s + "> " + p.key.value;

            a = b;

            p = ((STMSkipNode) p.right.value);
        }

        return(s);
    }

}
