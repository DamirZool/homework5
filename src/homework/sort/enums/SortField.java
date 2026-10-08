package sort.enums;

import sort.comparator.AvgScoreComparator;
import sort.comparator.GroupNumComparator;
import sort.comparator.StudentIdComparator;
import student.Student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

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

    public boolean matches(Student student, Object target) {
        return switch (this) {
            case AVG_SCORE -> target instanceof Double d && student.getAvgScore() == d;
            case GROUP_NUM -> target instanceof String s && s.equals(student.getGroupNum());
            case STUDENT_ID -> target instanceof String s && s.equals(student.getStudentId());
        };
    }
}