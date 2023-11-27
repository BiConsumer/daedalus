package me.orlando.daedalus.path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class IteratingPathChooser implements PathChooser {

    private final static char[] MOVEMENTS = {
      'r',
      'l',
      'u',
      'd'
    };

    @Override
    public Path choose(Map<Path, PathResolver.Result> previous) {
        Collection<Path> previousPaths = previous.keySet();
        List<Character> current = new ArrayList<>();
        int movementIndex = 0;
        current.add(MOVEMENTS[movementIndex]);

        while (
                previousPaths.stream().anyMatch(path -> path.matches(current))
                || fromMap(current, previous) == PathResolver.Result.BONK
        ) {
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

    private PathResolver.Result fromMap(List<Character> current, Map<Path, PathResolver.Result> previous) {
        for (Map.Entry<Path, PathResolver.Result> entry : previous.entrySet()) {
            if (entry.getKey().matches(current)) {
                return entry.getValue();
            }
        }

        return PathResolver.Result.MOVING;
    }
}
