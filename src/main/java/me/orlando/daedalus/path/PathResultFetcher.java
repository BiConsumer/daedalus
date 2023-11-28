package me.orlando.daedalus.path;

public interface PathResultFetcher {

    Result fetch(Path path);

    enum Result {
        BONK,
        MOVING,
        WIN
    }

}
