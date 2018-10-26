public class Transaction {
    TxnDescriptor descriptor;
    TxnMemento memento;

    public Transaction(TxnDescriptor descriptor, TxnMemento memento) {
        this.descriptor = descriptor;
        this.memento = memento;
    }

   

}
