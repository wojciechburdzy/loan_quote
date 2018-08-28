To Build the application please have maven

and do the following

mvn clean install package
go to the project dir and then ./target/ and then please find test.exe file

test.exe to work would require a <name of the>.csv to be in the same directory
<name of the>.csv cannot contains the names didnt use regex




References
Calculation for the repayment taken from this solution
answered Jun 1 '16 at 8:24 Chris Degnen
https://money.stackexchange.com/questions/54757/loan-repayment-calculation-and-monthly-compounding-interest-problem

The calculation can be made on the basis that the loan is equal to the sum of the repayments discounted to present value. (For more information see Calculating the Present Value of an Ordinary Annuity.)

With

s = value of loan
d = periodic repayment
r = periodic interest rate
n = number of periods
Deriving the loan formula from the simple discount summation.

d = (r s)/(1 - (1 + r)^-n)
As you can see, this is the same as the loan formula given here.

In the UK and Europe APR is usually quoted as the effective interest rate while in the US it is quoted as a nominal rate. (Also, in the US the effective APR is usually called the annual percentage yield, APY, not APR.)

Using the effective interest rate finds the expected answer.

Requested amount, s = £1000
Effective Rate: 7.0%, ∴ monthly rate, r = (1 + 0.07)^(1/12) - 1
Months, n = 36

d = (r s)/(1 - (1 + r)^-n) = 30.7789
The total repayment is £30.78 * n = £1108.08


I struggled with a interest rate calculation for the final

found a solution looks good

https://codereview.stackexchange.com/questions/132882/calculate-loan-rate-based-on-payment-and-duration-in-months