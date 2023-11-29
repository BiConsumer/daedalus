package me.orlando.daedalus;

import me.orlando.daedalus.result.HttpPathResultFetcher;
import me.orlando.daedalus.solver.*;
import me.orlando.daedalus.result.PathResultFetcher;
import me.orlando.daedalus.solver.PathSolver;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class DaedalusBootstrap {

    private final static String MOVE_URL = "https://ctf.ageei.org/daedalusv3105/move";
    private final static String OUTPUT = "flag.txt";

    public static void main(String[] args) throws Exception {
        PathResultFetcher resultFetcher = new HttpPathResultFetcher(MOVE_URL);
        PathSolver solver = new BfsPathSolver(resultFetcher);

        System.out.println("Finding path");

        long start = System.currentTimeMillis();
        Path solution = solver.solve();

        double duration = (System.currentTimeMillis() - start) / 1000D;
        System.out.println("Flag is: " + solution.encoded());
        System.out.println("Took " + duration + "s");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT))) {
            writer.append("Flag: ")
                    .append(solution.encoded())
                    .append("\n")
                    .append("Took")
                    .append(" ")
                    .append(Double.toString(duration))
                    .append("s");
        }

    }
}