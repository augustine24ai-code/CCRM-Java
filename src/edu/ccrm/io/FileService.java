package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileService {

    private static final Path DATA_DIRECTORY = Paths.get("data");
    private static final Path BACKUP_DIRECTORY = Paths.get("backups");
    private static final Path STUDENTS_FILE = DATA_DIRECTORY.resolve("students.csv");
    private static final Path COURSES_FILE = DATA_DIRECTORY.resolve("courses.csv");

    public record ImportedData(List<Student> students, List<Course> courses) {}

    public ImportedData importData() {
        if (Files.notExists(STUDENTS_FILE)) { return new ImportedData(new ArrayList<>(), new ArrayList<>()); }
        List<Student> students = new ArrayList<>();
        try (Stream<String> lines = Files.lines(STUDENTS_FILE)) {
            lines.forEach(line -> {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        students.add(new Student(parts[2], parts[3], LocalDate.parse(parts[4]), parts[1]));
                    }
                } catch (Exception e) { System.out.println("Could not parse student line: " + line); }
            });
        } catch (IOException e) { System.out.println("Could not read students file."); }
        
        List<Course> courses = new ArrayList<>();
        try (Stream<String> lines = Files.lines(COURSES_FILE)) {
            lines.forEach(line -> {
                try {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        courses.add(new Course.Builder(parts[0], parts[1], Integer.parseInt(parts[2])).build());
                    }
                } catch (Exception e) { System.out.println("Could not parse course line: " + line); }
            });
        } catch (IOException e) { System.out.println("Could not read courses file."); }
        
        return new ImportedData(students, courses);
    }

    public void exportData(List<Student> students, List<Course> courses) {
        try {
            if (Files.notExists(DATA_DIRECTORY)) {
                Files.createDirectories(DATA_DIRECTORY);
            }
            List<String> studentLines = students.stream()
                    .map(s -> String.join(",", String.valueOf(s.getId()), s.getRegNo(), s.getFullName(), s.getEmail(), s.getDateOfBirth().toString()))
                    .toList();
            Files.write(STUDENTS_FILE, studentLines);
            System.out.println("Successfully exported " + students.size() + " students to " + STUDENTS_FILE.toAbsolutePath());
            
            List<String> courseLines = courses.stream()
                    .map(c -> String.join(",", c.getCode(), c.getTitle(), String.valueOf(c.getCredits())))
                    .toList();
            Files.write(COURSES_FILE, courseLines);
            System.out.println("Successfully exported " + courses.size() + " courses to " + COURSES_FILE.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backupData() {
        try {
            String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss").format(LocalDateTime.now());
            Path specificBackupPath = BACKUP_DIRECTORY.resolve("backup_" + timestamp);
            Files.createDirectories(specificBackupPath);

            if (Files.exists(STUDENTS_FILE)) {
                Files.copy(STUDENTS_FILE, specificBackupPath.resolve("students.csv"), StandardCopyOption.REPLACE_EXISTING);
            }
            if (Files.exists(COURSES_FILE)) {
                Files.copy(COURSES_FILE, specificBackupPath.resolve("courses.csv"), StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("Backup created successfully at: " + specificBackupPath.toAbsolutePath());

            long totalSize = calculateDirectorySize(specificBackupPath);
            System.out.println("Total size of backup directory: " + totalSize + " bytes.");
        } catch (IOException e) {
            System.out.println("ERROR: Could not create backup. " + e.getMessage());
        }
    }

    private long calculateDirectorySize(Path path) throws IOException {
        try (Stream<Path> walk = Files.walk(path)) {
            return walk
                    .filter(Files::isRegularFile)
                    .mapToLong(p -> {
                        try { return Files.size(p); } 
                        catch (IOException e) { return 0L; }
                    })
                    .sum();
        }
    }
}