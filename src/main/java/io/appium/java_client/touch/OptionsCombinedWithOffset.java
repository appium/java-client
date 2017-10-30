package io.appium.java_client.touch;

import java.util.Map;

import static java.util.Optional.ofNullable;

public abstract class OptionsCombinedWithOffset <T extends OptionsCombinedWithOffset>
        extends ActionOptions< OptionsCombinedWithOffset<T>> {
    private ActionOptions<?> offsetOption;

    /**
     * Some actions may require some absolute offset value to be performed.
     * Invocation of this method replaces the result of previous invocation of
     * the {@link #withOffset(RelativeOffsetOption)}
     *
     * @param offset is the values of required absolute offset from the left corner of a screen.     *
     * @return self-reference
     */
    public T withOffset(AbsoluteOffsetOption offset) {
        offsetOption = offset;
        return (T) this;
    }

    /**
     * Some actions may require some relative offset value to be performed.
     * Invocation of this method replaces the result of previous invocation of
     * the {@link #withOffset(AbsoluteOffsetOption)}
     *
     * @param offset is the values of required offset from the left corner of an element.     *
     * @return self-reference
     */
    public T withOffset(RelativeOffsetOption offset) {
        offsetOption = offset;
        return (T) this;
    }

    protected void verify() {
        ofNullable(offsetOption).orElseThrow(() ->
                new IllegalArgumentException("Some relative or absolute offset should be defined. Use one of withOffset methods"));
    }

    @Override
    public Map<String, Object> build() {
        final Map<String, Object> result = super.build();
        result.putAll(offsetOption.build());
        return result;
    }
}
