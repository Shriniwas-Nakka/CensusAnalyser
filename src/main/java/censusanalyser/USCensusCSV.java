package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {
    //    StateId,State,Population,HousingUnits,TotalArea,WaterArea,LandArea,PopulationDensity,HousingDensity
    @CsvBindByName(column = "StateId", required = true)
    public String stateId;

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "HousingUnits", required = true)
    public String housingUnits;

    @CsvBindByName(column = "TotalArea", required = true)
    public double totalArea;

    @CsvBindByName(column = "WaterArea", required = true)
    public double waterArea;

    @CsvBindByName(column = "LandArea", required = true)
    public double landArea;

    @CsvBindByName(column = "PopulationDensity", required = true)
    public double populationDensity;

    @CsvBindByName(column = "HousingDensity", required = true)
    public String housing;

}
