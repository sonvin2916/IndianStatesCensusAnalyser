
package censusanalyser;

import com.google.gson.Gson;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> censusCSVList;
    List<IndiaStateCodeCSV> stateCodeCSV;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                this.censusCSVList.add(new IndiaCensusDAO(csvFileIterator.next()));
            }
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
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

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder<IndiaStateCodeCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCodeCSV = csvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return stateCodeCSV.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;

    }

    public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
        if (stateCodeCSV.size() == 0 || stateCodeCSV == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaStateCodeCSV> stateCodeCSVComparator = Comparator.comparing(census -> census.stateCode);
        sort(stateCodeCSV, stateCodeCSVComparator);
        String sortedStateCodeJson = new Gson().toJson(stateCodeCSV);
        return sortedStateCodeJson;

    }

    public String getPopulationWiseSortedData() throws CensusAnalyserException {
        if (censusCSVList.size() == 0 || censusCSVList == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusPopulationComparator = Comparator.comparing(census -> census.population);
        this.sort(censusPopulationComparator);
        String sortedPopulationJson = new Gson().toJson(censusCSVList);
        return sortedPopulationJson;

    }

    public String getDensityWiseSortedData() throws CensusAnalyserException {
        if (censusCSVList.size() == 0 || censusCSVList == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusDensityComparator = Comparator.comparing(census -> census.densityPerSqKm);
        this.sort(censusDensityComparator);
        String sortedDensityWiseJson = new Gson().toJson(censusCSVList);
        return sortedDensityWiseJson;
    }

    public String getAreaWiseSortedData() throws CensusAnalyserException {
        if (censusCSVList.size() == 0 || censusCSVList == null) {
            throw new CensusAnalyserException("NO_CENSUS_DATA", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusAreaComparator = Comparator.comparing(census -> census.areaInSqKm);
        this.sort(censusAreaComparator);
        String sortedAreaWiseJson = new Gson().toJson(censusCSVList);
        return sortedAreaWiseJson;


    }

    private <E> void sort(List<E> csvList, Comparator<E> comparator) {
        Collections.sort(csvList, comparator);
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusCSVList.size() - 1; i++) {
            for (int j = 0; j < censusCSVList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusCSVList.get(j);
                IndiaCensusDAO census2 = censusCSVList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusCSVList.set(j, census2);
                    censusCSVList.set(j + 1, census1);
                }
            }
        }
    }
}

