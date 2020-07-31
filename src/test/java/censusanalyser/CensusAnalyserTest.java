package censusanalyser;

import com.google.gson.Gson;
import javafx.scene.chart.ScatterChart;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE_PATH = "./src/test/resources/IndiaStateCensusData.pdf";
    private static final String WRONG_CSV_FILE_DELIMITER = "./src/test/resources/IndiaStateCensusDataDelimiter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/IndiaStateCensusDataHeader.csv";
    private static final String INDIA_STATE_CODE_CSV_FILE_PATH="./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_INDIA_STATE_CODE_FILE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CODE_CSV_FILE_TYPE_PATH = "./src/test/resources/IndiaStateCode.txt";
    private static final String WRONG_INDIA_STATE_CODE_FILE_DELIMITER = "./src/test/resources/IndiaStateCodeDelimiterAndHeader.csv";
    private static final String WRONG_INDIA_STATE_CODE_FILE_HEADER = "./src/test/resources/IndiaStateCodeDelimiterAndHeader.csv";

    @Test
    public void givenIndianCensusCSVFile_WhenPassedCorrect_ShouldReturnCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaCensusCSVFile_WhenIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVFile_WhenCorrect_ButIncorrectType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE,e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVFile_WhenIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCSVFileToCheckForDemiliterAndHeader(WRONG_CSV_FILE_DELIMITER );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CVS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusCSVFile_WhenIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCSVFileToCheckForDemiliterAndHeader(WRONG_CSV_FILE_HEADER );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CVS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeCSVFile_WhenPassedCorrect_ShouldReturnCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaStateCodeData(INDIA_STATE_CODE_CSV_FILE_PATH);
            Assert.assertEquals(37,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndiaStateCodeCSVFile_WhenIncorrect_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(WRONG_INDIA_STATE_CODE_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
    @Test
    public void givenIndiaStateCodeCSVFile_WhenCorrect_ButIncorrectType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaStateCodeData(WRONG_STATE_CODE_CSV_FILE_TYPE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE,e.type);
        }
    }
    @Test
    public void givenIndiastateCodeCSVFile_WhenIncorrectDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCSVFileToCheckForDemiliterAndHeader(WRONG_INDIA_STATE_CODE_FILE_DELIMITER );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CVS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiastateCodeCSVFile_WhenIncorrectHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCSVFileToCheckForDemiliterAndHeader(WRONG_INDIA_STATE_CODE_FILE_HEADER );
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CVS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnState_ShouldReturnSortedResult() throws CensusAnalyserException
    {

            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData=censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
    }
   @Test
   public void givenIndiaCensusData_WhenSortedOnStateCode_ShouldReturnSortedResult() throws CensusAnalyserException
   {

       CensusAnalyser censusAnalyser = new CensusAnalyser();
       censusAnalyser.loadIndiaStateCodeData( INDIA_STATE_CODE_CSV_FILE_PATH);
       String sortedStateCodeData=censusAnalyser.getStateCodeWiseSortedCensusData();
       IndiaStateCodeCSV[] stateCodeCSVS = new Gson().fromJson(sortedStateCodeData,IndiaStateCodeCSV[].class);
       Assert.assertEquals("AD",stateCodeCSVS[0].stateCode);
   }

}
