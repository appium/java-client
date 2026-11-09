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

import io.appium.java_client.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.support.pagefactory.ElementLocator;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageFactoryTest {

    private interface CustomWebElement extends WebElement {
    }

    @SuppressWarnings("unused")
    private static class BasePage<T extends WebElement> {
        @FindBy(id = "base")
        WebElement baseElement;

        @FindBy(id = "generic-single")
        T genericSingleElement;

        @FindBy(css = ".generic-elements")
        List<T> genericElements;

        @FindBy(css = ".generic-wildcard-elements")
        List<? extends T> genericWildcardElements;
    }

    private static class SubtypeElementPage extends BasePage<CustomWebElement> {
    }

    @SuppressWarnings("unused")
    private static class Page extends BasePage<WebElement> {
        @FindBy(id = "single")
        WebElement element;

        @FindBy(css = ".many")
        List<WebElement> elements;

        @FindBy(css = ".many-wildcard")
        List<? extends WebElement> wildcardElements;

        String untouched = "untouched";

        WebElement notAnnotatedElement;

        @FindBy(id = "list-of-strings")
        List<String> notAnElementList;

        @FindBy(id = "list-of-lower-bounded")
        List<? super WebElement> lowerBoundedElementList;

        @FindBy(id = "list-of-unbounded")
        List<?> unboundedElementList;

        @FindBy(id = "list-of-object-super")
        List<? super Object> objectSuperElementList;
    }

    private static ElementLocator locatorOf(String text) {
        return new ElementLocator() {
            @Override
            public WebElement findElement() {
                return Stubs.element(text);
            }

            @Override
            public List<WebElement> findElements() {
                return List.of(Stubs.element(text + "-1"), Stubs.element(text + "-2"));
            }
        };
    }

    @Test
    void decoratesElementsAndListsIncludingInheritedFields() {
        Page page = new Page();

        PageFactory.initElements(new DefaultFieldDecorator(field -> locatorOf(field.getName())), page);

        assertEquals("element", page.element.getText());
        assertEquals("baseElement", page.baseElement.getText());
        assertEquals("genericSingleElement", page.genericSingleElement.getText());
        assertEquals(2, page.elements.size());
        assertEquals("elements-2", page.elements.get(1).getText());
        assertEquals(2, page.wildcardElements.size());
        assertEquals("wildcardElements-1", page.wildcardElements.get(0).getText());
        assertEquals(2, page.lowerBoundedElementList.size());
        assertEquals(
                "lowerBoundedElementList-1",
                assertInstanceOf(WebElement.class, page.lowerBoundedElementList.get(0)).getText()
        );
        assertEquals(2, page.genericElements.size());
        assertEquals("genericElements-1", page.genericElements.get(0).getText());
        assertEquals(2, page.genericWildcardElements.size());
        assertEquals("genericWildcardElements-2", page.genericWildcardElements.get(1).getText());
        assertNull(page.unboundedElementList);
        assertNull(page.objectSuperElementList);

        SubtypeElementPage subtypePage = new SubtypeElementPage();
        PageFactory.initElements(new DefaultFieldDecorator(field -> locatorOf(field.getName())), subtypePage);
        assertNotNull(subtypePage.genericElements);
        assertEquals(2, subtypePage.genericElements.size());
        assertNotNull(subtypePage.genericWildcardElements);
        assertEquals(2, subtypePage.genericWildcardElements.size());
    }

    @Test
    void doesNotTouchFieldsThatAreNotElements() {
        Page page = new Page();

        PageFactory.initElements(field -> locatorOf(field.getName()), page);

        assertEquals("untouched", page.untouched);
        assertNull(page.notAnElementList);
    }

    @Test
    void elementProxiesExposeTheWrappedElement() {
        Page page = new Page();

        PageFactory.initElements(field -> locatorOf(field.getName()), page);

        WebElement wrapped = assertInstanceOf(WrapsElement.class, page.element).getWrappedElement();
        assertNotNull(wrapped);
        assertEquals("element", wrapped.getText());
    }

    @Test
    void elementProxiesReportTheLocatorWhenTheElementIsMissing() {
        Page page = new Page();

        PageFactory.initElements(field -> new ElementLocator() {
            @Override
            public WebElement findElement() {
                throw new NoSuchElementException("missing");
            }

            @Override
            public List<WebElement> findElements() {
                return List.of();
            }

            @Override
            public String toString() {
                return "the locator";
            }
        }, page);

        assertEquals("Proxy element for: the locator", page.element.toString());
        assertThrows(NoSuchElementException.class, () -> page.element.getText());
        assertTrue(page.elements.isEmpty());
    }

    @Test
    void aFactoryReturningNullLeavesTheFieldAlone() {
        Page page = new Page();

        PageFactory.initElements(field -> null, page);

        assertNull(page.element);
    }
}
