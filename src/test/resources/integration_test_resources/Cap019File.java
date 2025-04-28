
package integration_test_resources;

public class Cap019File {
    public static void main(String[] args) {
        try {
            int result = 10 / 0; 
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Este bloque siempre se ejecuta.");
        }

        try {
            int result = 10 / 0; 
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } 
        
    }
}
