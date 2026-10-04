package homework.menu;

import homework.sort.comparator.AvgScoreComparator;
import homework.sort.comparator.GroupNumComparator;
import homework.sort.comparator.StudentIdComparator;
import homework.sort.SortStrategy;
import homework.student.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class StudentService {

    public enum SortField {
        AVG_SCORE(1),
        GROUP_NUM(2),
        STUDENT_ID(3);

        private static final Map<Integer, SortField> BY_CODE =
                Arrays.stream(values())
                        .collect(Collectors.toMap(f -> f.code, f -> f));

        private final int code;

        SortField(int code) {
            this.code = code;
        }

        public static SortField fromCode(int code) {
            SortField field = BY_CODE.get(code);
            if (field == null) {
                throw new IllegalArgumentException("Неизвестное поле сортировки: " + code);
            }
            return field;
        }

        public Comparator<Student> getComparator() {
            return switch (this) {
                case AVG_SCORE -> new AvgScoreComparator();
                case GROUP_NUM -> new GroupNumComparator();
                case STUDENT_ID -> new StudentIdComparator();
            };
        }
    }

    private SortStrategy sorter;
    private final List<Student> students = new ArrayList<>();

    public StudentService(SortStrategy sorter) {
        this.sorter = Objects.requireNonNull(sorter, "sorter");
    }

    public void setSorter(SortStrategy sorter) {
        this.sorter = Objects.requireNonNull(sorter, "sorter");
    }

    public void setStudents(List<Student> students) {
        Objects.requireNonNull(students, "students");
        this.students.clear();
        for (Student student : students) {
            this.students.add(Objects.requireNonNull(student, "student"));
        }
    }

    public boolean isEmpty() {
        return students.isEmpty();
    }

    public int size() {
        return students.size();
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public void clear() {
        students.clear();
    }

    public void sort(int fieldCode) {
        if (isEmpty()) return;
        SortField field = SortField.fromCode(fieldCode);
        sorter.sort(students, field.getComparator());
    }
}