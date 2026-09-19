package swingy.gameplay.mapgenerator;

public final class Tiles {

    private Tiles() {
    }

    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 4;
    public static final int LEFT = 8;

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
            {false, true , false},
            {false, true , false},
            {false, true , false}
        },
        1 | 4
    );

    public static final Tile CORNER = new Tile(
        new boolean[][]{
            {true , true , false},
            {false, true , false},
            {false, true , false}
        },
        1 | 4
    );

    public static final Tile SQUARE = new Tile(
        new boolean[][]{
            {true , true , false},
            {true , true , false},
            {false, false, false}
        },
        1 | 8
    );

    public static final Tile T_JUNCTION = new Tile(
        new boolean[][]{
            {true , true , true},
            {false, true , false},
            {false, true , false}
        },
        1 | 4
    );

    public static final Tile DEAD_END = new Tile(
        new boolean[][]{
            {true , false, false},
            {true , true , false},
            {true , false, false}
        },
        8
    );

    public static final Tile CROSS = new Tile(
        new boolean[][]{
            {false, true , false},
            {true , true , true},
            {false, true , false}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile EMPTY = new Tile(
        new boolean[][]{
            {true , true , true},
            {true , true , true},
            {true , true , true}
        },
        1 | 2 | 4 | 8
    );

    // =========================================================
    // SPECIAL PATHS
    // =========================================================

    public static final Tile WEIRD_PATH = new Tile(
        new boolean[][]{
            {true , true , true},
            {false, false, true},
            {true , true , true}
        },
        1 | 2 | 4
    );

    public static final Tile WEIRDER_PATH = new Tile(
        new boolean[][]{
            {true , true , false},
            {false, true , false},
            {true , true , false}
        },
        1 | 4
    );

    public static final Tile SHORTER_DEAD_END = new Tile(
        new boolean[][]{
            {false, false, false},
            {true , false, false},
            {false, false, false}
        },
        8
    );

    public static final Tile LARGER_DEAD_END = new Tile(
        new boolean[][]{
            {false, false, false},
            {true , true , false},
            {false, false, false}
        },
        8
    );

    // =========================================================
    // CROSS VARIANTS
    // =========================================================

    public static final Tile SEMICROSS = new Tile(
        new boolean[][]{
            {false, true , false},
            {true , true , true},
            {true , true , true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile SENIDEMICROSS = new Tile(
        new boolean[][]{
            {true , true , false},
            {true , true , true},
            {false, true , true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile UOSEMIDEMICROSS = new Tile(
        new boolean[][]{
            {true , false, false},
            {true , true , true},
            {true , true , true}
        },
        2 | 4 | 8
    );

    // =========================================================
    // GENERATED: 2 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_2_01 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, false, false},
            {false, true , true}
        },
        4
    );

    // =========================================================
    // GENERATED: 3 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_3_01 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, false, false},
            {true , true , true}
        },
        4
    );

    public static final Tile SHAPE_3_02 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, false, true},
            {false, true , true}
        },
        2 | 4
    );

    public static final Tile SHAPE_3_03 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, true , false},
            {false, true , true}
        },
        4
    );

    public static final Tile SHAPE_3_04 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, true , true},
            {false, true , false}
        },
        2 | 4
    );

    // =========================================================
    // GENERATED: 4 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_4_01 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, false, true},
            {true , true , true}
        },
        2 | 4
    );

    public static final Tile SHAPE_4_02 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, true , true},
            {true , true , false}
        },
        2 | 4
    );

    public static final Tile SHAPE_4_03 = new Tile(
        new boolean[][]{
            {false, false, false},
            {true , true , true},
            {false, true , false}
        },
        2 | 4 | 8
    );

    // =========================================================
    // GENERATED: 5 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_5_01 = new Tile(
        new boolean[][]{
            {false, false, false},
            {false, true , true},
            {true , true , true}
        },
        2 | 4
    );

    public static final Tile SHAPE_5_02 = new Tile(
        new boolean[][]{
            {false, false, false},
            {true , false, true},
            {true , true , true}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_5_03 = new Tile(
        new boolean[][]{
            {false, false, false},
            {true , true , true},
            {false, true , true}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_5_04 = new Tile(
        new boolean[][]{
            {false, false, true},
            {false, false, true},
            {true , true , true}
        },
        2 | 4
    );

    public static final Tile SHAPE_5_05 = new Tile(
        new boolean[][]{
            {false, false, true},
            {false, true , true},
            {true , true , false}
        },
        2 | 4
    );

    public static final Tile SHAPE_5_06 = new Tile(
        new boolean[][]{
            {false, false, true},
            {true , true , true},
            {false, true , false}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_5_07 = new Tile(
        new boolean[][]{
            {false, false, true},
            {true , true , true},
            {true , false, false}
        },
        2 | 8
    );

    // =========================================================
    // GENERATED: 6 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_6_01 = new Tile(
        new boolean[][]{
            {false, false, false},
            {true , true , true},
            {true , true , true}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_6_02 = new Tile(
        new boolean[][]{
            {false, false, true},
            {false, true , true},
            {true , true , true}
        },
        2 | 4
    );

    public static final Tile SHAPE_6_03 = new Tile(
        new boolean[][]{
            {false, false, true},
            {true , false, true},
            {true , true , true}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_6_04 = new Tile(
        new boolean[][]{
            {false, false, true},
            {true , true , true},
            {false, true , true}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_6_05 = new Tile(
        new boolean[][]{
            {false, false, true},
            {true , true , true},
            {true , false, true}
        },
        2 | 8
    );

    public static final Tile SHAPE_6_06 = new Tile(
        new boolean[][]{
            {false, false, true},
            {true , true , true},
            {true , true , false}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_6_07 = new Tile(
        new boolean[][]{
            {false, true , false},
            {true , true , true},
            {false, true , true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile SHAPE_6_08 = new Tile(
        new boolean[][]{
            {false, true , false},
            {true , true , true},
            {true , false, true}
        },
        1 | 2 | 8
    );

    // =========================================================
    // GENERATED: 7 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_7_01 = new Tile(
        new boolean[][]{
            {false, true , true},
            {true , false, true},
            {true , true , true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile SHAPE_7_02 = new Tile(
        new boolean[][]{
            {false, true , true},
            {true , true , false},
            {true , true , true}
        },
        1 | 4 | 8
    );

    public static final Tile SHAPE_7_03 = new Tile(
        new boolean[][]{
            {true , false, true},
            {true , true , true},
            {true , false, true}
        },
        2 | 8
    );

    // =========================================================
    // GENERATED: 8 FILLED CELLS
    // =========================================================

    public static final Tile SHAPE_8_01 = new Tile(
        new boolean[][]{
            {false, true , true},
            {true , true , true},
            {true , true , true}
        },
        1 | 2 | 4 | 8
    );

    public static final Tile SHAPE_8_02 = new Tile(
        new boolean[][]{
            {true , false, true},
            {true , true , true},
            {true , true , true}
        },
        2 | 4 | 8
    );

    public static final Tile SHAPE_8_03 = new Tile(
        new boolean[][]{
            {true , true , true},
            {true , false, true},
            {true , true , true}
        },
        1 | 2 | 4 | 8
    );

    // =========================================================
    // ALL BASE TILES
    // =========================================================

    public static final Tile[] ALL = {
        STRAIGHT,
        CORNER,
        SQUARE,
        T_JUNCTION,
        DEAD_END,
        CROSS,
        EMPTY,
        WEIRD_PATH,
        WEIRDER_PATH,
        SHORTER_DEAD_END,
        LARGER_DEAD_END,
        SEMICROSS,
        SENIDEMICROSS,
        UOSEMIDEMICROSS,
        SHAPE_2_01,
        SHAPE_3_01,
        SHAPE_3_02,
        SHAPE_3_03,
        SHAPE_3_04,
        SHAPE_4_01,
        SHAPE_4_02,
        SHAPE_4_03,
        SHAPE_5_01,
        SHAPE_5_02,
        SHAPE_5_03,
        SHAPE_5_04,
        SHAPE_5_05,
        SHAPE_5_06,
        SHAPE_5_07,
        SHAPE_6_01,
        SHAPE_6_02,
        SHAPE_6_03,
        SHAPE_6_04,
        SHAPE_6_05,
        SHAPE_6_06,
        SHAPE_6_07,
        SHAPE_6_08,
        SHAPE_7_01,
        SHAPE_7_02,
        SHAPE_7_03,
        SHAPE_8_01,
        SHAPE_8_02,
        SHAPE_8_03
    };
}