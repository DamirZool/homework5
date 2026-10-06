package menu;

import sort.InsertionSort;
import sort.QuickSort;
import sort.SortStrategy;
import student.Student;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private final StudentService service;
    private final Scanner scanner;

    public Menu(StudentService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Menu(new StudentService(new InsertionSort()), scanner).run();
        }
    }

    public void run() {
        while (true) {
            printMainMenu();
            int choice = readChoice(0, 5);

            switch (choice) {
                case 1 -> createStudents();
                case 2 -> showStudents();
                case 3 -> sortStudentsMenu();
                case 4 -> sortEvenOnly();
                case 5 -> clearStudents();
//                case 6 -> сохранение в файл
                case 0 -> {
                    System.out.println("Выход.");
                    return;
                }
            }

            pause();
        }
    }

    private void printMainMenu() {
        System.out.println("""
                Меню:
                1 - создать список студентов
                2 - показать список студентов
                3 - отсортировать список
                4 - сортировка по четности среднего бала
                5 - очистить список
                6 - выгрузить список
                0 - выход
                
                Выберите пункт:\s""");
    }

    private void createStudents() {
        List<Student> created = FillMain.fillMain(scanner);
        if (created == null) {
            System.out.println("Создание отменено.");
            return;
        }
        service.setStudents(created);
        System.out.println("Список создан. Элементов: " + service.size());
    }

    private void showStudents() {
        if (!ensureNotEmpty()) return;
        service.getStudents().forEach(System.out::println);
    }

    private void sortStudentsMenu() {
        if (!ensureNotEmpty()) return;
        System.out.println("Выберите алгоритм: 1 — вставками, 2 — быстрая, 0 — вернуться в меню");
        int algoChoice = readChoice(0, 2);
        if (algoChoice == 0) return;
        SortStrategy strategy = switch (algoChoice) {
            case 1 -> new InsertionSort();
            case 2 -> new QuickSort();
            default -> throw new IllegalStateException();
        };
        service.setSorter(strategy);

        System.out.println("Сортировать по: 1 — средний балл, 2 — группа, 3 — зачётка, 0 — вернуться в меню");
        int fieldChoice = readChoice(0, 3);
        if (fieldChoice == 0) return;

        service.sort(fieldChoice);
        System.out.println("Список отсортирован.");
    }

    public void sortEvenOnly() {
        if (!ensureNotEmpty()) return;
        System.out.println("Выберите алгоритм: 1 — вставками, 2 — быстрая, 0 — вернуться в меню");
        int algoChoice = readChoice(0, 2);
        if (algoChoice == 0) return;
        SortStrategy strategy = switch (algoChoice) {
            case 1 -> new InsertionSort();
            case 2 -> new QuickSort();
            default -> throw new IllegalStateException();
        };

        service.sortEvenOnly(strategy);
        System.out.println("Список отсортирован.");
    }

    private void clearStudents() {
        service.clear();
        System.out.println("Список очищен.");
    }

    private boolean ensureNotEmpty() {
        if (service.isEmpty()) {
            System.out.println("Список пуст.");
            return false;
        }
        return true;
    }

    private void pause() {
        System.out.println("\nНажмите Enter для продолжения...");
        scanner.nextLine();
        System.out.println();
    }

    private int readChoice(int min, int max) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Введите число от " + min + " до " + max);
            } else if (scanner.hasNext()) {
                scanner.next();
                System.out.println("Введите число от " + min + " до " + max);
            } else {
                throw new IllegalStateException("Входной поток завершён");
            }
        }
    }
}