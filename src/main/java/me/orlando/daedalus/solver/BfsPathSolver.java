package me.orlando.daedalus.solver;

import me.orlando.daedalus.Path;
import me.orlando.daedalus.Vec2;
import me.orlando.daedalus.result.PathResultFetcher;

import java.util.*;

import static me.orlando.daedalus.Path.MOVEMENTS;

public class BfsPathSolver implements PathSolver {

    private final Queue<Path> queue = new ArrayDeque<>();
    private final Set<Vec2> visited = new HashSet<>();
    private final Set<Vec2> bonked = new HashSet<>();

    private final Map<Vec2, PathResultFetcher.Result> resultMap = new HashMap<>();

    private final PathResultFetcher resultFetcher;

    public BfsPathSolver(PathResultFetcher resultFetcher) {
        this.resultFetcher = resultFetcher;
    }

    @Override
    public Path solve() throws InterruptedException {
        Path start = new Path(new char[0]);
        this.visited.add(start.toVec());
        this.queue.offer(start);

        return solveBfs();
    }

    private Path solveBfs() throws InterruptedException {
        Optional<Path> pathOptional = Optional.ofNullable(queue.poll());
        while (pathOptional.isPresent()) {
            Path path = pathOptional.get();
            Vec2 vec2 = path.toVec();
            PathResultFetcher.Result result = PathResultFetcher.Result.MOVING;

            if (!path.isEmpty()) {
                System.out.println(path.asString());
                result = resultMap.computeIfAbsent(vec2, ignore -> resultFetcher.fetch(path));
//                Thread.sleep(10);
            }

            if (result == PathResultFetcher.Result.WIN) {
                return path;
            } else if (result == PathResultFetcher.Result.BONK) {
                bonked.add(vec2);
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
            this.visited.add(path.toVec());
        }
    }

    private boolean isValidAisle(Path path) {
        return !isVisited(path) && !isBonked(path);
    }

    private boolean isBonked(Path path) {
        return bonked.contains(path.toVec());
    }

    private boolean isVisited(Path path) {
        return visited.contains(path.toVec());
    }
}
