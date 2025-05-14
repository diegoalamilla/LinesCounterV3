package com.proy.Domain;

import com.proy.Infrastructure.FileWriterUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProjectComparator {

    private Path reportRoot;
    private Path originalFile;
    private Path modifiedFile;

    public ProjectComparator (Path originalFile, Path modifiedFile, Path reportRoot) {
        this.reportRoot = reportRoot;
        this.originalFile = originalFile;
        this.modifiedFile = modifiedFile;

    }

    public void compareProjects() throws IOException {

        if (this.originalFile.toFile().isFile()){
            compareFiles();
        } else if (this.originalFile.toFile().isDirectory()) {
            compareDirectories();
        }

    }
    private void compareFiles() throws IOException {
        List<String> originalLines = Files.readAllLines(this.originalFile);
        List<String> modifiedLines = Files.readAllLines(this.modifiedFile);
        List<String> reportLines = new ArrayList<>();

        int origIdx = 0, modIdx = 0;
        int origSize = originalLines.size();
        int modSize = modifiedLines.size();

        Path reportFile = reportRoot.resolve(originalFile.getFileName());

        while (origIdx < origSize || modIdx < modSize) {
            String origLine = origIdx < origSize ? originalLines.get(origIdx) : null;
            String modLine = modIdx < modSize ? modifiedLines.get(modIdx) : null;

            if (origLine != null && modLine != null && origLine.equals(modLine)) {
                reportLines.add(origLine);
                origIdx++;
                modIdx++;
            } else if (origLine != null && (modLine == null || !modifiedLines.subList(modIdx, modSize).contains(origLine))) {
                reportLines.add("//erased");
                origIdx++;
            } else if (modLine != null && (origLine == null || !originalLines.subList(origIdx, origSize).contains(modLine))) {
                reportLines.add(modLine + " //added");
                modIdx++;
            } else {
                reportLines.add(modLine + " //added");
                origIdx++;
                modIdx++;
            }
        }

        FileWriterUtil.writeLinesToFile(reportFile, reportLines);
    }

    private void compareDirectories() throws IOException {
        Directory originalDirectory = new Directory(this.originalFile.getFileName().toString());

        File[] originalFiles = this.getFilesFromTheDirectory(this.originalFile.toFile());

        for (File file : originalFiles) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                ProjectComparator pc = new ProjectComparator(file.toPath(), Path.of(modifiedFile + "/" + file.getName()), Path.of(reportRoot + "/" + originalDirectory.getName()));
                pc.compareProjects();

            } else if (file.isDirectory()) {
                ProjectComparator pc = new ProjectComparator(file.toPath(), Path.of(modifiedFile + "/" + file.getName()), Path.of(reportRoot + "/" + originalDirectory.getName()));
                pc.compareDirectories();

            }
        }

    }

    private File[] getFilesFromTheDirectory(File file) {

        return file.listFiles() != null ? file.listFiles() : new File[0];

    }




}

