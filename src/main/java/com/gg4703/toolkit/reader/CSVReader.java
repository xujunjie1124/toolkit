package com.gg4703.toolkit.reader;

import com.csvreader.CsvReader;
import com.gg4703.toolkit.support.Record;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CSVReader implements Reader {
    @Override
    public List<Record> read(String file) throws Exception {
        String filePath = "/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/userdata-jj-9-utf8.csv";

        List<Record> records = new ArrayList<Record>();
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath,',', Charset.forName("UTF-8"));

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                Record record = new Record();
                // 读一整行
                System.out.println(csvReader.getRawRecord());
                // 读这行的某一列
                System.out.println(csvReader.get(3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
