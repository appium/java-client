package io.appium.java_client.pagefactory;

import static io.appium.java_client.remote.MobilePlatform.*;
import io.appium.java_client.MobileBy;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.pagefactory.ByChained;

class AppiumAnnotations extends Annotations{

	private final static List<String> METHODS_TO_BE_EXCLUDED_WHEN_ANNOTATION_IS_READ = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			List<String> objectClassMethodNames = getMethodNames(Object.class
					.getDeclaredMethods());
			addAll(objectClassMethodNames);
			List<String> annotationClassMethodNames = getMethodNames(Annotation.class
					.getDeclaredMethods());
			annotationClassMethodNames.removeAll(objectClassMethodNames);
			addAll(annotationClassMethodNames);
		}
	};
	private final static Class<?>[] DEFAULT_ANNOTATION_METHOD_ARGUMENTS = new Class<?>[] {};
	private static List<String> getMethodNames(Method[] methods) {
		List<String> names = new ArrayList<String>();
		for (Method m : methods) {
			names.add(m.getName());
		}
		return names;
	}

	private static enum Strategies {
		BYUIAUTOMATOR("uiAutomator") {
			@Override
			By getBy(Annotation annotation) {
				String value = getValue(annotation, this);
				if (annotation.annotationType().equals(AndroidFindBy.class)) {
					return MobileBy.AndroidUIAutomator(value);
				}
				if (annotation.annotationType().equals(iOSFindBy.class)) {
					return MobileBy.IosUIAutomation(value);
				}
				return super.getBy(annotation);
			}
		},
		BYACCESSABILITY("accessibility") {
			@Override
			By getBy(Annotation annotation) {
				return MobileBy.AccessibilityId(getValue(annotation, this));
			}
		},
		BYCLASSNAME("className") {
			@Override
			By getBy(Annotation annotation) {
				return By.className(getValue(annotation, this));
			}
		},
		BYID("id") {
			@Override
			By getBy(Annotation annotation) {
				return By.id(getValue(annotation, this));
			}
		},
		BYTAG("tagName") {
			@Override
			By getBy(Annotation annotation) {
				return By.tagName(getValue(annotation, this));
			}
		},
		BYNAME("name") {
			@Override
			By getBy(Annotation annotation) {
				return By.name(getValue(annotation, this));
			}
		},
		BYXPATH("xpath") {
			@Override
			By getBy(Annotation annotation) {
				return By.xpath(getValue(annotation, this));
			}
		};

		private final String valueName;

		private String returnValueName() {
			return valueName;
		}

		private Strategies(String valueName) {
			this.valueName = valueName;
		}

		private static String[] strategiesNames() {
			Strategies[] strategies = values();
			String[] result = new String[strategies.length];
			int i = 0;
			for (Strategies strategy : values()) {
				result[i] = strategy.valueName;
				i++;
			}
			return result;
		}

		private static String getValue(Annotation annotation,
				Strategies strategy) {
			try {
				Method m = annotation.getClass().getMethod(strategy.valueName,
						DEFAULT_ANNOTATION_METHOD_ARGUMENTS);
				return m.invoke(annotation, new Object[] {}).toString();
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}

		By getBy(Annotation annotation) {
			return null;
		}
	}

	private final Field mobileField;
	private final String platform;

	AppiumAnnotations(Field field, String platform) {
		super(field);
		mobileField = field;
		this.platform = String.valueOf(platform).
				toUpperCase().trim();
	}

	private static void checkDisallowedAnnotationPairs(Annotation a1,
			Annotation a2) throws IllegalArgumentException {
		if (a1 != null && a2 != null) {
			throw new IllegalArgumentException(
					"If you use a '@" + a1.getClass().getSimpleName() + "' annotation, "
							+ "you must not also use a '@" + a2.getClass().getSimpleName() + "' annotation");
		}
	}
	
	private void assertValidAnnotations() {
		AndroidFindBy androidBy = mobileField
				.getAnnotation(AndroidFindBy.class);
		AndroidFindBys androidBys = mobileField
				.getAnnotation(AndroidFindBys.class);
		AndroidFindAll androidFindAll = mobileField.
				getAnnotation(AndroidFindAll.class);

		iOSFindBy iOSBy = mobileField.getAnnotation(iOSFindBy.class);
		iOSFindBys iOSBys = mobileField.getAnnotation(iOSFindBys.class);
		iOSFindAll iOSFindAll = mobileField.getAnnotation(iOSFindAll.class);
		
		checkDisallowedAnnotationPairs(androidBy, androidBys);
		checkDisallowedAnnotationPairs(androidBy, androidFindAll);
		checkDisallowedAnnotationPairs(androidBys, androidFindAll);
		
		checkDisallowedAnnotationPairs(iOSBy, iOSBys);
		checkDisallowedAnnotationPairs(iOSBy, iOSFindAll);
		checkDisallowedAnnotationPairs(iOSBys, iOSFindAll);
	}

	private static Method[] prepareAnnotationMethods(
			Class<? extends Annotation> annotation) {
		List<String> targeAnnotationMethodNamesList = getMethodNames(annotation.getDeclaredMethods());
		targeAnnotationMethodNamesList.removeAll(METHODS_TO_BE_EXCLUDED_WHEN_ANNOTATION_IS_READ);
		Method[] result = new Method[targeAnnotationMethodNamesList.size()];
		for (String methodName: targeAnnotationMethodNamesList){
			try {
				result[targeAnnotationMethodNamesList.indexOf(methodName)] = annotation.getMethod(methodName, DEFAULT_ANNOTATION_METHOD_ARGUMENTS);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			}
		}
		return result;
	}

	// I suppose that only @AndroidFindBy and @iOSFindBy will be here
	private static String getFilledValue(Annotation mobileBy) {
		Method[] values = prepareAnnotationMethods(mobileBy.getClass());
		for (Method value : values) {
			try {
				String strategyParameter = value.invoke(mobileBy,
						new Object[] {}).toString();
				if (!"".equals(strategyParameter)) {
					return value.getName();
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		}
		throw new IllegalArgumentException("@"
				+ mobileBy.getClass().getSimpleName() + ": one of "
				+ Strategies.strategiesNames().toString() + " should be filled");
	}

	private By getMobileBy(Annotation annotation, String valueName) {
		Strategies strategies[] = Strategies.values();
		for (Strategies strategy : strategies) {
			if (strategy.returnValueName().equals(valueName)) {
				return strategy.getBy(annotation);
			}
		}
		throw new IllegalArgumentException("@"
				+ annotation.getClass().getSimpleName()
				+ ": There is an unknown strategy " + valueName);
	}

	@SuppressWarnings("unchecked")
	private <T extends By> T getComplexMobileBy(Annotation[] annotations, Class<T> requiredByClass) {
		;
		By[] byArray = new By[annotations.length];
		for (int i = 0; i < annotations.length; i++) {
			byArray[i] = getMobileBy(annotations[i],
					getFilledValue(annotations[i]));
		}
		try {
			Constructor<?> c = requiredByClass.getConstructor(By[].class);
			Object[] values = new Object[]{byArray};
			return (T) c.newInstance(values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public By buildBy() {
		assertValidAnnotations();

		AndroidFindBy androidBy = mobileField
				.getAnnotation(AndroidFindBy.class);
		if (androidBy != null && ANDROID.toUpperCase().equals(platform)) {
			return getMobileBy(androidBy, getFilledValue(androidBy));
		}

		AndroidFindBys androidBys = mobileField
				.getAnnotation(AndroidFindBys.class);
		if (androidBys != null && ANDROID.toUpperCase().equals(platform)) {
			return getComplexMobileBy(androidBys.value(), ByChained.class);
		}
		
		AndroidFindAll androidFindAll = mobileField.getAnnotation(AndroidFindAll.class);
		if (androidFindAll != null && ANDROID.toUpperCase().equals(platform)) {
			return getComplexMobileBy(androidFindAll.value(), ByAll.class);
		}

		iOSFindBy iOSBy = mobileField.getAnnotation(iOSFindBy.class);
		if (iOSBy != null && IOS.toUpperCase().equals(platform)) {
			return getMobileBy(iOSBy, getFilledValue(iOSBy));
		}

		iOSFindBys iOSBys = mobileField.getAnnotation(iOSFindBys.class);
		if (iOSBys != null && IOS.toUpperCase().equals(platform)) {
			return getComplexMobileBy(iOSBys.value(), ByChained.class);
		}
		
		iOSFindAll iOSFindAll = mobileField.getAnnotation(iOSFindAll.class);
		if (iOSFindAll != null && IOS.toUpperCase().equals(platform)) {
			return getComplexMobileBy(iOSFindAll.value(), ByAll.class);
		}		

		return super.buildBy();
	}

}
