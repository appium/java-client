package io.appium.java_client.touch.offset;

import static java.util.Optional.ofNullable;

import io.appium.java_client.touch.ActionOptions;

import java.util.Map;

public abstract class AbstractOptionCombinedWithPosition<T extends AbstractOptionCombinedWithPosition<T>>
        extends ActionOptions<AbstractOptionCombinedWithPosition<T>> {
    private ActionOptions<?> positionOption;

    /**
     * Some actions may require coordinates. Invocation of this method
     * replaces the result of previous {@link #withElement(ElementOption)} invocation.
     *
     * @param positionOption required coordinates.     *
     * @return self-reference
     */
    public T withPosition(PointOption positionOption) {
        this.positionOption = positionOption;
        return (T) this;
    }

    /**
     * Most of touch action may use position which is relative to some element. In order to unify
     * this behaviour this method was added. Invocation of this method
     * replaces the result of previous {@link #withPosition(PointOption)} invocation.
     *
     * @param element required position which is relative to some element
     * @return self-reference
     */
    public T withElement(ElementOption element) {
        positionOption = element;
        return (T) this;
    }

    protected void verify() {
        ofNullable(positionOption).orElseThrow(() ->
                new IllegalArgumentException("Some coordinates or an offset from an element should "
                        + "be defined. Use withPosition or withElement methods"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.putAll(positionOption.build());
        return result;
    }
}
