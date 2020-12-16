public class MyTest {
    public static void main(String[] args) {
        Poly test = new Poly().add(new Poly(1,4)).add(new Poly(4,2));
        Poly testUno = new Poly().add(new Poly(2,4)).add(new Poly(4,1));

        System.out.println(test);
        System.out.println(testUno);
        System.out.println(test.mul(testUno));

    }
}