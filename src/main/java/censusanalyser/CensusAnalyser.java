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

    public enum Country {INDIA, US;}

    private Country country;
    Map<String, CensusDAO> censusStateMap = new HashMap<>();
    Map<SortCensusData, Comparator<CensusDAO>> sortBy = null;

    public CensusAnalyser(Country country) {
        this.sortBy = new HashMap<>();
        this.country = country;
        this.sortBy.put(SortCensusData.STATE, Comparator.comparing(census -> census.state));
        this.sortBy.put(SortCensusData.POPULATION, Comparator.comparing(census -> census.population, Comparator.reverseOrder()));
        this.sortBy.put(SortCensusData.AREA_IN_SQ_KM, Comparator.comparing(census -> census.areaInSqKm, Comparator.reverseOrder()));
        this.sortBy.put(SortCensusData.DENSITY_PER_SQ_KM, Comparator.comparing(census -> census.densityPerSqKm, Comparator.reverseOrder()));
        this.sortBy.put(SortCensusData.TOTAL_AREA, Comparator.comparing(census -> census.totalArea, Comparator.reverseOrder()));
        this.sortBy.put(SortCensusData.POPULATION_DENSITY, Comparator.comparing(census -> census.populationDensity, Comparator.reverseOrder()));
    }

    public String getSortedCensusData(SortCensusData field) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        ArrayList censusDTO = censusStateMap.values().stream()
                .sorted(this.sortBy.get(field))
                .map(censusDAO -> censusDAO.getCensusDTO(country))
                .collect(Collectors.toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTO);
        return sortedStateCensusJson;
    }

    public int loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        CensusAdaptor censusAdapter = CensusAnalyzerFactory.countryData(country, csvFilePath);
        censusStateMap = censusAdapter.loadCensusData(csvFilePath);
        return censusStateMap.size();
    }

    public String getStateWiseSortedCensusData(SortCensusData sortBy) throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data",
                    CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, this.sortBy.get(sortBy));
        String sortStateData = new Gson().toJson(censusDAOS);
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