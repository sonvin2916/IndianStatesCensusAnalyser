package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public int population;
    public double totalArea;
    public double populationDensity;
    public int densityPerSqKm;
    public int areaInSqKm;


    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea = indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        population = indiaCensusCSV.population;
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;

    }

    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateCode = censusCSV.stateid;
        population = censusCSV.population;
        populationDensity = censusCSV.populationdensity;
        totalArea = censusCSV.totalarea;


    }

    public CensusDAO(IndiaStateCodeCSV stateCodeCSV) {
        stateCode = stateCodeCSV.stateCode;

    }
}