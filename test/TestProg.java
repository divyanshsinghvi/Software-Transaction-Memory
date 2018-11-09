
public class TestProg
{

    public static void main(String[] args)
    {

        SkipList<Integer> S = new SkipList();
        Transaction tx = new Transaction();
//        TVar<STMSkipNode<Integer>> head = new TVar<>();
//        head.value = new STMSkipNode<>("A",2);
//        TVar<STMSkipNode<Integer>> p = new TVar<>();
//        p.value =  (STMSkipNode<Integer>) tx.read(head);
//        p.value.key.value = "B";
//        TVar<String> b = new TVar<>();
//        b.value = "B";
//        tx.write(p.value.key,b);
//        System.out.println(p.value.key.value);
//        System.out.println(head.value.key.value);

//        S.printHorizontal();
//        System.out.println("------");

//    S.printVertical();
//    System.out.println("======");

        S.put("ABC", 123,tx);
//        S.printHorizontal();
//        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");
        System.out.println("AAAA");

        S.put("DEF", 123,tx);
//        S.printHorizontal();
//        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        System.out.println("AAAA");
        S.put("KLM", 123,tx);
//        S.printHorizontal();
//        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");
        System.out.println("AAAA");

        S.put("HIJ", 123,tx);
//        S.printHorizontal();
//        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("GHJ", 123,tx);
      //  S.printHorizontal();
      //  System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("AAA", 123,tx);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.delete("HIJ",tx);
        S.printHorizontal();
        System.out.println("======");

    }

}