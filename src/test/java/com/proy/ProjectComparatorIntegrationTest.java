package com.proy;
import com.proy.Domain.ProjectComparator;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static org.junit.Assert.*;

 /**
 * Prueba de integración para verificar el comportamiento del comparador de proyectos.
 * Comprueba que se generen correctamente los reportes de diferencias entre dos proyectos Java.
 */
public class ProjectComparatorIntegrationTest {

    private Path tempDir;
    private Path originalRoot;
    private Path modifiedRoot;
    private Path reportDir;

    /**
     * Configura el entorno antes de cada prueba.
     * Crea carpetas y archivos Java simulados con una diferencia en el contenido.
     */
    @Before
    public void setUp() throws IOException {
        tempDir = Files.createTempDirectory("project-comparator-test");
        originalRoot = tempDir.resolve("original");
        modifiedRoot = tempDir.resolve("modified");
        reportDir = tempDir.resolve("report");

        Files.createDirectories(originalRoot);
        Files.createDirectories(modifiedRoot);
        Files.createDirectories(reportDir);

        // Crear archivos Java originales y modificados
        Path originalFile = originalRoot.resolve("Test.java");
        Path modifiedFile = modifiedRoot.resolve("Test.java");

        Files.write(originalFile, List.of(
            "public class Test {",
            "    public void sayHello() {",
            "        System.out.println(\"Hello\");",
            "    }",
            "}"
        ));

        Files.write(modifiedFile, List.of(
            "public class Test {",
            "    public void sayHello() {",
            "        System.out.println(\"Hello World\");",  // Línea modificada
            "    }",
            "}"
        ));
    }

    /**
     * Prueba principal que ejecuta la comparación entre proyectos y valida los resultados.
     */
    @Test
    public void testCompareProjectsIntegration() throws IOException {
        ProjectComparator comparator = new ProjectComparator(
            originalRoot,
            modifiedRoot,
            reportDir,
            originalRoot,
            modifiedRoot,
            reportDir.resolve("original"),
            reportDir.resolve("modified")
        );

        comparator.compareProjects();
        comparator.writeGlobalSummary(reportDir);

        Path originalReport = reportDir.resolve("original/Test.java");
        Path modifiedReport = reportDir.resolve("modified/Test.java");
        Path summaryReport = reportDir.resolve("modificationsSummary.txt");

        assertTrue("Debe generarse el reporte original", Files.exists(originalReport));
        assertTrue("Debe generarse el reporte modificado", Files.exists(modifiedReport));
        assertTrue("Debe generarse el resumen global", Files.exists(summaryReport));

        List<String> summaryLines = Files.readAllLines(summaryReport);
        boolean foundModifiedLine = summaryLines.stream()
            .anyMatch(line -> line.toLowerCase().contains("slightly modified"));

        assertTrue("Debe indicar que hubo una línea ligeramente modificada", foundModifiedLine);
    }

    /**
     * Limpia los archivos temporales generados durante la prueba.
     */
    @After
    public void tearDown() throws IOException {
        deleteRecursively(tempDir.toFile());
    }

    private void deleteRecursively(File file) {
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                deleteRecursively(child);
            }
        }
        file.delete();
    }
}
