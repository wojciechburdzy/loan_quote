public class InputChecker {


    public boolean checkRequestedAmount(int input) {
        boolean returnSt = false;
        //if value is of increments of 100
        if (  0 == input % 100) {
            ////£1000 and £15000 inclusive
            if ( 1000 <= input && 15000 >= input) {
                returnSt = true;
            }
        }
        return returnSt;
    }
}
