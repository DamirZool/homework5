package tests.testMain;

import tests.fill.TestFileFill;
import tests.fill.TestManualFill;
import tests.fill.TestRandomFill;
import tests.sort.TestInsertionTest;
import tests.sort.TestQuick;

public final class TestRun {
    public static void main(String[] args) {
        System.out.println("=== Fill tests ===");
        TestRandomFill.main(args);
        TestManualFill.main(args);
        TestFileFill.main(args);

        System.out.println("=== Sort tests ===");
        TestInsertionTest.main(args);
        TestQuick.main(args);
    }
}