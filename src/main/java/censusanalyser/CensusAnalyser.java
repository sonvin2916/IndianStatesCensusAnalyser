package censusanalyser;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, CensusDAO> censusStateMap=null;
    public CensusAnalyser()
    {

    }

    public int loadIndiaCensusData(String... csvFilePath) throws CensusAnalyserException
    {
        censusStateMap=  new CensusLoader().loadCensusData(IndiaCensusCSV.class,csvFilePath);
        return censusStateMap.size();
    }

    public int loadUSCensusData(String... csvFilePath) throws CensusAnalyserException {
        censusStateMap=  new CensusLoader().loadCensusData(USCensusCSV.class,csvFilePath);
        return censusStateMap.size();
    }


    public boolean loadCSVFileToCheckForDemiliterAndHeader(String csvFilePath) throws CensusAnalyserException {
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            String row;
            while ((row = csvReader.readLine()) != null) {
                if (row.contains(",") || (row.contains("State")))
                    System.out.println("File contains a Delimiter or Header");
            }
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CVS_FILE_PROBLEM);
        }
        return true;
    }


    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;

    }


    public String getPopulationWiseSortedData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusPopulationComparator = Comparator.comparing(census -> census.population);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusPopulationComparator);
        String sortedPopulationJson = new Gson().toJson(censusDAOS);
        return sortedPopulationJson;

    }

    public String getDensityWiseSortedData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusDensityComparator = Comparator.comparing(census -> census.densityPerSqKm);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusDensityComparator);
        String sortedDensityWiseJson = new Gson().toJson(censusDAOS);
        return sortedDensityWiseJson;
    }

    public String getAreaWiseSortedData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusAreaComparator = Comparator.comparing(census -> census.areaInSqKm);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusAreaComparator);
        String sortedAreaWiseJson = new Gson().toJson(censusDAOS);
        return sortedAreaWiseJson;


    }

    private void sort(List<CensusDAO> censusDAOS, Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() -1; i++) {
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
    public String getUSPopulationWiseSortedData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusPopulationComparator = Comparator.comparing(census -> census.population);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusPopulationComparator);
        String sortedPopulationJson = new Gson().toJson(censusDAOS);
        return sortedPopulationJson;
    }

    public String getUSPopulationDensityWiseSortedData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusPopulationComparator = Comparator.comparing(census -> census.populationDensity);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusPopulationComparator);
        String sortedPopulationJson = new Gson().toJson(censusDAOS);
        return sortedPopulationJson;
    }

    public String getUSTotalAreaWiseSortedData() throws CensusAnalyserException {
        if (censusStateMap.size() == 0 || censusStateMap == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusPopulationComparator = Comparator.comparing(census -> census.totalArea);
        List<CensusDAO> censusDAOS = censusStateMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusPopulationComparator);
        String sortedAreaJson = new Gson().toJson(censusDAOS);
        return sortedAreaJson;
    }
}