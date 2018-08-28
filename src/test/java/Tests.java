import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;
import java.util.List;

public class Tests {

    int input= 0;
    InputChecker inputChecker;
    QuoteCalculator quoteCalculator;

    @Before
    public void setup(){
        inputChecker = new InputChecker();
         quoteCalculator = new QuoteCalculator();
    }

    @Test
    public void testIfInputISBetween(){
        //£1000 and £15000 inclusive and 100 increments
        input =900;
        Assert.assertFalse(inputChecker.checkRequestedAmount(input));
        input =999;
        Assert.assertFalse(inputChecker.checkRequestedAmount(input));
        input = 1001;
        Assert.assertFalse(inputChecker.checkRequestedAmount(input));
        input = 15001;
        Assert.assertFalse(inputChecker.checkRequestedAmount(input));
        input = 15100;
        Assert.assertFalse(inputChecker.checkRequestedAmount(input));

        input = 1000;
        Assert.assertTrue(inputChecker.checkRequestedAmount(input));
        input = 1100;
        Assert.assertTrue(inputChecker.checkRequestedAmount(input));
        input = 15000;
        Assert.assertTrue(inputChecker.checkRequestedAmount(input));
    }
    @Test
    public void testYearlyRatetoThefrq(){
        double retRate = 0.0;
        double rate = 0.07;
        int frequency = 12;
        retRate = quoteCalculator.yearlyToFreqRate(rate,frequency);
        //0.18920711500272103
        Assert.assertEquals(0.005654145387405274,retRate, 0.0000000001);
    }

    @Test
    public void testRepaymentMonthlyAmount(){
        /*
        Requested amount: £1000
        Rate: 7.0%
        Monthly repayment: £30.78
        Total repayment: £1108.10
        */


        int amount = 1000;
        double rate = 0.07;
        int period = 36;
        int frequency = 12;
        double repaymentMonthlyAmount = 30.78;
        double repaymentMonthlyAmountActual = 0.0;


        repaymentMonthlyAmountActual = quoteCalculator.repaymentMonthlyAmount(amount, rate , period , frequency);

        Assert.assertEquals(repaymentMonthlyAmount,repaymentMonthlyAmountActual,0.01);

    }

    @Test
    public void testRepaymentMonthlyAmountTestWithAclculaterRate(){

        int amount = 600;
        double rate = 0.075;
        int period = 36;
        int frequency = 12;
        double repaymentMonthlyAmount = 18.450000000000003;
        double repaymentMonthlyAmountActual = 0.0;

        double guessrate = quoteCalculator.determineRate(amount,repaymentMonthlyAmount,period,0.075);
        repaymentMonthlyAmountActual = quoteCalculator.repaymentMonthlyAmount(amount,guessrate/100,36,12);


/*        repaymentMonthlyAmountActual = quoteCalculator.repaymentMonthlyAmount(amount, rate , period , frequency);

        Assert.assertEquals(repaymentMonthlyAmount,repaymentMonthlyAmountActual,0.01);*/

    }

    @Test
    public void testTotalAmount(){
        //Total repayment: £1108.10

        //ToDO
        // check with them why 1108.10  and not 1108.08 total amount rounded to 1 decimal

        double repaymentMonthlyAmount = 30.78;
        double expectedTotalRepaymentAmount = 1108.08;
        double actualTotalRepaymentAmount = 0.0;
        int period = 36;

        actualTotalRepaymentAmount = quoteCalculator.repaymentTotalAmont(repaymentMonthlyAmount,period);

        Assert.assertEquals(expectedTotalRepaymentAmount,actualTotalRepaymentAmount,0.01);

    }

    /*@Test
    public void readAcsv(){
        ReadACSV readACSV = new ReadACSV();
        List<DataObject> myObjects = null;
        try {
            myObjects = readACSV.readACSV("Market Data for Exercise - .csv.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(7,myObjects.size());

    }*/
    @Test
    public void readAcsvWithNoFile(){
        ReadACSV readACSV = new ReadACSV();
        String str = "";
        List<DataObject> dataObjects = null;
        try {
            dataObjects = readACSV.readACSV(Paths.get(""));
        }catch (Exception e){
            if (e instanceof AccessDeniedException){
                str = "CSV File not found please check the path or name";
            }

        }
        Assert.assertEquals("CSV File not found please check the path or name",str);
        Assert.assertEquals(null,dataObjects);
    }



    @Test
    public  void testdetermineRate(){
        double repaymentMonthlyAmount = 30.78;
        double expectedTotalRepaymentAmount = 1108.08;
        double actualTotalRepaymentAmount = 0.0;
        int period = 36;

        actualTotalRepaymentAmount = (double) quoteCalculator.determineRate(1000.0,repaymentMonthlyAmount,36,0.072);

    }

    /*@Test
    public void testMainApp() throws IOException {
        App app = new App();
        String[] arg = {"market.csv", "1000"};
        app.main(arg);
    }*/




}
