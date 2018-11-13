package com.gg4703.toolkit;

import com.gg4703.toolkit.reader.CSVReader;
import com.gg4703.toolkit.reader.Reader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToolkitApplicationTests {

    @Test
    public void contextLoads() {
        try {
            Reader<Object> reader = new CSVReader<Object>();
            reader.read(null,Object.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
