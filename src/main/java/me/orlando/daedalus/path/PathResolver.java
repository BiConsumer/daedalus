package me.orlando.daedalus.path;

public interface PathResolver {

    Result resolve(Path path) throws Exception;

    enum Result {
        BONK,
        MOVING,
        WIN
    }

}
