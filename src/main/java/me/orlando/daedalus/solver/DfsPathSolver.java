package me.orlando.daedalus.solver;

import me.orlando.daedalus.Path;
import me.orlando.daedalus.Vec2;
import me.orlando.daedalus.result.PathResultFetcher;

import java.util.*;

import static me.orlando.daedalus.Path.MOVEMENTS;

public class DfsPathSolver implements PathSolver {

    private final Deque<Path> stack = new ArrayDeque<>();
    private final Set<Vec2> visited = new HashSet<>();
    private final Set<Vec2> bonked = new HashSet<>();
    private final Map<Vec2, PathResultFetcher.Result> resultMap = new HashMap<>();

    private final PathResultFetcher resultFetcher;

    public DfsPathSolver(PathResultFetcher resultFetcher) {
        this.resultFetcher = resultFetcher;
    }

    @Override
    public Path solve() throws Exception {
        Path start = new Path(new char[0]);
        traverseNext(start);

        return solveDfs();
    }

    private Path solveDfs() throws Exception {
        Optional<Path> pathOptional = Optional.ofNullable(stack.peekFirst());

        while (pathOptional.isPresent()) {
            PathResultFetcher.Result result = PathResultFetcher.Result.MOVING;
            Path path = pathOptional.get();
            Vec2 vec2 = Vec2.fromPath(path);

            if (!path.isEmpty()) {
                System.out.println(path.asString());
                result = resultMap.computeIfAbsent(vec2, ignore -> resultFetcher.fetch(path));
                Thread.sleep(100);
            }

            if (result == PathResultFetcher.Result.WIN) {
                return path;
            } else if (result == PathResultFetcher.Result.BONK) {
                stack.pop();
                bonked.add(vec2);
            } else {
                Optional<Path> next = pathOptional.map(this::nextTraversalAisle);
                if (next.isPresent()) {
                    // Traverse next block
                    traverseNext(next.get());
                } else {
                    // Dead end, backtrack and chose alternate path
                    stack.pop();
                }
            }

            pathOptional = Optional.ofNullable(stack.peekFirst());
        }

        return null;
    }

    private void traverseNext(Path next) {
        this.visited.add(next.toVec());
        stack.push(next);
    }

    private Path nextTraversalAisle(Path path) {
        Path next = null;

        for (char move : MOVEMENTS) {
            Path appended = path.append(move);
            if (isValidAisle(appended)) {
                next = appended;
                break;
            }
        }

        return next;
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
