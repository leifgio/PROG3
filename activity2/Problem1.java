import java.util.Scanner;

public class Problem1{
    static String word1,word2;
    public static void main(String[] args) {
            getEntry();
            checkEnd();    
    }

    public static void getEntry(){
        System.out.println("Please enter a word: ");
        Scanner scanner = new Scanner(System.in);
        word1 = scanner.nextLine();
        System.out.println("Please enter another word: ");
        word2 = scanner.nextLine();
        scanner.close();
    }

    public static void checkEnd(){
        if(word1.toUpperCase().endsWith("S") && word2.toUpperCase().endsWith("S")){
            System.out.println("");
            System.out.println("They both end with s");
            System.out.println("");
        }
    }
}