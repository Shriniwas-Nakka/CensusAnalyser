package censusanalyser;

import java.util.HashMap;
import java.util.Map;

public class USCensusAdaptor extends CensusAdaptor {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> usStateMap = new HashMap<>();
        usStateMap = super.loadCensusData(USCensusCSV.class, csvFilePath[0]);
        return usStateMap;
    }
}
