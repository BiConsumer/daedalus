package me.orlando.daedalus.solver;

import me.orlando.daedalus.Path;
import me.orlando.daedalus.result.PathResultFetcher;

import java.util.*;

import static me.orlando.daedalus.Path.MOVEMENTS;

public class DfsPathSolver implements PathSolver {

    private final Deque<Path> stack = new ArrayDeque<>();
    private final Set<Path> visited = new HashSet<>();
    private final Set<Path> bonked = new HashSet<>();
    private final Map<Path, PathResultFetcher.Result> resultMap = new HashMap<>();

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
        Optional<Path> path = Optional.ofNullable(stack.peekFirst());

        while (path.isPresent()) {
            PathResultFetcher.Result result = PathResultFetcher.Result.MOVING;

            if (!path.get().isEmpty()) {
                System.out.println(path.get().asString());
                result = resultMap.computeIfAbsent(path.get(), resultFetcher::fetch);
                Thread.sleep(100);
            }

            if (result == PathResultFetcher.Result.WIN) {
                return path.get();
            } else if (result == PathResultFetcher.Result.BONK) {
                stack.pop();
                bonked.add(path.get());
            } else {
                Optional<Path> next = path.map(this::nextTraversalAisle);
                if (next.isPresent()) {
                    // Traverse next block
                    traverseNext(next.get());
                } else {
                    // Dead end, backtrack and chose alternate path
                    stack.pop();
                }
            }

            path = Optional.ofNullable(stack.peekFirst());
        }

        return null;
    }

    private void traverseNext(Path next) {
        this.visited.add(next);
        stack.push(next);
    }

    private Path nextTraversalAisle(Path path) {
        Path next = null;

        for (char move : MOVEMENTS) {
            Path appended = path.append(move);
            if (isValidAisle(appended) && !isReverse(path, move)) {
                next = appended;
                break;
            }
        }

        return next;
    }

    private boolean isValidAisle(Path path) {
        return !isVisited(path) && !isBonked(path);
    }

    private boolean isReverse(Path path, char move) {
        char last = path.lastMove();
        if (last == 'r' && move == 'l') {
            return true;
        } else if (last == 'l' && move == 'r') {
            return true;
        } else if (last == 'u' && move == 'd') {
            return true;
        } else if (last == 'd' && move == 'u') {
            return true;
        }

        return false;
    }

    private boolean isBonked(Path path) {
        return bonked.stream().anyMatch(other -> other.matches(path));
    }

    private boolean isVisited(Path path) {
        return visited.stream().anyMatch(other -> other.matches(path));
    }
}
