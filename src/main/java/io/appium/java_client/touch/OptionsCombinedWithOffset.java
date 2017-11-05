package io.appium.java_client.touch;

import static java.util.Optional.ofNullable;

import java.util.Map;

public abstract class OptionsCombinedWithOffset<T extends OptionsCombinedWithOffset>
        extends ActionOptions<OptionsCombinedWithOffset<T>> {
    private ActionOptions<?> offsetOption;

    /**
     * Some actions may require offset. It may be option which contains x,y - offset
     * from the left corner of the screen or from the current point on the screen
     * or x,y - offset from the left corner of the element.
     *
     * @param offset required absolute offset option.     *
     * @return self-reference
     */
    public <S extends PositionOffsetOption> T withOffset(S offset) {
        offsetOption = offset;
        return (T) this;
    }

    protected void verify() {
        ofNullable(offsetOption).orElseThrow(() ->
                new IllegalArgumentException("Some relative or absolute offset should "
                        + "be defined. Use one of withOffset methods"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.putAll(offsetOption.build());
        return result;
    }
}
