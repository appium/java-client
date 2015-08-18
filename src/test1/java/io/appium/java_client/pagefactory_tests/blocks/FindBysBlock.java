package io.appium.java_client.pagefactory_tests.blocks;

import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;

import java.util.List;

/**
 * Created by dolf on 19.08.15
 */
@AndroidFindBys({
        @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/decor_content_parent\")"),
        @AndroidFindBy(className = "android.widget.ListView")
})
public class FindBysBlock extends AndroidElement {
    @AndroidFindBy(id = "android:id/text1")
    public List<AndroidElement> items;

    @AndroidFindBy(id = "android:id/text1")
    public AndroidElement item;
}
