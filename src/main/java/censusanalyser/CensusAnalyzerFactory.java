package censusanalyser;

public class CensusAnalyzerFactory {

    public static CensusAdaptor countryData(CensusAnalyser.Country country, String[] csvFilePath) throws CensusAnalyserException {
        if (country.equals(CensusAnalyser.Country.INDIA)) {
            return new IndiaCensusAdaptor();
        } else if (country.equals(CensusAnalyser.Country.US)) {
            return new USCensusAdaptor();
        } else {
            throw new CensusAnalyserException("Incorrect Country", CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
        }
    }

}
