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

package io.appium.java_client.pagefactory;

import io.appium.java_client.support.CacheLookup;
import io.appium.java_client.support.FindAll;
import io.appium.java_client.support.FindBy;
import io.appium.java_client.support.FindBys;
import io.appium.java_client.support.How;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SeleniumAnnotationsCompatTest {

    @SuppressWarnings("unused")
    private static class Page {
        @org.openqa.selenium.support.FindBy(id = "foo")
        @org.openqa.selenium.support.CacheLookup
        WebElement seleniumShort;

        @org.openqa.selenium.support.FindBy(how = org.openqa.selenium.support.How.XPATH, using = "//a")
        WebElement seleniumLong;

        @org.openqa.selenium.support.FindBys({
            @org.openqa.selenium.support.FindBy(id = "foo"),
            @org.openqa.selenium.support.FindBy(className = "bar")})
        WebElement seleniumChained;

        @org.openqa.selenium.support.FindAll({
            @org.openqa.selenium.support.FindBy(name = "foo"),
            @org.openqa.selenium.support.FindBy(tagName = "bar")})
        List<WebElement> seleniumAll;

        @FindBy(id = "appium")
        @org.openqa.selenium.support.FindBy(id = "selenium")
        WebElement both;

        @FindBy(id = "appium")
        @CacheLookup
        WebElement appiumOnly;

        WebElement none;
    }

    private static Field field(String name) throws NoSuchFieldException {
        return Page.class.getDeclaredField(name);
    }

    private static DefaultElementByBuilder builderFor(String fieldName) throws NoSuchFieldException {
        DefaultElementByBuilder builder = new DefaultElementByBuilder(null, null);
        builder.setAnnotated(field(fieldName));
        return builder;
    }

    @Test
    void readsSeleniumFindByAttributes() throws NoSuchFieldException {
        FindBy findBy = SeleniumAnnotationsCompat.find(field("seleniumShort"), FindBy.class);

        assertNotNull(findBy);
        assertEquals("foo", findBy.id());
        assertEquals("", findBy.css());
        assertEquals(How.UNSET, findBy.how());
        assertEquals(FindBy.class, findBy.annotationType());
    }

    @Test
    void convertsTheSeleniumHowEnum() throws NoSuchFieldException {
        FindBy findBy = SeleniumAnnotationsCompat.find(field("seleniumLong"), FindBy.class);

        assertNotNull(findBy);
        assertEquals(How.XPATH, findBy.how());
        assertEquals("//a", findBy.using());
    }

    @Test
    void convertsNestedAnnotationArrays() throws NoSuchFieldException {
        FindBys findBys = SeleniumAnnotationsCompat.find(field("seleniumChained"), FindBys.class);
        FindAll findAll = SeleniumAnnotationsCompat.find(field("seleniumAll"), FindAll.class);

        assertNotNull(findBys);
        assertNotNull(findAll);
        assertEquals(2, findBys.value().length);
        assertEquals("bar", findBys.value()[1].className());
        assertEquals("foo", findAll.value()[0].name());
        assertEquals("bar", findAll.value()[1].tagName());
    }

    @Test
    void detectsTheSeleniumCacheLookupMarker() throws NoSuchFieldException {
        assertTrue(SeleniumAnnotationsCompat.isPresent(field("seleniumShort"), CacheLookup.class));
        assertFalse(SeleniumAnnotationsCompat.isPresent(field("seleniumLong"), CacheLookup.class));
    }

    @Test
    void appiumAnnotationsTakePrecedence() throws NoSuchFieldException {
        FindBy findBy = SeleniumAnnotationsCompat.find(field("both"), FindBy.class);

        assertNotNull(findBy);
        assertEquals("appium", findBy.id());
    }

    @Test
    void returnsNullWhenNothingIsPresent() throws NoSuchFieldException {
        assertNull(SeleniumAnnotationsCompat.find(field("none"), FindBy.class));
        assertNull(SeleniumAnnotationsCompat.find(field("appiumOnly"), FindAll.class));
    }

    @Test
    void defaultElementByBuilderUnderstandsSeleniumAnnotations() throws NoSuchFieldException {
        assertEquals(By.id("foo"), builderFor("seleniumShort").buildDefaultBy());
        assertTrue(builderFor("seleniumShort").isLookupCached());
        assertEquals(By.xpath("//a"), builderFor("seleniumLong").buildDefaultBy());
        assertEquals("By.chained({By.id: foo,By.className: bar})",
                builderFor("seleniumChained").buildDefaultBy().toString());
        assertEquals("By.all({By.name: foo,By.tagName: bar})",
                builderFor("seleniumAll").buildDefaultBy().toString());
    }

    @Test
    void defaultElementByBuilderStillUnderstandsAppiumAnnotations() throws NoSuchFieldException {
        assertEquals(By.id("appium"), builderFor("appiumOnly").buildDefaultBy());
        assertTrue(builderFor("appiumOnly").isLookupCached());
        assertFalse(builderFor("none").isLookupCached());
    }
}
