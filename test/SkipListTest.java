public class SkipListTest {
    /*
        Testing single threaded implementation of skiplist
     */

    public static void main(String[] args)
    {

        SkipListBasic<Integer> S = new SkipListBasic();

        S.printVertical();
        System.out.println("------");

        S.put("ABC", 123);
        S.printVertical();
        System.out.println("------");

        S.put("DEF", 123);
        S.printVertical();
        System.out.println("------");

        S.put("KLM", 123);
        S.printVertical();
        System.out.println("------");

        S.put("HIJ", 123);
        S.printVertical();
        System.out.println("------");

        S.put("GHJ", 123);
        S.printVertical();
        System.out.println("------");

        S.put("AAA", 123);
        S.printVertical();
        System.out.println("------");


    }

}



