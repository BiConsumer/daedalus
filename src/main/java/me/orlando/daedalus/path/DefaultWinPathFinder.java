package me.orlando.daedalus.path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultWinPathFinder implements WinPathFinder {

    private final PathChooser chooser;
    private final PathResolver resolver;

    public DefaultWinPathFinder(PathChooser chooser, PathResolver resolver) {
        this.chooser = chooser;
        this.resolver = resolver;
    }

    @Override
    public Path find() throws Exception {
        List<Path> bonked = new ArrayList<>();
        PathResolver.Result result = PathResolver.Result.BONK;
        Path path = null;

        while (result != PathResolver.Result.WIN) {
            path = chooser.choose(bonked);
            System.out.println("chosen " + Arrays.toString(path.movements()));
            result = resolver.resolve(path);

            if (result != PathResolver.Result.WIN) {
                System.out.println(Arrays.toString(path.movements()) + " NOT WIN");
                bonked.add(path);
            }
        }

        return path;
    }
}
