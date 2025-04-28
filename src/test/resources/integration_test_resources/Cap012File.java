package integration_test_resources;

public class Cap012File {

    public static int cuadrado(int numero) {
        return numero * numero;
    }
    public static int sumar(int a, int b) {
        return a + b;
    }

    public static String mensaje(int numero, String texto) {
        return "El número es: " + numero + " y el texto es: " + texto;
    }

    public static int sumaArreglo(int[] numeros) {
        int suma = 0;
        for (int num : numeros) {
            suma += num;
        }
        return suma;
    }

    public static void main(String[] args) {
        int[] arreglo = {1, 2, 3, 4, 5}; // Crear un arreglo
        int resultado = sumaArreglo(arreglo); // Llamada al método
        System.out.println("La suma del arreglo es: " + resultado);
    }

}

