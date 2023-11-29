package me.orlando.daedalus.solver;

import me.orlando.daedalus.Path;
import me.orlando.daedalus.Coordinate;
import me.orlando.daedalus.result.PathResultFetcher;

import java.util.*;

import static me.orlando.daedalus.Path.MOVEMENTS;

public class BfsPathSolver implements PathSolver {

    private final Queue<Path> queue = new ArrayDeque<>();
    private final Set<Coordinate> visited = new HashSet<>();
    private final Set<Coordinate> bonked = new HashSet<>();

    private final Map<Coordinate, PathResultFetcher.Result> resultMap = new HashMap<>();

    private final PathResultFetcher resultFetcher;

    public BfsPathSolver(PathResultFetcher resultFetcher) {
        this.resultFetcher = resultFetcher;
    }

    @Override
    public Path solve() throws InterruptedException {
        Path start = new Path(new char[0]);
        this.visited.add(start.toCoordinate());
        this.queue.offer(start);

        return solveBfs();
    }

    private Path solveBfs() throws InterruptedException {
        Optional<Path> pathOptional = Optional.ofNullable(queue.poll());
        while (pathOptional.isPresent()) {
            Path path = pathOptional.get();
            Coordinate coordinate = path.toCoordinate();
            PathResultFetcher.Result result = PathResultFetcher.Result.MOVING;

            if (!path.isEmpty()) {
                System.out.println(path.asString());
                result = resultMap.computeIfAbsent(coordinate, ignore -> resultFetcher.fetch(path));
//                Thread.sleep(10);
            }

            if (result == PathResultFetcher.Result.WIN) {
                return path;
            } else if (result == PathResultFetcher.Result.BONK) {
                bonked.add(coordinate);
                pathOptional = Optional.ofNullable(queue.poll());
            } else {
                process(path);
                pathOptional = Optional.ofNullable(queue.poll());
            }
        }

        return null;
    }

    private void process(Path path) {
        for (char move : MOVEMENTS) {
            addAisle(path.append(move));
        }
    }

    private void addAisle(Path path) {
        if (isValidAisle(path)) {
            this.queue.offer(path);
            this.visited.add(path.toCoordinate());
        }
    }

    private boolean isValidAisle(Path path) {
        return !isVisited(path) && !isBonked(path);
    }

    private boolean isBonked(Path path) {
        return bonked.contains(path.toCoordinate());
    }

    private boolean isVisited(Path path) {
        return visited.contains(path.toCoordinate());
    }
}
