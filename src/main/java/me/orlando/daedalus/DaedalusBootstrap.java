package me.orlando.daedalus;

import me.orlando.daedalus.result.HttpPathResultFetcher;
import me.orlando.daedalus.solver.*;
import me.orlando.daedalus.result.PathResultFetcher;
import me.orlando.daedalus.solver.PathSolver;

public class DaedalusBootstrap {

    private final static String MOVE_URL = "https://ctf.ageei.org/daedalusv3105/move";

    public static void main(String[] args) throws Exception {
        PathResultFetcher resultFetcher = new HttpPathResultFetcher(MOVE_URL);
        PathSolver solver = new DfsPathSolver(resultFetcher);

        System.out.println("Finding path");
        Path path = solver.solve();

        System.out.println("Flag is: " + path.encoded());
    }
}