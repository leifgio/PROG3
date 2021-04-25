import java.util.Scanner;

public class Jonard{
    public static void main(String[] args) { 
        String word1; 
        String word2;
 
        Scanner scan = new Scanner(System.in); 
        System.out.println("Word 1: "); 
        word1 = scan.nextLine(); 
        
        System.out.println("Word 2: "); 
        word2 = scan.nextLine(); 
 
        if(word1.length()== word2.length()){ 
         System.out.println(word1 + word2); 
        } 
        else{ 
            System.out.println("Thats the word");
        } 
    }
}