package me.orlando.daedalus;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public record Path(char[] movements) {

    public final static char[] MOVEMENTS = {
            'r',
            'l',
            'u',
            'd'
    };

    private final static Base64.Encoder ENCODER = Base64.getEncoder();

    public String encoded() {
        return ENCODER.encodeToString(asString().getBytes(StandardCharsets.UTF_8));
    }

    public String asString() {
        return String.valueOf(movements);
    }

    public Path append(char movement) {
        char[] newMovements = new char[movements.length + 1];
        System.arraycopy(movements, 0, newMovements, 0, movements.length);

        newMovements[newMovements.length - 1] = movement;
        return new Path(newMovements);
    }

    public boolean isEmpty() {
        return movements.length == 0;
    }

    public Vec2 toVec() {
        return Vec2.fromPath(this);
    }

}
