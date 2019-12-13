package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class UsStateAnalyzerTest {
    private static final String US_CENSUS_DATA = "./src/test/resources/USCensusData.csv";
    private static final String US_CENSUS_DATA_WITHOUT_HEADER = "./src/test/resources/USCensusDataWithoutHeader.csv";

    CensusAdaptor censusAdapter = new USCensusAdaptor();

    @Test
    public void givenUSCSVData_ShouldReturnNumberOfStateCount() {
        try {
            Map<String, CensusDAO> numberOfCount = censusAdapter.loadCensusData(US_CENSUS_DATA);
            Assert.assertEquals(51, numberOfCount.size());
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenUSStateCSVFile_WhenHeaderNotAvailable_ThrowsException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAdapter.loadCensusData(US_CENSUS_DATA_WITHOUT_HEADER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateCSVFile_WhenPassedEmptyFile_ThrowsException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAdapter.loadCensusData("");
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }
}
