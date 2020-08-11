package censusanalyser;

import com.google.gson.Gson;
import javafx.scene.chart.ScatterChart;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

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
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";


    @Test
    public void givenIndianCensusCSVFile_WhenPassedCorrect_ShouldReturnCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,WRONG_INDIA_STATE_CODE_FILE_PATH);
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
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_FILE_PATH);
                String sortedCensusData=censusAnalyser.getStateWiseSortedCensusData();
                IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData,IndiaCensusCSV[].class);
                Assert.assertEquals("Andhra Pradesh",censusCSV[0].state);
            }
            catch (CensusAnalyserException e){}
    }

   @Test
   public void givenIndiaCensusData_WhenSortedPopulationWise_ShouldReturnSortedResult() throws CensusAnalyserException
   {
       try
       {
           CensusAnalyser censusAnalyser = new CensusAnalyser();
           censusAnalyser.loadIndiaCensusData( INDIA_CENSUS_CSV_FILE_PATH);
           String sortedPopulationData=censusAnalyser.getPopulationWiseSortedData();
           IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedPopulationData,IndiaCensusCSV[].class);
           Assert.assertEquals(199812341,censusCSV[censusCSV.length - 1].population);
       }
       catch (CensusAnalyserException e){}
   }
    @Test
    public void givenIndiaCensusData_WhenSortedDensityWise_ShouldReturnSortedResult() throws CensusAnalyserException
    {

        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData( INDIA_CENSUS_CSV_FILE_PATH);
        String sortedDensityData=censusAnalyser.getDensityWiseSortedData();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedDensityData,IndiaCensusCSV[].class);
        Assert.assertEquals(1102,censusCSV[censusCSV.length - 1].densityPerSqKm);
    }
    @Test
    public void givenIndiaCensusData_WhenSortedAreaWise_ShouldReturnSortedResult() throws CensusAnalyserException
    {

        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData( INDIA_CENSUS_CSV_FILE_PATH);
        String sortedAreaData=censusAnalyser.getAreaWiseSortedData();
        IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedAreaData,IndiaCensusCSV[].class);
        Assert.assertEquals(342239,censusCSV[censusCSV.length - 1].areaInSqKm);
    }

    @Test
    public void givenUSCensusData_WhenPassedCorrect_ShouldReturnCorrectRecords() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        int numOfRecords =  censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51,numOfRecords);

    }
    @Test
    public void givenUSCensusData_WhenSortedPopulationWise_ShouldReturnSortedResult() throws CensusAnalyserException
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedUSPopulationData = censusAnalyser.getUSPopulationWiseSortedData();
            USCensusCSV[] censusCSVS = new Gson().fromJson(sortedUSPopulationData,USCensusCSV[].class);
            Assert.assertEquals(37253956, censusCSVS[censusCSVS.length-1].population);
        }
        catch (CensusAnalyserException e){}

    }
    @Test
    public void givenUSCensusData_WhenSortedPopulationDensityWise_ShouldReturnSortedResult() throws CensusAnalyserException
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedUSPopulationData = censusAnalyser.getUSPopulationDensityWiseSortedData();
            USCensusCSV[] censusCSVS = new Gson().fromJson(sortedUSPopulationData,USCensusCSV[].class);
            Assert.assertEquals(3805.61,censusCSVS[censusCSVS.length-1].populationdensity,10000.0);
        }
        catch (CensusAnalyserException e){}

    }
    @Test
    public void givenUSCensusData_WhenSortedTotalAreaWise_ShouldReturnSortedResult() throws CensusAnalyserException
    {
        try
        {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
            String sortedUSAreaData = censusAnalyser.getUSTotalAreaWiseSortedData();
            USCensusCSV[] censusCSVS = new Gson().fromJson(sortedUSAreaData,USCensusCSV[].class);
            Assert.assertEquals(1723338.01,censusCSVS[censusCSVS.length-1].totalarea,10000000.00);
        }
        catch (CensusAnalyserException e){}

    }
}
