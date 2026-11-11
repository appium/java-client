package io.appium.java_client.support;

import io.appium.java_client.support.pagefactory.Annotations;
import io.appium.java_client.support.pagefactory.ByAll;
import io.appium.java_client.support.pagefactory.ByChained;
import io.appium.java_client.support.pagefactory.DefaultFieldDecorator;
import io.appium.java_client.support.pagefactory.ElementLocator;
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

class AnnotationsTest {

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

        @TestId("submit-btn")
        WebElement customSingle;

        @TestId
        List<WebElement> inferredItems;

        @TestId("__null__")
        WebElement customNullFallback;

        @TestId("__invalid__")
        WebElement customInvalid;

        @LegacyRole("listitem")
        @org.openqa.selenium.support.CacheLookup
        List<WebElement> legacyItems;

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

    private static ElementLocator locatorFor(Field field) {
        By by = new Annotations(field).buildBy();
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
        Annotations standard = new Annotations(field("standard"));
        assertEquals(By.id("save"), standard.buildBy());
        assertTrue(standard.isLookupCached());

        assertInstanceOf(ByChained.class, new Annotations(field("chained")).buildBy());
        assertInstanceOf(ByAll.class, new Annotations(field("any")).buildBy());

        assertEquals(
                By.cssSelector("[data-testid='submit-btn']"),
                new Annotations(field("customSingle")).buildBy()
        );
        assertFalse(new Annotations(field("customSingle")).isLookupCached());

        assertEquals(
                By.cssSelector("[data-testid='inferredItems']"),
                new Annotations(field("inferredItems")).buildBy()
        );

        assertInstanceOf(ByIdOrName.class, new Annotations(field("fallbackByName")).buildBy());
        assertInstanceOf(ByIdOrName.class, new Annotations(field("customNullFallback")).buildBy());

        Annotations legacy = new Annotations(field("legacyItems"));
        assertEquals(By.cssSelector("[role='listitem']"), legacy.buildBy());
        assertTrue(legacy.isLookupCached());

        assertEquals(By.id("appium-wins"), new Annotations(field("bothBuiltins")).buildBy());
    }

    @Test
    void decoratesSingleAndListFieldsAnnotatedWithCustomAndLegacyFinders() {
        SamplePage page = new SamplePage();
        DefaultFieldDecorator decorator = new DefaultFieldDecorator(AnnotationsTest::locatorFor);

        for (String name : List.of("customSingle", "inferredItems", "legacyItems", "unannotatedList")) {
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
        assertNotNull(page.legacyItems);
        assertEquals(By.cssSelector("[role='listitem']").toString(), page.legacyItems.get(0).getText());
        assertNull(page.unannotatedList);
    }

    @Test
    void rejectsConflictingAnnotationsAndInvalidBuilders() throws NoSuchFieldException {
        Field conflicting = field("conflicting");
        IllegalArgumentException conflictError = assertThrows(
                IllegalArgumentException.class,
                () -> new Annotations(conflicting).buildBy()
        );
        assertTrue(conflictError.getMessage().contains("@TestId"), conflictError.getMessage());
        assertTrue(conflictError.getMessage().contains("@FindBy"), conflictError.getMessage());

        Field customInvalid = field("customInvalid");
        IllegalArgumentException builderValidationError = assertThrows(
                IllegalArgumentException.class,
                () -> new Annotations(customInvalid).buildBy()
        );
        assertEquals("Invalid test id", builderValidationError.getMessage());

        Field broken = field("brokenBuilderField");
        IllegalArgumentException brokenBuilderError = assertThrows(
                IllegalArgumentException.class,
                () -> new Annotations(broken).buildBy()
        );
        assertTrue(brokenBuilderError.getMessage().contains("NoZeroArgConstructorBuilder"));
    }
}
