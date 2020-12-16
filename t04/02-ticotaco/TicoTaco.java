import java.util.Scanner;

public class TicoTaco {
    public static void main(String[] args){
        int max = 0;
        try(Scanner s = new Scanner(System.in)){
            max = s.nextInt();
        }
        ticoTaco(max);
    }

    public static void ticoTaco(int max){
        for(int i = 1; i <= max; i++){
            if(i%3 == 0 && i%7 == 0){
                System.out.println("Tico Taco");
            }else if(i%3 == 0){
                System.out.println("Tico");
            }else if(i%7 == 0){
                System.out.println("Taco");
            }else{
                System.out.println(i);
            }
        }
    }
}