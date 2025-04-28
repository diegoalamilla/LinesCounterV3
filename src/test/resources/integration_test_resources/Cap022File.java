package integration_test_resources;
public class Cap022File {
    public static void main(String[] args) {
        int a = 10, b = 20;
        int max = (a > b) ? a : b;
        System.out.println("El mayor es: " + max);
        
        String resultado = (a == b) ? "a y b son iguales" : (a > b) ? "a es mayor" : "b es mayor";
        System.out.println("Resultado de comparación: " + resultado);
        
        String valor = (a > b) ? ((b > 5) ? "b es mayor que 5" : "b no es mayor que 5") : "a no es mayor que b";
        System.out.println("Valor: " + valor);

        int resultado2 = (a > b) ? (max = a, 100) : (max = b, 200);  
        System.out.println("Resultado 2: " + resultado2 + ", max: " + max);

        boolean isEven = (a % 2 == 0) ? true : (b % 2 == 0) ? false : true;
        System.out.println("¿Es a o b par? " + isEven);
        
        String cadena = (a < b) ? "Valor de a es menor que b, " + (a + b) : "Valor de b es mayor que a";
        System.out.println(cadena);
        
        System.out.println(getResultado(a, b));
    }

    public static String getResultado(int x, int y) {
        return (x > y) ? "x es mayor" : (x == y) ? "x y y son iguales" : "y es mayor";
    }
}
