package fill;

import collection.StudentList;
import student.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StreamRandomFill implements FillStrategy {

    private static final String[] GROUP_NUMS = {"A_1", "B_1", "C_1"};

    private final Random random = new Random();

    @Override
    public List<Student> fill(int length, java.util.Scanner scanner) {
        if (length < 0) {
            throw new IllegalArgumentException("Длина не может быть отрицательной");
        }
        List<Student> result = new StudentList(length);
        IntStream.range(0, length)
                .mapToObj(i -> new Student.Builder()
                        .setGroupNum(GROUP_NUMS[random.nextInt(GROUP_NUMS.length)])
                        .setAvgScore(random.nextDouble(Student.MIN_SCORE, Student.MAX_SCORE))
                        .setStudentId(String.valueOf(10000 + random.nextInt(90000)))
                        .build())
                .forEach(result::add);
        return result;
    }
}