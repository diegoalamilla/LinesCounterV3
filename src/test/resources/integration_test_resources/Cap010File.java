package integration_test_resources;

import integration.integration_test_resources.Cap009Interface;

public class Cap010File extends Cap001File implements Cap010Interface, Cap009Interface {
    @Override
    public void metodoInterfaz() {
        System.out.println("Implementación del método de la interfaz en la clase padre");
    }
}
