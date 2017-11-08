package io.appium.java_client.touch.offset;

import static java.util.Optional.ofNullable;

import io.appium.java_client.touch.ActionOptions;

import java.util.Map;

public abstract class AbstractOptionCombinedWithPosition<T extends AbstractOptionCombinedWithPosition<T, S>,
        S extends AbstractPositionOption>
        extends ActionOptions<AbstractOptionCombinedWithPosition<T, S>> {
    private ActionOptions<?> offsetOption;

    /**
     * Some actions may require offset. It may be option which contains x,y - offset
     * from the left corner of the screen or from the current point on the screen
     * or x,y - offset from the left corner of the element. Invocation of this method
     * replaces the result of previous {@link #withElement(ElementOption)} invocation.
     *
     * @param offset required offset option.     *
     * @return self-reference
     */
    public T withPosition(S offset) {
        offsetOption = offset;
        return (T) this;
    }

    /**
     * Most of touch action may use position which is relative to some element. In order to unify
     * this behaviour this method was added. Invocation of this method
     * replaces the result of previous {@link #withPosition(AbstractPositionOption)} invocation.
     *
     * @param element required position which is relative to some element
     * @return self-reference
     */
    public T withElement(ElementOption element) {
        offsetOption = element;
        return (T) this;
    }

    protected void verify() {
        ofNullable(offsetOption).orElseThrow(() ->
                new IllegalArgumentException("Some relative or absolute position should "
                        + "be defined. Use withPosition or withElement methods"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.putAll(offsetOption.build());
        return result;
    }
}
