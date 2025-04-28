package integration_test_resources;

public class Cap008File extends Cap007File {
    @Override
    public void metodoAbstracto() {
        System.out.println("Implementación del método abstracto en la clase hija");
    }
    
    public void metodoHijo() {
        System.out.println("Método específico de la clase hija");
    }
}
