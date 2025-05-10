package io.appium.java_client.android.nativekey;

public enum KeyEventMetaModifier {
    /**
     * SHIFT key locked in CAPS mode.
     * Reserved for use by MetaKeyKeyListener for a published constant in its API.
     */
    CAP_LOCKED(0x100),
    /**
     * ALT key locked.
     * Reserved for use by MetaKeyKeyListener for a published constant in its API.
     */
    ALT_LOCKED(0x200),
    /**
     * SYM key locked.
     * Reserved for use by MetaKeyKeyListener for a published constant in its API.
     */
    SYM_LOCKED(0x400),
    /**
     * Text is in selection mode.
     * Reserved for use by MetaKeyKeyListener for a private unpublished constant
     * in its API that is currently being retained for legacy reasons.
     */
    SELECTING(0x800),
    /**
     * This mask is used to check whether one of the ALT meta keys is pressed.
     *
     * @see AndroidKey#ALT_LEFT
     * @see AndroidKey#ALT_RIGHT
     */
    ALT_ON(0x02),
    /**
     * his mask is used to check whether the left ALT meta key is pressed.
     *
     * @see AndroidKey#ALT_LEFT
     */
    ALT_LEFT_ON(0x10),
    /**
     * This mask is used to check whether the right the ALT meta key is pressed.
     *
     * @see AndroidKey#ALT_RIGHT
     */
    ALT_RIGHT_ON(0x20),
    /**
     * This mask is used to check whether one of the SHIFT meta keys is pressed.
     *
     * @see AndroidKey#SHIFT_LEFT
     * @see AndroidKey#SHIFT_RIGHT
     */
    SHIFT_ON(0x1),
    /**
     * This mask is used to check whether the left SHIFT meta key is pressed.
     *
     * @see AndroidKey#SHIFT_LEFT
     */
    SHIFT_LEFT_ON(0x40),
    /**
     * This mask is used to check whether the right SHIFT meta key is pressed.
     *
     * @see AndroidKey#SHIFT_RIGHT
     */
    SHIFT_RIGHT_ON(0x80),
    /**
     * This mask is used to check whether the SYM meta key is pressed.
     */
    SYM_ON(0x4),
    /**
     * This mask is used to check whether the FUNCTION meta key is pressed.
     */
    FUNCTION_ON(0x8),
    /**
     * This mask is used to check whether one of the CTRL meta keys is pressed.
     *
     * @see AndroidKey#CTRL_LEFT
     * @see AndroidKey#CTRL_RIGHT
     */
    CTRL_ON(0x1000),
    /**
     * This mask is used to check whether the left CTRL meta key is pressed.
     *
     * @see AndroidKey#CTRL_LEFT
     */
    CTRL_LEFT_ON(0x2000),
    /**
     * This mask is used to check whether the right CTRL meta key is pressed.
     *
     * @see AndroidKey#CTRL_RIGHT
     */
    CTRL_RIGHT_ON(0x4000),
    /**
     * This mask is used to check whether one of the META meta keys is pressed.
     *
     * @see AndroidKey#META_LEFT
     * @see AndroidKey#META_RIGHT
     */
    META_ON(0x10000),
    /**
     * This mask is used to check whether the left META meta key is pressed.
     *
     * @see AndroidKey#META_LEFT
     */
    META_LEFT_ON(0x20000),
    /**
     * This mask is used to check whether the right META meta key is pressed.
     *
     * @see AndroidKey#META_RIGHT
     */
    META_RIGHT_ON(0x40000),
    /**
     * This mask is used to check whether the CAPS LOCK meta key is on.
     *
     * @see AndroidKey#CAPS_LOCK
     */
    CAPS_LOCK_ON(0x100000),
    /**
     * This mask is used to check whether the NUM LOCK meta key is on.
     *
     * @see AndroidKey#NUM_LOCK
     */
    NUM_LOCK_ON(0x200000),
    /**
     * This mask is used to check whether the SCROLL LOCK meta key is on.
     *
     * @see AndroidKey#SCROLL_LOCK
     */
    SCROLL_LOCK_ON(0x400000),
    /**
     * This mask is a combination of {@link #SHIFT_ON}, {@link #SHIFT_LEFT_ON}
     * and {@link #SHIFT_RIGHT_ON}.
     */
    SHIFT_MASK(SHIFT_ON.getValue() | SHIFT_LEFT_ON.getValue() | SHIFT_RIGHT_ON.getValue()),
    /**
     * This mask is a combination of {@link #ALT_ON}, {@link #ALT_LEFT_ON}
     * and {@link #ALT_RIGHT_ON}.
     */
    ALT_MASK(ALT_ON.getValue() | ALT_LEFT_ON.getValue() | ALT_RIGHT_ON.getValue()),
    /**
     * This mask is a combination of {@link #CTRL_ON}, {@link #CTRL_LEFT_ON}
     * and {@link #CTRL_RIGHT_ON}.
     */
    CTRL_MASK(CTRL_ON.getValue() | CTRL_LEFT_ON.getValue() | CTRL_RIGHT_ON.getValue()),
    /**
     * This mask is a combination of {@link #META_ON}, {@link #META_LEFT_ON}
     * and {@link #META_RIGHT_ON}.
     */
    META_MASK(META_ON.getValue() | META_LEFT_ON .getValue() | META_RIGHT_ON.getValue());

    private final int value;

    KeyEventMetaModifier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
