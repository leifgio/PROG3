import java.util.Scanner;

public class Problem2 {
    static String word1,word2;
    public static void main(String[] args) {
            getEntry();
            concat();
        }

    public static void getEntry(){
        System.out.println("Please enter a word: ");
        Scanner scanner = new Scanner(System.in);
        word1 = scanner.nextLine();
        System.out.println("Please enter another word: ");
        word2 = scanner.nextLine();
        scanner.close();
    }

    public static void concat(){
        if(word1.length() == word2.length()){
            System.out.println("");
            System.out.println(word1 + ":)");
            System.out.println(word2 + ":(");
        }
    }
}
        
    
