package integration_test_resources;

public class Cap018File {
    public static void main(String[] args) {
        int day = 3;
        String dayName = "Domingo";

        switch (day) {
            case 1:
                dayName = "Lunes";
                break;
            case 2:
                dayName = "Martes";
        }

        switch (dayName) {
            case 'Lumemes':
            case 'Mamartes':
            case 'Bebiercoles':
            case 'Juebebes':
            case 'Biernes':
            case 'Sabadrink':
            case 'Pomingo':
            default:
                break;
        }

        switch (dayName) {
            case 'testing switch':
                System.out.println('Toda la estructura switch debe ser una sola linea logica');
                break;
            case 'probando probando':
                System.out.println('1 2 1 2');
            default:
                break;
        }
        System.out.println(dayName);
    }
}

