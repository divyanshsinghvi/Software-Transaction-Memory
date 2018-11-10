public class SkipListEntry<T>
{   //This implementation from ~cheung/Courses/
    public String key;
    public T value;

    public int pos;      // I added this to print the skiplist "nicely"

    public SkipListEntry up, down, left, right;

    public static String negInf = new String("-oo");  // -inf key value
    public static String posInf = new String("+oo");  // +inf key value

    public SkipListEntry(String k, T v)
    {
        key = k;
        value = v;

        up = down = left = right = null;
    }

    public T getValue()
    {
        return value;
    }

    public String getKey()
    { return key;
    }

    public T setValue(T val)
    {
        T oldValue = value;
        value = val;
        return oldValue;
    }

    public boolean equals(Object o)
    {
        SkipListEntry ent;

        try
        {
            ent = (SkipListEntry) o;    // Test if o is a SkipListEntry...
        }
        catch (ClassCastException ex)
        {
            return false;
        }

        return (ent.getKey() == key) && (ent.getValue() == value);
    }

    public String toString()
    {
        return "(" + key + "," + value + ")";
    }
}