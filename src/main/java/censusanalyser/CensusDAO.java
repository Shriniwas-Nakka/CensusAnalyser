package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int areaInSqKm;
    public int population;
    public int densityPerSqKm;
    public int stateId;
    public double populationDensity;
    public double totalArea;

    public CensusDAO(IndiaCensusCSV indianCensusCSV) {
        state = indianCensusCSV.state;
        areaInSqKm = indianCensusCSV.areaInSqKm;
        population = indianCensusCSV.population;
        densityPerSqKm = indianCensusCSV.densityPerSqKm;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        state = usCensusCSV.state;
        stateId = usCensusCSV.stateId;
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.populationDensity;
        totalArea = usCensusCSV.totalArea;
    }
}