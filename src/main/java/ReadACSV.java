import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadACSV {

    public List<DataObject> readACSV(Path path) throws IOException {


        Iterator<DataObject> csvUserIterator;
        CsvToBean<DataObject> csvToBean2;
        List<DataObject> dataObjects = new ArrayList<>();

        try {
            Reader reader1 = Files.newBufferedReader(path);
            csvToBean2 = new CsvToBeanBuilder(reader1).withType(DataObject.class).withIgnoreLeadingWhiteSpace(true).build();

            csvUserIterator = csvToBean2.iterator();
            //csvToBean2.setOrderedResults(true);

            while (csvUserIterator.hasNext()) {
                DataObject csvUser = csvUserIterator.next();
                dataObjects.add(csvUser);
            }
        } catch (IOException e) {
            throw e;
        } catch ( Exception e1){
            throw e1;
        }
        return dataObjects;
    }

}
