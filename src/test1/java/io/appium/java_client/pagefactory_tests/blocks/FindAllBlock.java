package io.appium.java_client.pagefactory_tests.blocks;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;

import java.util.List;

/**
 * Created by dolf on 19.08.15
 */
@AndroidFindAll({
        @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/fail_parent\")"),
        @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"android:id/decor_content_parent\")")
})
public class FindAllBlock extends AndroidElement {
        public ListItemBlock item;
        public List<ListItemBlock> items;
}
