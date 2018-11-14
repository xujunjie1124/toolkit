package com.gg4703.toolkit;

import com.gg4703.toolkit.reader.CSVReader;
import com.gg4703.toolkit.reader.Reader;
import com.gg4703.toolkit.support.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolkitApplicationTests {

    @Test
    public void contextLoads() {
        try {
            Reader reader = new CSVReader();
            List<Record> records = reader.read(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
