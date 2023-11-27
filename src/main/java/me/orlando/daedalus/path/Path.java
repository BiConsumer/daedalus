package me.orlando.daedalus.path;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public record Path(char[] movements) {

    public Path(Character[] movements) {
        this(Arrays.stream(movements)
                .map(Object::toString)
                .collect(Collectors.joining())
                .toCharArray()
        );
    }

    private final static Base64.Encoder ENCODER = Base64.getEncoder();

    public String encoded() {
        return ENCODER.encodeToString(asString().getBytes(StandardCharsets.UTF_8));
    }

    public String asString() {
        return String.valueOf(movements);
    }

    public boolean matches(char[] path) {
        return Arrays.equals(movements, path);
    }

    public boolean matches(List<Character> path) {
        if (path.size() != movements.length) {
            return false;
        }

        for (int i = 0; i < movements.length; i++) {
            if (path.get(i) != movements[i]) {
                return false;
            }
        }

        return true;
    }

}
