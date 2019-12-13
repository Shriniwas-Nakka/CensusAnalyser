package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int areaInSqKm;
    public int population;
    public int densityPerSqKm;
    public double populationDensity;
    public double totalArea;

    public CensusDAO(IndiaCensusCSV indianCensusCSV) {
        this.state = indianCensusCSV.state;
        this.areaInSqKm = indianCensusCSV.areaInSqKm;
        this.population = indianCensusCSV.population;
        this.densityPerSqKm = indianCensusCSV.densityPerSqKm;
    }

    public CensusDAO(USCensusCSV usCensusCSV) {
        this.state = usCensusCSV.state;
        this.stateCode = usCensusCSV.stateId;
        this.population = usCensusCSV.population;
        this.populationDensity = usCensusCSV.populationDensity;
        this.totalArea = usCensusCSV.totalArea;
    }
}