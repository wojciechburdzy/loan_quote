import com.opencsv.bean.CsvBindByName;

public class DataObject {
    @CsvBindByName
    String Lender;
    @CsvBindByName
    String Rate;
    @CsvBindByName
    String Available;

    public String getLender() {
        return Lender;
    }

    public void setLender(String lender) {
        Lender = lender;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getAvailable() {
        return Available;
    }

    public void setAvailable(String available) {
        Available = available;
    }
}
