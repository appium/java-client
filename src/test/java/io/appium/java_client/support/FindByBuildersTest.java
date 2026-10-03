/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.appium.java_client.support;

import io.appium.java_client.support.pagefactory.ByAll;
import io.appium.java_client.support.pagefactory.ByChained;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindByBuildersTest {

    @SuppressWarnings("unused")
    private static class Page {
        @FindBy(id = "foo")
        WebElement shortForm;

        @FindBy(how = How.XPATH, using = "//a")
        WebElement longForm;

        @FindBy(id = "a", css = "b")
        WebElement conflicting;

        @FindBys({@FindBy(id = "foo"), @FindBy(className = "bar")})
        WebElement chained;

        @FindAll({@FindBy(how = How.NAME, using = "foo"), @FindBy(tagName = "bar")})
        List<WebElement> all;

        @FindBy(linkText = "a")
        @CacheLookup
        WebElement cached;
    }

    private static Field field(String name) throws NoSuchFieldException {
        return Page.class.getDeclaredField(name);
    }

    @Test
    void buildsByFromTheShortForm() throws NoSuchFieldException {
        Field f = field("shortForm");

        assertEquals(By.id("foo"), new FindBy.FindByBuilder().buildIt(f.getAnnotation(FindBy.class), f));
    }

    @Test
    void buildsByFromTheLongForm() throws NoSuchFieldException {
        Field f = field("longForm");

        assertEquals(By.xpath("//a"), new FindBy.FindByBuilder().buildIt(f.getAnnotation(FindBy.class), f));
    }

    @Test
    void rejectsMoreThanOneLocationStrategy() throws NoSuchFieldException {
        Field f = field("conflicting");

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> new FindBy.FindByBuilder().buildIt(f.getAnnotation(FindBy.class), f));
        assertTrue(e.getMessage().contains("at most one location strategy"), e.getMessage());
    }

    @Test
    void findBysBuildsAChain() throws NoSuchFieldException {
        Field f = field("chained");

        By by = new FindBys.FindByBuilder().buildIt(f.getAnnotation(FindBys.class), f);

        assertInstanceOf(ByChained.class, by);
        assertEquals("By.chained({By.id: foo,By.className: bar})", by.toString());
    }

    @Test
    void findAllBuildsAnAnyOfLookup() throws NoSuchFieldException {
        Field f = field("all");

        By by = new FindAll.FindByBuilder().buildIt(f.getAnnotation(FindAll.class), f);

        assertInstanceOf(ByAll.class, by);
        assertEquals("By.all({By.name: foo,By.tagName: bar})", by.toString());
    }

    @Test
    void cacheLookupIsARuntimeMarker() throws NoSuchFieldException {
        assertTrue(field("cached").isAnnotationPresent(CacheLookup.class));
    }

    @Test
    void howUnsetFallsBackToId() {
        assertEquals(By.id("x"), How.UNSET.buildBy("x"));
    }

    @Test
    void byIdOrNameFindsByIdFirstAndFallsBackToName() {
        WebElement byName = Stubs.element("by name");

        By by = new ByIdOrName("foo");

        assertEquals(byName, by.findElement(Stubs.context(locator -> {
            if (locator.toString().contains("name")) {
                return List.of(byName);
            }
            throw new NoSuchElementException("not by id");
        })));
    }

    @Test
    void byIdOrNameFindsAllMatches() {
        WebElement byId = Stubs.element("by id");
        WebElement byName = Stubs.element("by name");

        List<WebElement> found = new ByIdOrName("foo").findElements(Stubs.context(
                locator -> locator.toString().contains("name") ? List.of(byName) : List.of(byId)));

        assertEquals(List.of(byId, byName), found);
    }
}
