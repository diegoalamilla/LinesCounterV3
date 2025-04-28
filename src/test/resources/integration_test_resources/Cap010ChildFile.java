package integration_test_resources;

public class Cap010ChildFile extends Cap010File {
    @Override
    public void metodoInterfaz() {
        System.out.println("Implementación del método de la interfaz en la clase hija");
    }
    
    public void metodoEspecifico() {
        System.out.println("Método exclusivo de la clase hija");
    }
}
