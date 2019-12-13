package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
    CensusAnalyser usCensusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String INDIA_STATE_CODE_CSV_DELIMITER_FILE_PATH = "./src/test/resources/IndiaStateCensusDelimiter.csv";
    private static final String INDIA_STATE_CODE_CSV_HEADER_MISSING_FILE_PATH = "./src/test/resources/IndiaStateCensusHeaderMissing.csv";
    private static final String US_CENSUS_DATA = "./src/test/resources/USCensusData.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            int numOfRecords = censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaStateCode_ShouldReturnExpectedCount() {
        try {
            int numOfStateCode = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFile_whenDelimiterIncorrect_ReturnCustomException() {
        try {
            int numOfStateCode = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_DELIMITER_FILE_PATH);
            Assert.assertEquals(29, numOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenStateCensusCSVFile_whenHeaderIncorrect_ReturnCustomException() {
        try {
            int numOfStateCode = censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_HEADER_MISSING_FILE_PATH);
            Assert.assertEquals(29, numOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnState_ShouldReturnSortedState() {
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenPassedWrongFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenPassedEmptyFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData("");
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.STATE);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedPopulation() {
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_IfPassedWrongFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_IfPassedEmptyFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData("");
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnArea_ShouldReturnSortedPopulation() {
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.AREA_IN_SQ_KM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnArea_IfPassedWrongFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.AREA_IN_SQ_KM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnArea_IfPassedEmptyFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData("");
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.AREA_IN_SQ_KM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnStateDensity_ShouldReturnSortedPopulation() {
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.DENSITY_PER_SQ_KM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnStateDensity_IfPassedWrongFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.DENSITY_PER_SQ_KM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnStateDensity_IfPassedEmptyFilePath_ShouldReturnException() {
        try {
            censusAnalyser.loadCensusData("");
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.DENSITY_PER_SQ_KM);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void commonCSVFile() {
        censusAnalyser.commonCSVBuilder(INDIA_CENSUS_CSV_FILE_PATH);
    }

    @Test
    public void gievnUSCensusData_ShouldReturnCorrectData() {
        try {
            int noOfRecords = censusAnalyser.loadCensusData(US_CENSUS_DATA);
            Assert.assertEquals(51, noOfRecords);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnState_ShouldReturnSortedState() {
        try {
            usCensusAnalyser.loadCensusData(US_CENSUS_DATA);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenPassedWrongFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenPassedEmptyFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData("");
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.STATE);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alabama", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnPopulation_ShouldReturnSortedPopulation() {
        try {
            usCensusAnalyser.loadCensusData(US_CENSUS_DATA);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnPopulation_IfPassedWrongFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnPopulation_IfPassedEmptyFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData("");
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnArea_ShouldReturnSortedPopulation() {
        try {
            usCensusAnalyser.loadCensusData(US_CENSUS_DATA);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.TOTAL_AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnArea_IfPassedWrongFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.TOTAL_AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnArea_IfPassedEmptyFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData("");
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.TOTAL_AREA);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("Alaska", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnStateDensity_ShouldReturnSortedPopulation() {
        try {
            usCensusAnalyser.loadCensusData(US_CENSUS_DATA);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION_DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnStateDensity_IfPassedWrongFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData(INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION_DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_WhenSortedOnStateDensity_IfPassedEmptyFilePath_ShouldReturnException() {
        try {
            usCensusAnalyser.loadCensusData("");
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION_DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("District of Columbia", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenUSStateData_FirstSortByPopulation_AnsThenSortByDensity() {
        try {
            usCensusAnalyser.loadCensusData(US_CENSUS_DATA);
            String sortedCensusData = usCensusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION_AND_DENSITY);
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, USCensusCSV[].class);
            Assert.assertEquals("California", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }

    @Test
    public void givenIndiaStateData_FirstSortByPopulation_AnsThenSortByDensity() {
        try {
            censusAnalyser.loadCensusData(INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(SortCensusData.POPULATION_AND_DENSITY);
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.SOME_OTHER_FILES_ERRORS, e.type);
        }
    }
}
