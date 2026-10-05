package fill;

import student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomFill implements FillStrategy {
    private final Random random = new Random();

    private static final String[] GROUP_NUMS = {"A_1", "B_1", "C_1"};

    @Override
    public List<Student> fill(int length, Scanner scanner) {
        List<Student> result = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            String groupNum = GROUP_NUMS[random.nextInt(GROUP_NUMS.length)];
            double avgScore = random.nextDouble(Student.MIN_SCORE, Student.MAX_SCORE);
            result.add(new Student.Builder().setGroupNum(groupNum).setAvgScore(avgScore).setStudentId(String.valueOf(i + 1)).build());
        }
        return result;
    }
}