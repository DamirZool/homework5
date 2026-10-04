package homework.fill;

import homework.student.Student;

import java.util.List;
import java.util.Scanner;

@FunctionalInterface
public interface FillStrategy {
    List<Student> fill(int length, Scanner scanner);
}
