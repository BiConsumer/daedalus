package me.orlando.daedalus.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IteratingPathChooser implements PathChooser {

    private final static char[] MOVEMENTS = {
      'r',
      'l',
      'u',
      'd'
    };

    @Override
    public Path choose(Collection<Path> previous) {
        List<Character> current = new ArrayList<>();
        int movementIndex = 0;
        current.add(MOVEMENTS[movementIndex]);

        while (previous.stream().anyMatch(path -> path.matches(current))) {
            if (movementIndex < MOVEMENTS.length-1) {
                movementIndex++;
                current.set(current.size()-1, MOVEMENTS[movementIndex]);
            } else {
                movementIndex = 0;
                current.add(MOVEMENTS[movementIndex]);
            }
        }

        return new Path(current.toArray(new Character[0]));
    }
}
