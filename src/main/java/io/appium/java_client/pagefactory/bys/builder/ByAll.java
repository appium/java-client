package io.appium.java_client.pagefactory.bys.builder;

import static com.google.common.base.Preconditions.checkNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class ByAll extends org.openqa.selenium.support.pagefactory.ByAll {

    private List<By> bys;

    private Function<SearchContext, Optional<WebElement>> getSearchingFunction(By by) {
        return input -> {
            try {
                return input.findElement(by);
            } catch (NoSuchElementException e) {
                return Optional.empty();
            }
        };
    }

    /**
     * @param bys is a set of {@link org.openqa.selenium.By} which forms the all possible searching.
     */
    public ByAll(By[] bys) {
        super(bys);
        checkNotNull(bys);

        this.bys = Arrays.asList(bys);
        if (this.bys.isEmpty()) {
            throw new IllegalArgumentException("By array should not be empty");
        }
    }

    @Override
    public WebElement findElement(SearchContext context) {
        for (By by : bys) {
            Optional<WebElement> element = getSearchingFunction(by).apply(context);
            if (element.isPresent()) {
                return element.get();
            }
        }
        throw new NoSuchElementException("Cannot locate an element using " + toString());
    }
}