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

package io.appium.java_client.events.stubs;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver.Window;

public class StubWindow implements Window {
    public StubWindow() {
    }

    public void setSize(Dimension targetSize) {
    }

    public void setPosition(Point targetPosition) {
    }

    public Dimension getSize() {
        return null;
    }

    public Point getPosition() {
        return null;
    }

    public void maximize() {
    }

    @Override
    public void minimize() {
    }

    public void fullscreen() {
    }
}
