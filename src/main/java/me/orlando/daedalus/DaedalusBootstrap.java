package me.orlando.daedalus;

import me.orlando.daedalus.path.*;

import java.util.Arrays;

public class DaedalusBootstrap {

    private final static String MOVE_URL = "https://ctf.ageei.org/daedalusv3105/move";

    public static void main(String[] args) throws Exception {
        PathChooser chooser = new IteratingPathChooser();
        PathResolver resolver = new HttpPathResolver(MOVE_URL);
        WinPathFinder pathFinder = new DefaultWinPathFinder(chooser, resolver);

        System.out.println("Finding path");
        Path path = pathFinder.find();

        System.out.println(Arrays.toString(path.movements()));
    }
}