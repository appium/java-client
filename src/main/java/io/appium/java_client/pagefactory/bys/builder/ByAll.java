package io.appium.java_client.pagefactory.bys.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import org.openqa.seleniumone.By;
import org.openqa.seleniumone.NoSuchElementException;
import org.openqa.seleniumone.SearchContext;
import org.openqa.seleniumone.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class ByAll extends org.openqa.seleniumone.support.pagefactory.ByAll {

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
