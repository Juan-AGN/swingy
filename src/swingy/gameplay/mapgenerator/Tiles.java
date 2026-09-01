package swingy.gameplay.mapgenerator;

public final class Tiles {

    private Tiles() {
    }

    /*
     * Connection masks:
     *
     * TOP    = 1
     * RIGHT  = 2
     * BOTTOM = 4
     * LEFT   = 8
     */

    // =========================================================
    // BASIC TILES
    // =========================================================

    public static final Tile STRAIGHT = new Tile(
        new boolean[][]{
            {false, true,  false},
            {false, true,  false},
            {false, true,  false}
        },
        1 | 4
    );

    public static final Tile CORNER = new Tile(
        new boolean[][]{
            {true,  true,  false},
            {false, true,  false},
            {false, true,  false}
        },
        1 | 2
    );

    public static final Tile T_JUNCTION = new Tile(
        new boolean[][]{
            {true,  true,  true},
            {false, true,  false},
            {false, true,  false}
        },
        1 | 2 | 8
    );

    public static final Tile DEAD_END = new Tile(
        new boolean[][]{
            {true, false,  false},
            {true, true,  false},
            {true, false, false}
        },
        1
    );

    public static final Tile CROSS = new Tile(
        new boolean[][]{
            {false, true,  false},
            {true,  true,  true},
            {false, true,  false}
        },
        1 | 2 | 4 | 8
    );

    // Completely open tile
    public static final Tile EMPTY = new Tile(
        new boolean[][]{
            {true, true, true},
            {true, true, true},
            {true, true, true}
        },
        1 | 2 | 4 | 8
    );


    // =========================================================
    // SPECIAL PATHS
    // =========================================================

    public static final Tile WEIRD_PATH = new Tile(
        new boolean[][]{
            {true,  true,  true},
            {false, false, true},
            {true,  true,  true}
        },
        8 | 2
    );

    public static final Tile WEIRDER_PATH = new Tile(
        new boolean[][]{
            {true,  true,  false},
            {false, true,  false},
            {true,  true,  false}
        },
        8 | 2
    );

    public static final Tile SHORTER_DEAD_END = new Tile(
        new boolean[][]{
            {false, false, false},
            {true,  false, false},
            {false, false, false}
        },
        8
    );

    public static final Tile LARGER_DEAD_END = new Tile(
        new boolean[][]{
            {false, false, false},
            {true,  true,  false},
            {false, false, false}
        },
        8
    );


    // =========================================================
    // CROSS VARIANTS
    // =========================================================

    public static final Tile CROSS_PLUS = new Tile(
        new boolean[][]{
            {false, true,  false},
            {true,  true,  true},
            {false, true,  false}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile SEMICROSS = new Tile(
        new boolean[][]{
            {false, true,  false},
            {true,  true,  true},
            {true,  true,  true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile SENIDEMICROSS = new Tile(
        new boolean[][]{
            {true,  true,  false},
            {true,  true,  true},
            {false, true,  true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile UOSEMIDEMICROSS = new Tile(
        new boolean[][]{
            {true,  false, false},
            {true,  true,  true},
            {true,  true,  true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile HEARTH = new Tile(
        new boolean[][]{
            {true,  true,  false},
            {true,  true,  true},
            {false, true,  true}
        },
        1 | 2 | 4 | 8
    );


    // =========================================================
    // ALL BASE TILES
    // =========================================================

    public static final Tile[] ALL = {
        STRAIGHT,
        CORNER,
        T_JUNCTION,
        DEAD_END,
        CROSS,
        EMPTY,

        WEIRD_PATH,
        WEIRDER_PATH,
        SHORTER_DEAD_END,
        LARGER_DEAD_END,

        CROSS_PLUS,
        SEMICROSS,
        SENIDEMICROSS,
        UOSEMIDEMICROSS,
        HEARTH
    };
}