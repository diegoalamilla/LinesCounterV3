package com.proy.Domain;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;
import java.nio.file.Path;
import static org.junit.Assert.*;

/**
* Pruebas unitarias para la clase ProjectComparator.
* Se enfocan en validar los métodos internos de comparación de texto,
* como la similitud de líneas y la distancia de Levenshtein.
*/
public class ProjectComparatorTest {
    
    private ProjectComparator comparator;

    /**
     * Configura un ProjectComparator con rutas ficticias antes de cada prueba.
     */
    @Before
    public void setUp() {
        comparator = new ProjectComparator(
                Path.of("original.txt"),
                Path.of("modified.txt"),
                Path.of("report"),
                Path.of("original"),
                Path.of("modified"),
                Path.of("originalReport"),
                Path.of("modifiedReport")
        );
    }

    /**
     * Verifica que la similitud entre dos líneas idénticas sea 1.0.
     */
    @Test
    public void testCalculateSimilarity_identicalLines() throws Exception {
        Method method = ProjectComparator.class.getDeclaredMethod(
            "calculateSimilarity", String.class, String.class
        );
        method.setAccessible(true);
        double similarity = (double) method.invoke(
            comparator,
            "System.out.println(\"Hello\");",
            "System.out.println(\"Hello\");"
        );
        assertEquals(1.0, similarity, 0.0001);
    }

    /**
     * Verifica que la similitud entre dos líneas completamente diferentes sea menor a 0.5.
     */
    @Test
    public void testCalculateSimilarity_completelyDifferentLines() throws Exception {
    Method method = ProjectComparator.class.getDeclaredMethod(
        "calculateSimilarity", String.class, String.class
    );
    method.setAccessible(true);
    double similarity = (double) method.invoke(comparator, 
        "int x = 10;", 
        "String name = \"abc\";"
    );
    assertTrue("Expected similarity < 0.5", similarity < 0.5);
}

    /**
     * Verifica que la similitud entre dos líneas levemente diferentes sea mayor a 0.5 pero menor a 1.0.
     */
    @Test
    public void testCalculateSimilarity_slightlyModifiedLines() throws Exception {
    Method method = ProjectComparator.class.getDeclaredMethod(
        "calculateSimilarity", String.class, String.class
    );
    method.setAccessible(true);
    double similarity = (double) method.invoke(
        comparator, "int x = 10;", "int x = 100;"
    );
    assertTrue("Expected similarity between 0.5 and 1.0", similarity > 0.5 && similarity < 1.0);
}

     /**
     * Verifica que la distancia de Levenshtein entre "kitten" y "sitting" sea 3.
     */
    @Test
    public void testComputeLevenshteinDistance_basic() throws Exception {
    Method method = ProjectComparator.class.getDeclaredMethod(
        "computeLevenshteinDistance", String.class, String.class
    );
    method.setAccessible(true);
    int distance = (int) method.invoke(comparator, "kitten", "sitting");
    assertEquals(3, distance);
}

    /**
     * Verifica que la distancia de Levenshtein entre dos cadenas idénticas sea 0.
     */
    @Test
    public void testComputeLevenshteinDistance_identicalStrings() throws Exception {
    Method method = ProjectComparator.class.getDeclaredMethod(
        "computeLevenshteinDistance", String.class, String.class
    );
    method.setAccessible(true);
    int distance = (int) method.invoke(comparator, "test", "test");
    assertEquals(0, distance);
}

    /**
     * Verifica que la distancia de Levenshtein entre dos cadenas vacías sea 0.
     */
    @Test
    public void testComputeLevenshteinDistance_emptyStrings() throws Exception {
    Method method = ProjectComparator.class.getDeclaredMethod(
        "computeLevenshteinDistance", String.class, String.class
    );
    method.setAccessible(true);
    int distance = (int) method.invoke(comparator, "", "");
    assertEquals(0, distance);
}
}

