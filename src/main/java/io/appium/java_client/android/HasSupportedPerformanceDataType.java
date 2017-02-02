package io.appium.java_client.android;

import static io.appium.java_client.android.AndroidMobileCommandHelper.getPerformanceDataCommand;
import static io.appium.java_client.android.AndroidMobileCommandHelper.getSupportedPerformanceDataTypesCommand;

import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.ExecutesMethod;

import java.util.List;

/**
 * Created by hwangheeseon on 2017. 2. 2..
 */
public interface HasSupportedPerformanceDataType extends ExecutesMethod {
    default List<String> getSupportedPerformanceDataTypes() {
        return CommandExecutionHelper.execute(this, getSupportedPerformanceDataTypesCommand());
    }

    default List<List<Object>> getPerformanceData(String packageName, String dataType, int dataReadTimeout)
            throws Exception {
        return CommandExecutionHelper.execute(this,
                getPerformanceDataCommand(packageName, dataType, dataReadTimeout));
    }
}
