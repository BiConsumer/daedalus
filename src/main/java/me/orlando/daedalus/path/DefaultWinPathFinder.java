package me.orlando.daedalus.path;

import java.util.*;

public class DefaultWinPathFinder implements WinPathFinder {

    private final PathChooser chooser;
    private final PathResolver resolver;

    public DefaultWinPathFinder(PathChooser chooser, PathResolver resolver) {
        this.chooser = chooser;
        this.resolver = resolver;
    }

    @Override
    public Path find() throws Exception {
        Map<Path, PathResolver.Result> previous = new HashMap<>();
        PathResolver.Result result = PathResolver.Result.BONK;
        Path path = null;

        while (result != PathResolver.Result.WIN) {
            path = chooser.choose(previous);
            System.out.println("chosen " + Arrays.toString(path.movements()));
            result = resolver.resolve(path);

            if (result != PathResolver.Result.WIN) {
                System.out.println(Arrays.toString(path.movements()) + " NOT WIN");
                previous.put(path, result);
            }
        }

        return path;
    }
}
