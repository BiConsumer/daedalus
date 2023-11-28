package me.orlando.daedalus;

public record Vec2(int x, int y) {

    public static Vec2 fromPath(Path path) {
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

        return new Vec2(x, y);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vec2 vec2)) {
            return false;
        }

        return x == vec2.x
                && y == vec2.y;
    }
}
