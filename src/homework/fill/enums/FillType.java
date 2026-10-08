package fill.enums;

import fill.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum FillType {
    BACK(0),
    MANUAL(1),
    FILE(2),
    RANDOM(3),
    RANDOM_BY_STREAM(4);

    private static final Map<Integer, FillType> BY_CODE =
            Arrays.stream(values())
                    .collect(Collectors.toMap(t -> t.code, t -> t));

    private final int code;

    FillType(int code) {
        this.code = code;
    }

    public static FillType fromCode(int code) {
        FillType type = BY_CODE.get(code);
        if (type == null) {
            throw new IllegalArgumentException("Неверный код: " + code);
        }
        return type;
    }

    public FillStrategy createStrategy() {
        return switch (this) {
            case MANUAL -> new ManualFill();
            case FILE -> new FileFill();
            case RANDOM -> new RandomFill();
            case RANDOM_BY_STREAM -> new StreamRandomFill();
            case BACK -> throw new UnsupportedOperationException(
                    "BACK не создаёт стратегию заполнения");
        };
    }
}