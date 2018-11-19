package com.gg4703.toolkit.reader;

import com.csvreader.CsvReader;
import com.gg4703.toolkit.support.Record;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"ALL", "AlibabaRemoveCommentedCode"})
public class CSVReader implements Reader {


    @SuppressWarnings("AlibabaRemoveCommentedCode")
    @Override
    public List<Record> read(String file) throws Exception {
        String filePath = "/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/userdata-jj-9-utf8.csv";

        List<Record> records = new ArrayList<Record>();
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()) //noinspection AlibabaRemoveCommentedCode
            {
                Record record = new Record();
                record.setTransactionType("支出");
                record.setDate(DateUtils.parseDate(csvReader.get(1), "yyyy-MM-dd"));
                record.setCategory(null);
                record.setSubCategory(null);
                record.setAccountType("信用卡");

                String amount = new String(csvReader.get(5));
                amount = amount.replace(",", "");
                record.setAmount(new BigDecimal(amount));
                record.setMember("老公");
                record.setRemark(csvReader.get(3));
                records.add(record);
//                // 读一整行
//                System.out.println(csvReader.getRawRecord());
//                // 读这行的某一列
//                System.out.println(csvReader.get(3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }


}
