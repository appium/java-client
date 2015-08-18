package io.appium.java_client.pagefactory;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import java.util.ArrayList;
import java.util.List;

public class Annotations {
    public static final List<Class> FIND_BY = new ArrayList<Class>() {{
        add(FindBy.class);
        add(FindAll.class);
        add(FindBys.class);
        add(AndroidFindBy.class);
        add(AndroidFindAll.class);
        add(AndroidFindBys.class);
        add(iOSFindBy.class);
        add(iOSFindAll.class);
        add(iOSFindBys.class);
        add(SelendroidFindBy.class);
        add(SelendroidFindAll.class);
        add(SelendroidFindBys.class);
    }};
}
