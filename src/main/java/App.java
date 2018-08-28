import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class App {


    public static void main(String[] args) throws IOException {

        int freq = 12;
        int period = 36;
        int reuestOrig = 0;
        int reuest = 0;
        double totalAvila = 0;

        String out = "";

        List<DataObject> dataObjects = new ArrayList<>();
        ;

        int argsSize = args.length;

        if (2 != argsSize) {
            out = "Not enough arguments please check the input 'cmd> [application] [market_file] [loan_amount]' ";
        } else if (2 == argsSize) {
            reuestOrig = Integer.parseInt(args[1]);
            InputChecker inputChecker = new InputChecker();
            if (!inputChecker.checkRequestedAmount(reuestOrig)) {
                out = "value is not of increments of 100 and it needs to be between £1000 and £15000 inclusive";
            }
        } else {
                out = "To many arguments please check the input 'cmd> [application] [market_file] [loan_amount]'" + "\n" +
                    "File name cannot contain spaces  ";
        }
        reuest = reuestOrig;


        String current = new java.io.File(".").getCanonicalPath();
        Path path = Paths.get(current, args[0].toString()).normalize();
        //if by now the string out is not empty then
        // the dataObjects size should increase for the next if statement
        if (out.isEmpty()) {
            try {
                ReadACSV readACSV = new ReadACSV();

                dataObjects = readACSV.readACSV(path);
            } catch (Exception e) {
                if (e instanceof AccessDeniedException) {
                    out = "CSV File not found please check the path or name";
                }
                if (e instanceof NoSuchFileException) {
                    out = "CSV File not found please check the path or name";
                }

            }
        }


        if (0 == dataObjects.size()&&out.isEmpty()) {
            out = "No data in the file";
        } else if (0 < dataObjects.size()) {
            dataObjects.sort(Comparator.comparing(DataObject::getRate).thenComparingDouble(dataObject -> Double.parseDouble(dataObject.getAvailable())));
            for (DataObject e : dataObjects) {
                totalAvila += Double.parseDouble(e.getAvailable());
            }
            if (totalAvila < reuestOrig) {
                out = "It is not possible to provide a quote at that time.";
            } else {
                QuoteCalculator quoteCalculator = new QuoteCalculator();
                List<Quote> quotes = new ArrayList<>();

                for (int i = 0; i < dataObjects.size(); i++) {
                    int first = Integer.parseInt(dataObjects.get(i).getAvailable());

                    //exit the for loop when request goes to zero
                    if (0 == reuest) {
                        break;
                    }

                    if (reuest <= first) {
                        dataObjects.get(i).setAvailable(String.valueOf(first - reuest));
                        Quote quote = new Quote();
                        double ratei = Double.parseDouble(dataObjects.get(i).getRate());
                        double mamount = quoteCalculator.repaymentMonthlyAmount(reuest, ratei, period, freq);
                        double totali = quoteCalculator.repaymentTotalAmont(mamount, period);
                        quote.setAmountMonthly(mamount);
                        quote.setRate(ratei);
                        quote.setTotalAmount(totali);
                        quotes.add(quote);
                        reuest = 0;
                    } else {
                        reuest = reuest - first;
                        dataObjects.get(i).setAvailable(String.valueOf(0));
                        Quote quote = new Quote();
                        double ratei = Double.parseDouble(dataObjects.get(i).getRate());
                        double mamount = quoteCalculator.repaymentMonthlyAmount(first, ratei, period, freq);
                        double totali = quoteCalculator.repaymentTotalAmont(mamount, period);
                        quote.setAmountMonthly(mamount);
                        quote.setRate(ratei);
                        quote.setTotalAmount(totali);
                        quotes.add(quote);

                    }

                }

                double totalMonthly = 0.0;
                double totalTotal = 0.0;
                double totalRate = 0.0;
                double avarageRate = 0.0;

                for (Quote q : quotes) {
                    totalMonthly = totalMonthly + q.getAmountMonthly();
                    totalTotal = totalTotal + q.getTotalAmount();
                    avarageRate = avarageRate + q.getRate();
                }
                avarageRate = avarageRate / quotes.size();
                totalRate = quoteCalculator.determineRate(reuestOrig, totalMonthly, period, avarageRate);
                out = "Requested amount: £" + reuestOrig + "\n" +
                        "Rate: " + totalRate + "%" + "\n" +
                        "*it is a yearly rate rounded up to the 1 decimal place" + "\n" +
                        "Monthly repayment: £" + totalMonthly + "\n" +
                        "Total repayment: £" + totalTotal;

            }
        }
        System.out.println(out);
    }
}
