package censusanalyser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Map;

public class IndiaStateAnalyzerTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_CODE_CSV_DELIMITER_FILE_PATH = "./src/test/resources/IndiaStateCensusDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_HEADER_MISSING_FILE_PATH = "./src/test/resources/IndiaStateCensusHeaderMissing.csv";

    CensusAdaptor censusAdapter = new IndiaCensusAdaptor();

    @Test
    public void givenIndiaCSVData_ShouldGetMapOfCorrectSize() {
        Map<String, CensusDAO> keyMap = null;
        try {
            keyMap = censusAdapter.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, keyMap.size());
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
        }
    }

    @Test
    public void givenStateCSVFile_WhenHeaderNotAvailable_ThrowsException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAdapter.loadCensusData(INDIA_STATE_CODE_CSV_HEADER_MISSING_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenStateCSVFile_WhenDelimiterWrong_ThrowsException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAdapter.loadCensusData(INDIA_STATE_CODE_CSV_DELIMITER_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCSVFile_WhenPassedEmptyFile_ThrowsException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAdapter.loadCensusData("", INDIA_STATE_CODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }
}