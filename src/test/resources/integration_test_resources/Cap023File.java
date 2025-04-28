package integration_test_resources;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class Cap023File {
    Function<String, Integer> longitud = cadena -> cadena.length();
    BiFunction<Integer, Integer, Integer> suma = (a, b) -> a + b;
    BinaryOperator<Double> multiplicacion = (Double a, Double b) -> a * b;
    
}