public interface SkipNode<T> {
    public int getKey();
    public void setKey(int value);
    public T getItem();
    public void setItem(T value);
    public STMArray<SkipNode<T>> getNext();
    public void setNext(STMArray<SkipNode<T>> value);
}
