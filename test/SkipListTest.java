public class SkipListTest {


    public static void main(String[] args)
    {

        SkipListBasic<Integer> S = new SkipListBasic();

        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("ABC", 123);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("DEF", 123);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("KLM", 123);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("HIJ", 123);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("GHJ", 123);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");

        S.put("AAA", 123);
        S.printHorizontal();
        System.out.println("------");
//    S.printVertical();
//    System.out.println("======");


    }

}



