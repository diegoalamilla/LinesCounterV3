package integration_test_resources;

public class Cap020File {

    public void bucleFor() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("For: " + i);
            for (int j = 1; i <= 5; i++) {
                System.out.println("For: " + i);
            }
        }
    }

    public void bucleWhile() {
        int j = 1;
        while (j <= 5) {
            System.out.println("While: " + j);
            j++;
        }
        int n = 1;
        while (j <= 5) {
            System.out.println("While: " + j);
            j++;
            while (n <= 100) {
                System.out.println("While: " + j);
                n++;
            }
        }


    }
}