package menu;

import sort.enums.SortField;
import student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ParallelCounter {

    private ParallelCounter() {}

    public static long countOccurrences(List<Student> students, Object target, int fieldCode, int threads) {
        if (students == null || students.isEmpty() || threads < 1) {
            return 0;
        }

        SortField field = SortField.fromCode(fieldCode);
        int total = students.size();
        int chunk = Math.max(1, total / threads);

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        try {
            List<Future<Long>> futures = new ArrayList<>();

            for (int t = 0; t < threads; t++) {
                int from = t * chunk;
                if (from >= total) break;
                int to = Math.min(from + chunk, total);

                int finalFrom = from;
                int finalTo = to;

                Callable<Long> task = () -> {
                    long count = 0;
                    for (int i = finalFrom; i < finalTo; i++) {
                        if (field.matches(students.get(i), target)) {
                            count++;
                        }
                    }
                    return count;
                };

                futures.add(pool.submit(task));
            }

            long result = 0;
            for (Future<Long> f : futures) {
                result += f.get();
            }
            return result;
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Ошибка многопоточного подсчёта", e);
        } finally {
            pool.shutdown();
        }
    }
}