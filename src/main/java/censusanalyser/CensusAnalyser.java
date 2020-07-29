package censusanalyser;



import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws  CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));)
        {

            CsvToBeanBuilder<IndiaCensusCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaCensusCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaCensusCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaCensusCSV> censusCSVIterator = csvToBean.iterator();;
            Iterable<IndiaCensusCSV> censusCSVIterable=() -> censusCSVIterator;
            int numOfEntries = (int) StreamSupport.stream(censusCSVIterable.spliterator(),false).count();
            return numOfEntries;
        }
        catch (IOException e)
        {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        catch (IllegalStateException e)
        {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }

    }
    public boolean loadIndiaCensusDataForDelimiter(String csvFilePath) throws  CensusAnalyserException{
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            String row;
            while((row =csvReader.readLine())!=null)
            {
                if(row.contains(","))
                    System.out.println("File contains a Delimiter");
            }

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_DELIMITER);
        }
        return true;
    }
    public boolean loadIndiaCensusDataForHeader(String csvFilePath) throws  CensusAnalyserException{
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            String row;
            while((row =csvReader.readLine())!=null)
            {
                if(row.contains("State"))
                    System.out.println("File contains a Header");
            }

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_HEADER);
        }
        return true;
    }
    public int loadIndiaStateCodeData(String csvFilePath) throws  CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            CsvToBeanBuilder<IndiaStateCodeCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndiaStateCodeCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndiaStateCodeCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndiaStateCodeCSV> csvIterable = () -> censusCSVIterator;
            int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return numOfEntries;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
    public boolean loadIndiaStateCodeFileForDelimiter(String csvFilePath) throws  CensusAnalyserException{
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            String row;
            while((row =csvReader.readLine())!=null)
            {
                if(row.contains(","))
                    System.out.println("File contains a Delimiter");
            }

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_DELIMITER);
        }
        return true;
    }
    public boolean loadIndiaStateCodeFileForHeader(String csvFilePath) throws  CensusAnalyserException{
        try{
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath));
            String row;
            while((row =csvReader.readLine())!=null)
            {
                if(row.contains("Name"))
                    System.out.println("File contains a correct header");
            }

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),CensusAnalyserException.ExceptionType.CENSUS_FILE_HEADER);
        }
        return true;
    }


}
