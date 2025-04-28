package integration_test_resources;

public class Cap016File {
    public static void main(String[] args) {
        int number = 10;

        if (number > 5) {
            System.out.println("El número es mayor que 5");
            if(number > 10){
                System.out.println("Numero muy grande");
            } else{
                System.out.println("Numero aaaa");
            }
        } else {
            System.out.println("El número es menor o igual a 5");
        }

        if (number % 2 == 0) {
            System.out.println("Es par");
        } else {
            System.out.println("Es impar");
        }
    }
}