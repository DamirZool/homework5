package menu;

import sort.InsertionSort;
import sort.QuickSort;
import sort.SortStrategy;

import student.Student;

import writing.AddFile;

import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

import static menu.FillMain.fillMain;

public class Menu extends MenuIO{

    private final StudentService service;

    public Menu(StudentService service, Scanner scanner) {
        super(scanner);
        this.service = service;
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Menu(new StudentService(new InsertionSort()), scanner).run();
        }
    }

    public void run() {
        while (true) {
            printMainMenu();
            int choice = readChoice(0, 7);

            switch (choice) {
                case 1 -> createStudents();
                case 2 -> showStudents();
                case 3 -> sortStudentsMenu();
                case 4 -> sortEvenOnly();
                case 5 -> clearStudents();
                case 6 -> saveFile();
                case 7 -> countOccurrencesMenu();
                case 0 -> {
                    System.out.print("Выход.");
                    return;
                }
            }
            pause();
        }
    }

    // Создание списка студента
    private void createStudents() {
        List<Student> created = fillMain(scanner);
        if (created == null) {
            System.out.println("Создание отменено.");
            return;
        }
        service.setStudents(created);
        System.out.println("Список создан. Элементов: " + service.size());
    }

    // Вывод списка студентов
    private void showStudents() {
        if (!ensureNotEmpty()) return;
        service.getStudents().forEach(System.out::println);
    }

    // Меню сортировки студентов
    private void sortStudentsMenu() {
        if (!ensureNotEmpty()) return;

        System.out.println("Сортировать по: 1 — средний балл, 2 — группа, 3 — зачётка, 0 — вернуться");
        int fieldChoice = readChoice(0, 3);
        if (fieldChoice == 0) return;

        chooseAndApply(strategy -> {
            service.setSorter(strategy);
            service.sort(fieldChoice);
        });
    }

    private void sortEvenOnly() {
        chooseAndApply(strategy -> service.sortEvenOnly(strategy));
    }

    private void chooseAndApply(Consumer<SortStrategy> action) {
        if (!ensureNotEmpty()) return;

        System.out.println("Выберите алгоритм: 1 — вставками, 2 — быстрая, 0 — вернуться в меню");
        int choice = readChoice(0, 2);
        if (choice == 0) return;

        SortStrategy strategy = switch (choice) {
            case 1 -> new InsertionSort();
            case 2 -> new QuickSort();
            default -> throw new IllegalStateException();
        };

        action.accept(strategy);
        System.out.println("Список отсортирован.");
    }

    private void clearStudents() {
        service.clear();
        System.out.println("Список очищен.");
    }

    private void countOccurrencesMenu() {
        if (!ensureNotEmpty()) return;

        System.out.println("По какому полю считать: 1 — средний балл, 2 — группа, 3 — зачётка, 0 — отмена");
        int fieldChoice = readChoice(0, 3);
        if (fieldChoice == 0) return;

        System.out.print("Введите значение для поиска: ");
        String raw = scanner.nextLine().trim();

        Object target;
        if (fieldChoice == 1) {
            try {
                target = Double.parseDouble(raw);
            } catch (NumberFormatException e) {
                System.out.println("Для среднего балла нужно число.");
                return;
            }
        } else {
            target = raw;
        }

        System.out.print("Сколько потоков использовать: ");
        int threads = readChoice(1, 32);

        long count = service.countOccurrences(target, fieldChoice, threads);
        System.out.println("Найдено вхождений: " + count);
    }

    private void saveFile() {
        if (!ensureNotEmpty()) return;
        AddFile.addFile(service.getStudents(), scanner);
    }

    private boolean ensureNotEmpty() {
        if (service.isEmpty()) {
            System.out.println("Список пуст.");
            return false;
        }
        return true;
    }
}