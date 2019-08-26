package io.appium.java_client.pagefactory.bys.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import io.appium.java_client.selenium.By;
import io.appium.java_client.selenium.SearchContext;
import io.appium.java_client.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class ByAll extends io.appium.java_client.selenium.support.pagefactory.ByAll {

    private final List<By> bys;

    private Function<SearchContext, Optional<WebElement>> getSearchingFunction(By by) {
        return input -> {
            try {
                return Optional.of(input.findElement(by));
            } catch (NoSuchElementException e) {
                return Optional.empty();
            }
        };
    }

    /**
     * Finds all elements that matches any of the locators in sequence.
     *
     * @param bys is a set of {@link By} which forms the all possible searching.
     */
    public ByAll(By[] bys) {
        super(bys);
        checkNotNull(bys);

        this.bys = Arrays.asList(bys);

        checkArgument(!this.bys.isEmpty(), "By array should not be empty");
    }

    @Override
    public WebElement findElement(SearchContext context) {
        return bys.stream()
                .map(by -> getSearchingFunction(by).apply(context))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Cannot locate an element using " + toString()));
    }
}
