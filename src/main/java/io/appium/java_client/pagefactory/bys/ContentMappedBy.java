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

package io.appium.java_client.pagefactory.bys;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.appium.java_client.pagefactory.bys.ContentType.NATIVE_MOBILE_SPECIFIC;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class ContentMappedBy extends By {
    private final Map<ContentType, By> map;
    private ContentType currentContent = NATIVE_MOBILE_SPECIFIC;

    public ContentMappedBy(Map<ContentType, By> map) {
        this.map = map;
    }

    public By useContent(@Nonnull ContentType type) {
        checkNotNull(type);
        currentContent = type;
        return this;
    }

    @Override public WebElement findElement(SearchContext context) {
        return context.findElement(map.get(currentContent));
    }

    @Override public List<WebElement> findElements(SearchContext context) {
        return context.findElements(map.get(currentContent));
    }

    @Override public String toString() {
        return map.get(currentContent).toString();
    }
}
