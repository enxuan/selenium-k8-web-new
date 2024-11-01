package test.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test_data.DataObjectBuilder;
import test_data.computer.ComputerData;

import java.util.Arrays;

public class DataProviderComputerData {

    @Test(dataProvider = "computerData")
    public void testDataProvider(ComputerData user) {
        System.out.println(user);
    }

    @DataProvider
    public ComputerData[] computerData() {
        String fileLocation = "/src/test/java/test_data/computer/CheapComputerDataList.json";
        return DataObjectBuilder.buildDataObjectFrom(fileLocation, ComputerData[].class);
    }
}
