import java.util.*;

/*Implemntation of cheugn/Courses
* */

public class SkipListBasic<T>
{
    SkipListEntry<T> head;
    SkipListEntry<T> tail;
    int noEntry;
    int h;
    Random r;
    SkipListBasic()
    {
        SkipListEntry p1, p2;

        p1 = new SkipListEntry(SkipListEntry.negInf, null);
        p2 = new SkipListEntry(SkipListEntry.posInf, null);

        head = p1;
        tail = p2;

        p1.right = p2;
        p2.left = p1;

        noEntry = 0;
        h = 0;

        r = new Random();
    }

    public boolean isPresent(String k){
        SkipListEntry p = head;
        while ( true )
        {
            while ( p.right.key != SkipListEntry.posInf && p.right.key.compareTo(k) <= 0 ) {
                p = p.right;
            }

	        if ( p.down != null )
            {
                p = p.down;
            }
            else
                break;
        }
        if(p.key.compareTo(k) == 0)
            return true;
        return false;
    }

    public SkipListEntry findEntry(String k)
    {
        SkipListEntry p;
        p = head;
        while ( true )
        {
            while ( p.right.key != SkipListEntry.posInf && p.right.key.compareTo(k) <= 0 )
            {
                p = p.right;
            }

            if ( p.down != null )
            {
                p = p.down;
            }
            else
                break;
        }

        return(p);
    }


    public T get (String k)
    {
        SkipListEntry p;
        p = findEntry(k);
        if ( k.equals( p.getKey() ) )
            return(T)(p.value);
        else
            return(null);
    }

    public synchronized T put (String k, T v)
    {
        SkipListEntry p, q;
        int       i;

        p = findEntry(k);
        if ( k.equals( p.getKey() ) )
        {
            T old = (T)p.value;
            p.value = v;
            return (old);
        }

        q = new SkipListEntry(k, v);
        q.left = p;
        q.right = p.right;
        p.right.left = q;
        p.right = q;

        i = 0;
        while ( r.nextDouble() < 0.5 )
        {
            if ( i >= h )
            {
                SkipListEntry p1, p2;
                h = h + 1;
                p1 = new SkipListEntry(SkipListEntry.negInf,null);
                p2 = new SkipListEntry(SkipListEntry.posInf,null);
                p1.right = p2;
                p1.down  = head;
                p2.left = p1;
                p2.down = tail;
                head.up = p1;
                tail.up = p2;
                head = p1;
                tail = p2;
            }

            while ( p.up == null ) {
                p = p.left;
            }
            p = p.up;
            SkipListEntry e;
            e = new SkipListEntry(k, null);
            e.left = p;
            e.right = p.right;
            e.down = q;
            p.right.left = e;
            p.right = e;
            q.up = e;
            q = e;
            i = i + 1;
        }
        noEntry = noEntry + 1;
        return(null);
    }

    public T remove (String key)
    {
        SkipListEntry p = findEntry(key);

        if (!p.key.equals(key))
            return(null);

        while ( p != null )
        {
            p.left.right = p.right;
            p.right.left = p.left;
            p = p.up;
        }
        return(null);
    }

    public void printHorizontal()
    {
        String s = "";
        int i;
        SkipListEntry p;
        p = head;
        while ( p.down != null )
        {
            p = p.down;
        }
        i = 0;
        while ( p != null )
        {
            p.pos = i++;
            p = p.right;
        }
        p = head;
        while ( p != null )
        {
            s = getOneRow( p );
            System.out.println(s);
            p = p.down;
        }
    }

    public String getOneRow( SkipListEntry p )
    {
        String s;
        int a, b, i;
        a = 0;
        s = "" + p.key;
        p = p.right;

        while ( p != null )
        {
            SkipListEntry q;
            q = p;
            while (q.down != null)
                q = q.down;
            b = q.pos;
            s = s + " <-";
            for (i = a+1; i < b; i++)
                s = s + "--------";
            if(p.value == null)
                s = s + "> " + p.key;
            else
                s = s + "> " + p.key + " "+((TVar<Integer>)p.value).value;
            a = b;
            p = p.right;
        }
        return(s);
    }

    public void printVertical()
    {
        String s = "";
        SkipListEntry p;
        p = head;
        while ( p.down != null )
            p = p.down;
        while ( p != null )
        {
            s = getOneColumn( p );
            System.out.println(s);
            p = p.right;
        }
    }


    public String getOneColumn( SkipListEntry p )
    {
        String s = "";
        while ( p != null )
        {
            s = s + " " + p.key;
            p = p.up;
        }
        return(s);
    }

} 