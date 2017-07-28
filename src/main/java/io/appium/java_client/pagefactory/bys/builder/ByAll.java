package io.appium.java_client.pagefactory.bys.builder;

import static com.google.common.base.Preconditions.checkNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.function.Function;


public class ByAll extends org.openqa.selenium.support.pagefactory.ByAll {

    private By[] bys;

    private Function<SearchContext, WebElement> getSearchingFunction(By by) {
        return input -> {
            try {
                return input.findElement(by);
            } catch (NoSuchElementException e) {
                return null;
            }
        };
    }

    /**
     * @param bys is a set of {@link org.openqa.selenium.By} which forms the all possible searching.
     */
    public ByAll(By[] bys) {
        super(bys);
        checkNotNull(bys);
        if (bys.length == 0) {
            throw new IllegalArgumentException("By array should not be empty");
        }
        this.bys = bys;
    }

    @Override
    public WebElement findElement(SearchContext context) {
        for (By by : bys) {
            WebElement element = getSearchingFunction(by).apply(context);
            if (element != null) {
                return element;
            }
        }
        throw new NoSuchElementException("Cannot locate an element using " + toString());
    }
}