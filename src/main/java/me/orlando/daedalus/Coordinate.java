package me.orlando.daedalus;

public record Coordinate(int x, int y) {

    public static Coordinate fromPath(Path path) {
        int x = 0;
        int y = 0;

        for (char movement : path.movements()) {
            switch (movement) {
                case 'r' -> x++;
                case 'l' -> x--;
                case 'u' -> y++;
                case 'd' -> y--;
            }
        }

        return new Coordinate(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coordinate coordinate)) {
            return false;
        }

        return x == coordinate.x
                && y == coordinate.y;
    }
}
