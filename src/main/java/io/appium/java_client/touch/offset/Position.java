package io.appium.java_client.touch.offset;

/**
 * This is the default superclass of the {@link AbstractOptionCombinedWithPosition}
 * @param <S> is the sub type of {@link AbstractPositionOption} which instances should be
 *           consumed.
 */
public class Position<S extends AbstractPositionOption> extends AbstractOptionCombinedWithPosition<Position<S>, S> {

    public static <T extends AbstractPositionOption> Position<T> position() {
        return new Position<T>();
    }
}
