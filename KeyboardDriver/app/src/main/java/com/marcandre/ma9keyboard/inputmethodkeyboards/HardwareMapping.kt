package com.marcandre.ma9keyboard.inputmethodkeyboards



// All special keys are negatively assigned => groups are built by primary factors
public enum class SPECIAL_HW_KEY(val value:Int){
    FUNC(-1),
    BACK(-2),
    GREEN(-3),
    RED(-4),

    LEFT(-5),
    RIGHT(-6),
    UP(-7),
    DOWN(-8),
    ENTER(-9),

    ASTRIX(-10),
    HASH(-11),
}

// This maps the raw keycode onto a keyboard that is understood
public val KEYCODE_MAPPING = mapOf<Int, Int>(
    82 to SPECIAL_HW_KEY.FUNC.value,
    5 to SPECIAL_HW_KEY.GREEN.value,
    4 to SPECIAL_HW_KEY.BACK.value,

    23 to SPECIAL_HW_KEY.ENTER.value,
    19 to SPECIAL_HW_KEY.UP.value,
    22 to SPECIAL_HW_KEY.RIGHT.value,
    20 to SPECIAL_HW_KEY.DOWN.value,
    21 to SPECIAL_HW_KEY.LEFT.value,

    8 to 1,
    9 to 2,
    10 to 3,
    11 to 4,
    12 to 5,
    13 to 6,
    14 to 7,
    15 to 8,
    16 to 9,
    7 to 0,

    17 to SPECIAL_HW_KEY.ASTRIX.value,
    18 to SPECIAL_HW_KEY.HASH.value,
)


