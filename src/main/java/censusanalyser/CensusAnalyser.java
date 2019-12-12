package censusanalyser;

import com.google.gson.Gson;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {

    public enum Country {INDIA, US}

    ;

    Map<String, CensusDAO> censusStateMap;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<String, CensusDAO>();
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusStateMap = new CensusLoader().loadCensusData(country, csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> csvComparable = Comparator.comparing(census -> census.state);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, csvComparable);
        String sortStateData = new Gson().toJson(censusStateMap);
        return sortStateData;
    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }

    public void commonCSVBuilder(String commonCsvFilePath) {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(commonCsvFilePath));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
                String name = csvRecord.get(0);
                String email = csvRecord.get(1);
                String phone = csvRecord.get(2);
                String country = csvRecord.get(3);
                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("Name : " + name);
                System.out.println("Email : " + email);
                System.out.println("Phone : " + phone);
                System.out.println("Country : " + country);
                System.out.println("---------------\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}