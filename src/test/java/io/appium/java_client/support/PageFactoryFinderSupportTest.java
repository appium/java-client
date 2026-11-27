package io.appium.java_client.support;

import io.appium.java_client.support.pagefactory.ByAll;
import io.appium.java_client.support.pagefactory.ByChained;
import io.appium.java_client.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.support.pagefactory.ElementLocator;
import io.appium.java_client.support.pagefactory.internal.PageFactoryFinderSupport;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PageFactoryFinderSupportTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @PageFactoryFinder(TestIdBuilder.class)
    @interface TestId {
        String value() default "";
    }

    private static final class TestIdBuilder extends AbstractFindByBuilder<TestId> {
        @Override
        public By buildIt(TestId annotation, @Nullable Field field) {
            String id = annotation.value().isEmpty() && field != null
                    ? field.getName()
                    : annotation.value();
            if ("__null__".equals(id)) {
                return null;
            }
            if ("__invalid__".equals(id)) {
                throw new IllegalArgumentException("Invalid test id");
            }
            return By.cssSelector("[data-testid='" + id + "']");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @PageFactoryFinder(NoZeroArgConstructorBuilder.class)
    @interface BrokenFinder {
    }

    private static final class NoZeroArgConstructorBuilder extends AbstractFindByBuilder<BrokenFinder> {
        NoZeroArgConstructorBuilder(String unused) {
        }

        @Override
        public By buildIt(BrokenFinder annotation, @Nullable Field field) {
            return By.id("unreachable");
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    @org.openqa.selenium.support.PageFactoryFinder(LegacyRoleBuilder.class)
    @interface LegacyRole {
        String value();
    }

    static final class LegacyRoleBuilder
            extends org.openqa.selenium.support.AbstractFindByBuilder<LegacyRole> {
        @Override
        public By buildIt(LegacyRole annotation, Field field) {
            return By.cssSelector("[role='" + annotation.value() + "']");
        }
    }

    @SuppressWarnings("unused")
    private static class SamplePage {
        @FindBy(id = "save")
        @CacheLookup
        WebElement standard;

        @FindBys({@FindBy(id = "form"), @FindBy(name = "email")})
        WebElement chained;

        @FindAll({@FindBy(id = "primary"), @FindBy(id = "secondary")})
        WebElement any;

        @org.openqa.selenium.support.FindBys({
            @org.openqa.selenium.support.FindBy(id = "form"),
            @org.openqa.selenium.support.FindBy(name = "email")
        })
        WebElement seleniumChained;

        @org.openqa.selenium.support.FindAll({
            @org.openqa.selenium.support.FindBy(id = "primary"),
            @org.openqa.selenium.support.FindBy(id = "secondary")
        })
        WebElement seleniumAll;

        @TestId("submit-btn")
        WebElement customSingle;

        @TestId
        List<WebElement> inferredItems;

        @FindBy(id = "appium-list")
        List<WebElement> appiumFindByList;

        @TestId("__null__")
        WebElement customNullFallback;

        @TestId("__invalid__")
        WebElement customInvalid;

        @LegacyRole("listitem")
        @org.openqa.selenium.support.CacheLookup
        WebElement legacySingleFallback;

        @LegacyRole("listitem")
        List<WebElement> legacyOnlyList;

        @org.openqa.selenium.support.FindBy(id = "selenium-list")
        List<WebElement> seleniumOnlyList;

        @FindBy(id = "explicit-legacy")
        @LegacyRole("ignored")
        WebElement findByWithLegacy;

        @FindBy(id = "appium-wins")
        @org.openqa.selenium.support.FindBy(id = "selenium-ignored")
        WebElement bothBuiltins;

        @TestId("one")
        @FindBy(id = "two")
        WebElement conflicting;

        @BrokenFinder
        WebElement brokenBuilderField;

        List<WebElement> unannotatedList;

        WebElement fallbackByName;
    }

    private static Field field(String name) throws NoSuchFieldException {
        return SamplePage.class.getDeclaredField(name);
    }

    private static By buildByOrDefault(Field field) {
        By by = PageFactoryFinderSupport.buildBy(field);
        return by != null ? by : new ByIdOrName(field.getName());
    }

    private static ElementLocator locatorFor(Field field) {
        By by = buildByOrDefault(field);
        return new ElementLocator() {
            @Override
            public WebElement findElement() {
                return Stubs.element(by.toString());
            }

            @Override
            public List<WebElement> findElements() {
                return List.of(Stubs.element(by.toString()));
            }
        };
    }

    @Test
    void builtinAnnotationsCarryPageFactoryFinderMetadata() {
        assertEquals(FindBy.FindByBuilder.class, FindBy.class.getAnnotation(PageFactoryFinder.class).value());
        assertEquals(FindBys.FindByBuilder.class, FindBys.class.getAnnotation(PageFactoryFinder.class).value());
        assertEquals(FindAll.FindByBuilder.class, FindAll.class.getAnnotation(PageFactoryFinder.class).value());
    }

    @Test
    void buildsBuiltinCustomLegacyAndFallbackLocators() throws NoSuchFieldException {
        Field standard = field("standard");
        assertEquals(By.id("save"), PageFactoryFinderSupport.buildBy(standard));
        assertTrue(PageFactoryFinderSupport.isCompatibleAnnotationPresent(standard, CacheLookup.class));

        assertInstanceOf(ByChained.class, PageFactoryFinderSupport.buildBy(field("chained")));
        assertInstanceOf(ByAll.class, PageFactoryFinderSupport.buildBy(field("any")));
        assertInstanceOf(ByChained.class, PageFactoryFinderSupport.buildBy(field("seleniumChained")));
        assertInstanceOf(ByAll.class, PageFactoryFinderSupport.buildBy(field("seleniumAll")));

        Field customSingle = field("customSingle");
        assertEquals(
                By.cssSelector("[data-testid='submit-btn']"),
                PageFactoryFinderSupport.buildBy(customSingle)
        );
        assertFalse(PageFactoryFinderSupport.isCompatibleAnnotationPresent(customSingle, CacheLookup.class));

        assertEquals(
                By.cssSelector("[data-testid='inferredItems']"),
                PageFactoryFinderSupport.buildBy(field("inferredItems"))
        );

        assertNull(PageFactoryFinderSupport.buildBy(field("fallbackByName")));
        assertNull(PageFactoryFinderSupport.buildBy(field("customNullFallback")));

        Field legacy = field("legacySingleFallback");
        assertNull(PageFactoryFinderSupport.buildBy(legacy));
        assertTrue(PageFactoryFinderSupport.isCompatibleAnnotationPresent(legacy, CacheLookup.class));

        assertEquals(By.id("explicit-legacy"), PageFactoryFinderSupport.buildBy(field("findByWithLegacy")));
        assertEquals(By.id("appium-wins"), PageFactoryFinderSupport.buildBy(field("bothBuiltins")));
    }

    @Test
    void decoratesSingleAndListFieldsRespectingAppiumFinderRequirement() {
        SamplePage page = new SamplePage();
        DefaultFieldDecorator decorator = new DefaultFieldDecorator(PageFactoryFinderSupportTest::locatorFor);

        for (String name : List.of(
                "customSingle",
                "inferredItems",
                "appiumFindByList",
                "legacySingleFallback",
                "legacyOnlyList",
                "seleniumOnlyList",
                "unannotatedList"
        )) {
            try {
                Field f = field(name);
                Object decorated = decorator.decorate(SamplePage.class.getClassLoader(), f);
                if (decorated != null) {
                    f.setAccessible(true);
                    f.set(page, decorated);
                }
            } catch (ReflectiveOperationException e) {
                throw new AssertionError(e);
            }
        }

        assertNotNull(page.customSingle);
        assertEquals(By.cssSelector("[data-testid='submit-btn']").toString(), page.customSingle.getText());
        assertNotNull(page.inferredItems);
        assertEquals(1, page.inferredItems.size());
        assertEquals(By.cssSelector("[data-testid='inferredItems']").toString(), page.inferredItems.get(0).getText());
        assertNotNull(page.appiumFindByList);
        assertEquals(By.id("appium-list").toString(), page.appiumFindByList.get(0).getText());
        assertNotNull(page.legacySingleFallback);
        assertEquals(new ByIdOrName("legacySingleFallback").toString(), page.legacySingleFallback.getText());
        assertNull(page.legacyOnlyList);
        assertNull(page.seleniumOnlyList);
        assertNull(page.unannotatedList);
    }

    @Test
    void rejectsConflictingAnnotationsAndInvalidBuilders() throws NoSuchFieldException {
        Field conflicting = field("conflicting");
        IllegalArgumentException conflictError = assertThrows(
                IllegalArgumentException.class,
                () -> PageFactoryFinderSupport.buildBy(conflicting)
        );
        assertTrue(conflictError.getMessage().contains("@TestId"), conflictError.getMessage());
        assertTrue(conflictError.getMessage().contains("@FindBy"), conflictError.getMessage());

        Field customInvalid = field("customInvalid");
        IllegalArgumentException builderValidationError = assertThrows(
                IllegalArgumentException.class,
                () -> PageFactoryFinderSupport.buildBy(customInvalid)
        );
        assertEquals("Invalid test id", builderValidationError.getMessage());

        Field broken = field("brokenBuilderField");
        IllegalArgumentException brokenBuilderError = assertThrows(
                IllegalArgumentException.class,
                () -> PageFactoryFinderSupport.buildBy(broken)
        );
        assertTrue(brokenBuilderError.getMessage().contains("NoZeroArgConstructorBuilder"));
    }
}
