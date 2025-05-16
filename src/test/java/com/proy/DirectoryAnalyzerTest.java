package com.proy;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import com.proy.Domain.CodeSegment;
import com.proy.Domain.Directory;
import com.proy.Domain.DirectoryAnalyzer;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class DirectoryAnalyzerTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testDirectoryAnalyzerCountsJavaFiles() throws Exception {
        // Crear estructura de carpetas y archivos
        File rootDir = tempFolder.newFolder("src");
        File javaFile1 = new File(rootDir, "File1.java");
        File javaFile2 = new File(rootDir, "File2.java");

        List<String> javaContent = List.of(
                "public class Test {",
                "  public void hello() {",
                "    System.out.println(\"Hello\");",
                "  }",
                "}"
        );

        Files.write(javaFile1.toPath(), javaContent);
        Files.write(javaFile2.toPath(), javaContent);

        DirectoryAnalyzer analyzer = new DirectoryAnalyzer(rootDir);
        Directory result = analyzer.countLinesInDirectory();

        assertEquals("src", result.getName());
        assertEquals(2, result.getCodeSegments().size());

        for (CodeSegment cs : result.getCodeSegments()) {
            assertEquals(5, cs.getPhysicalLines());
            assertTrue(cs.isAClass());
        }

        assertEquals(10, result.getTotalPhysicalLines());
    }
}
