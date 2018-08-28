import java.math.BigDecimal;

public class QuoteCalculator {
    public double repaymentMonthlyAmount(int amount, double rate, int period, int frequency) {
        double r = yearlyToFreqRate(rate,frequency);

        double payment = (amount*r)/(1-Math.pow((1+r),-period));

        return Double.isNaN(payment) ? 0.0 : BigDecimal.valueOf(payment).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public double yearlyToFreqRate(double rate, int frequency) {
        //r = (1 + 0.07)^(1/12) - 1
        double freqRate= 0.0;
        freqRate = Math.pow((1.0+(rate)),(1.0/frequency))-1.0;
        return freqRate;
    }

    public double repaymentTotalAmont(double repaymentMonthlyAmount , int period) {
        double repaymentTotalAmount = 0.0;
        repaymentTotalAmount = repaymentMonthlyAmount * period;
        return Double.isNaN(repaymentTotalAmount) ? 0.0 : BigDecimal.valueOf(repaymentTotalAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     *  code copied from https://codereview.stackexchange.com/questions/132882/calculate-loan-rate-based-on-payment-and-duration-in-months
     *
     *  change the calculation of the monthly repayment to my implementation since it used nominal interest rate
     *
     * @param loanAmount
     * @param payment
     * @param termInMonths
     * @return
     */
    public double determineRate(double loanAmount, double payment, int termInMonths , double ratein) {
        //initial guess .05  5% rate.
        double rateGuess = ratein;
        double calculatedPayment = 0.0;
        int wag = 19;
        int times = 0;
        do {
            times++;
            calculatedPayment =repaymentMonthlyAmount((int) loanAmount,rateGuess,termInMonths,12);

            if (payment < calculatedPayment) {
                //rate needs to go down by a percentage of the difference.
                double rateGuessPrior = rateGuess;
                rateGuess = Math.abs(rateGuess - Math.abs(((rateGuess * (1 - (Math.min(calculatedPayment, payment) / Math.max(calculatedPayment, payment)))) * Math.max(wag - times, 1))));
                if (rateGuessPrior < rateGuess) {
                    rateGuess = rateGuessPrior - 0.001; // remove a percent. safety net.
                }
            } else {
                //rate needs to go up by a percentage of the difference.
                double rateGuessPrior = rateGuess;
                rateGuess = Math.abs(rateGuess + Math.abs(((rateGuess * (1 - (Math.min(calculatedPayment, payment) / Math.max(calculatedPayment, payment)))) * Math.max(wag - times, 1))));
                if (rateGuessPrior > rateGuess) {
                    rateGuess = rateGuessPrior + 0.001; // remove a percent. safety net.
                }
            }
        } while (Math.max(payment, calculatedPayment) - Math.min(payment, calculatedPayment) > 0.0001);

        //setScale(1, BigDecimal.ROUND_UP) the only reason why 1 place and round_up because 6.9 was not enough, 6.93 but rate of the  loan should be displayed to one decimal place.
        return Double.isNaN(rateGuess) ? 0.0 : BigDecimal.valueOf(rateGuess).multiply(BigDecimal.valueOf(100.0)).setScale(1, BigDecimal.ROUND_UP).doubleValue();
    }




}
