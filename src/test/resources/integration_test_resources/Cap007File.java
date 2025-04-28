package integration_test_resources;

public abstract class Cap007File {
    public abstract void metodoAbstracto();
    public abstract void metodoAbstracto1();
    public abstract void metodoAbstracto3();
    
    public void metodoConcreto() {
        System.out.println("Este es un método concreto en una clase abstracta");
    }
}
