package com.gg4703.toolkit.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gg4703.toolkit.support.DataSet;
import com.gg4703.toolkit.support.ExcelSheet;
import com.gg4703.toolkit.support.Record;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("ALL")
public class ExcelOperClass {
    private static String EXCEL_2003 = ".xls";
    private static String EXCEL_2007 = ".xlsx";

    /**
     * 通过POI方式读取Excel
     */
    public static DataSet readExcelPOI(String filePath, Integer cons) throws Exception {
        File excelFile = new File(filePath);
        if (excelFile != null) {
            String fileName = excelFile.getName();
            fileName = fileName.toLowerCase();
            if (fileName.toLowerCase().endsWith(EXCEL_2003)) {
                DataSet dataSet = readExcelPOI2003(excelFile, cons);
                return dataSet;
            }
            if (fileName.toLowerCase().endsWith(EXCEL_2007)) {
                DataSet dataSet = readExcelPOI2007(excelFile, cons);
                return dataSet;
            }
        }
        return null;
    }

    /**
     * 将数据写入到Excel文件中
     *
     * @param filePath
     * @param dataSet
     * @throws Exception
     */
    public static void writeExcelPOI(String filePath, DataSet dataSet) throws Exception {
        if (filePath != null) {
            File excelFile = new File(filePath);
            String fileName = excelFile.getName();
            Workbook workbook = null;
            if (fileName.toLowerCase().endsWith(EXCEL_2003)) {
                workbook = new HSSFWorkbook();
            }
            if (fileName.toLowerCase().endsWith(EXCEL_2007)) {
                workbook = new XSSFWorkbook();
            }
            // 支持多sheet数据写入
            List<ExcelSheet> sheets = dataSet.getSheetList();
            if (CollectionUtils.isNotEmpty(sheets)) {
                for (ExcelSheet excelSheet : sheets) {
                    String sheetName = excelSheet.getSheetName();
                    String[] headers = excelSheet.getHeaders();
                    List<String[]> dataList = excelSheet.getDatasList();
                    // 创建sheet对象
                    Sheet sheet1 = (Sheet) workbook.createSheet(sheetName);
                    dataList.add(0, headers);
                    // 循环数据将写入Excel中
                    int listSize = dataList.size();
                    for (int i = 0; i < listSize; i++) {
                        Row row = sheet1.createRow(i);
                        String[] columns = dataList.get(i);
                        for (int j = 0; j < columns.length; j++) {
                            String value = columns[j];
                            Cell cell = row.createCell(j);
                            cell.setCellValue(columns[j]);
                        }
                    }
                }
            }

            // 写入到文件输出流中
            OutputStream outputStream = new FileOutputStream(excelFile);
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
    }

    /**
     * 读取Excel2003的表单
     *
     * @param excelFile
     * @return
     * @throws Exception
     */
    private static DataSet readExcelPOI2003(File excelFile, Integer rCons) throws Exception {
        List<ExcelSheet> sheetList = new ArrayList<>();
        InputStream input = new FileInputStream(excelFile);
        HSSFWorkbook workBook = new HSSFWorkbook(input);
        // 获取Excel的sheet数量
        Integer sheetNum = workBook.getNumberOfSheets();
        // 循环Sheet表单
        for (int i = 0; i < sheetNum; i++) {
            ExcelSheet excelSheet = new ExcelSheet();
            List<String[]> datasList = new ArrayList<String[]>();
            String[] headers = null;
            HSSFSheet sheet = workBook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            String sheetName = sheet.getSheetName();
            // 获取Sheet里面的Row数量
            Integer rowNum = sheet.getLastRowNum() + 1;
            for (int j = 0; j < rowNum; j++) {
                HSSFRow row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (j > rCons) {
                    //System.out.println("===========");
                    String[] datas = readRowCellValue2003(row);
                    datasList.add(datas);
                } else {
                    if (j == 0) {// 默认第一行为表头
                        headers = readRowCellValue2003(row);
                    }
                }
            }
            excelSheet.setSheetName(sheetName);
            excelSheet.setHeaders(headers);
            excelSheet.setDatasList(datasList);
            sheetList.add(excelSheet);
        }
        DataSet dataSet = new DataSet();
        dataSet.setSheetList(sheetList);
        return dataSet;
    }

    /**
     * 读取Excel2007的表单
     *
     * @param excelFile
     * @return
     * @throws Exception
     */
    private static DataSet readExcelPOI2007(File excelFile, Integer rCons) throws Exception {
        List<ExcelSheet> sheetList = new ArrayList<>();
        InputStream input = new FileInputStream(excelFile);
        XSSFWorkbook workBook = new XSSFWorkbook(input);
        // 获取Sheet数量
        Integer sheetNum = workBook.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            ExcelSheet excelSheet = new ExcelSheet();
            List<String[]> datasList = new ArrayList<String[]>();
            String[] headers = null;
            XSSFSheet sheet = workBook.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            String sheetName = sheet.getSheetName();
            // 获取行值
            Integer rowNum = sheet.getLastRowNum() + 1;
            for (int j = 0; j < rowNum; j++) {
                XSSFRow row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (j > rCons) {
                    // System.out.println("=============");
                    String[] datas = readRowCellValue2007(row);
                    datasList.add(datas);
                } else {
                    if (j == 0) {// 默认第一行为表头
                        headers = readRowCellValue2007(row);
                    }
                }
            }
            excelSheet.setSheetName(sheetName);
            excelSheet.setHeaders(headers);
            excelSheet.setDatasList(datasList);
            sheetList.add(excelSheet);
        }
        DataSet dataSet = new DataSet();
        dataSet.setSheetList(sheetList);
        return dataSet;
    }

    private static String[] readRowCellValue2003(HSSFRow row) {
        Integer cellNum = (int) row.getLastCellNum();
        String[] datas = new String[cellNum];
        for (int k = 0; k < cellNum; k++) {
            HSSFCell cell = row.getCell(k);
            if (cell == null) {
                continue;
            }
            if (cell != null) {
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                String cellValue = "";
                int cellValueType = cell.getCellType();
                if (cellValueType == Cell.CELL_TYPE_STRING) {
                    cellValue = cell.getStringCellValue();
                }
                if (cellValueType == Cell.CELL_TYPE_NUMERIC) {
                    Double number = cell.getNumericCellValue();
                    System.out.println("字符串+++==========" + number.intValue());
                    cellValue = cell.getNumericCellValue() + "";
                }
                datas[k] = cellValue;
            }
        }
        return datas;
    }

    private static String[] readRowCellValue2007(XSSFRow row) {
        Integer cellNum = (int) row.getLastCellNum();
        String[] datas = new String[cellNum];
        for (int k = 0; k < cellNum; k++) {
            XSSFCell cell = row.getCell(k);
            if (cell == null) {
                continue;
            }
            if (cell != null) {
                cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                String cellValue = "";
                int cellValueType = cell.getCellType();
                if (cellValueType == Cell.CELL_TYPE_STRING) {
                    cellValue = cell.getStringCellValue();
                }
                if (cellValueType == Cell.CELL_TYPE_NUMERIC) {
                    Double number = cell.getNumericCellValue();
                    System.out.println("字符串+++==========" + number.toString());
                    cellValue = cell.getNumericCellValue() + "";
                }
                datas[k] = cellValue;
            }
        }
        return datas;
    }

    /**
     * 读取样例2003
     */
    private static void readSample2003() {
        try {
            DataSet dataSet = readExcelPOI("/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/template.xls", 0);
            System.out.println("================================");
            List<ExcelSheet> sheetList = dataSet.getSheetList();
            for (ExcelSheet excelSheet : sheetList) {
                System.out.println(excelSheet.getSheetName());
                List<String[]> dataList = excelSheet.getDatasList();
                String[] headers = excelSheet.getHeaders();
                for (String string : headers) {
                    System.out.println(string);
                }
                System.out.println("================================");
                for (String[] data : dataList) {
                    for (int i = 0; i < data.length; i++) {
                        System.out.println(data[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取样例2007
     */
    private static void readSample2007() {
        try {
            DataSet dataSet = readExcelPOI("D:\\工作簿1.xlsx", 0);
            System.out.println("================================");
            List<ExcelSheet> sheetList = dataSet.getSheetList();
            for (ExcelSheet excelSheet : sheetList) {
                System.out.println(excelSheet.getSheetName());
                List<String[]> dataList = excelSheet.getDatasList();
                String[] headers = excelSheet.getHeaders();
                for (String string : headers) {
                    System.out.println(string);
                }
                System.out.println("================================");
                for (String[] data : dataList) {
                    for (int i = 0; i < data.length; i++) {
                        System.out.println(data[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入样例
     *
     * @throws Exception
     */
    private static void writeSample() throws Exception {
        List<ExcelSheet> sheetList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ExcelSheet excelSheet = new ExcelSheet();
            excelSheet.setSheetName("Sheet样例" + (i + 1));
            excelSheet.setHeaders(new String[]{"列表1", "列表2"});
            List<String[]> datasList = new ArrayList<>();
            for (int j = 0; j < 20; j++) {
                String[] data = new String[]{"数据Cell0" + (j + 1), "数据Cell1" + (j + 1)};
                datasList.add(data);
            }
            excelSheet.setDatasList(datasList);
            sheetList.add(excelSheet);
        }

        DataSet dataSet = new DataSet();
        dataSet.setSheetList(sheetList);
        writeExcelPOI("/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/template.xls", dataSet);
    }

    public static void main(String[] args) throws Exception {
        // 读取数据
        Reader reader = new CSVReader();
        List<Record> records = reader.read(null);

        //写入模板
        try {
            //读取历史数据生成分类池
            Map<String, String> categoryMap = new HashMap<String, String>();
            DataSet myMoneyDataSet = readExcelPOI("/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/myMoney.xls", 0);
            List<ExcelSheet> myMoneySheetList = myMoneyDataSet.getSheetList();
            for (ExcelSheet excelSheet : myMoneySheetList) {
                List<String[]> dataList = excelSheet.getDatasList();
                for (String[] data : dataList) {
                    if (StringUtils.isNotBlank(data[10])) {
                        if(data[10].indexOf("京东") != -1){
                            continue;
                        }
                        categoryMap.put(StringUtils.strip(data[10], "\""), StringUtils.strip(data[1], "\"") + "," + StringUtils.strip(data[2], "\""));
                    }
                }
            }

            DataSet dataSet = readExcelPOI("/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/template.xls", 0);
            List<ExcelSheet> sheetList = dataSet.getSheetList();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (ExcelSheet excelSheet : sheetList) {

                //data
                List<String[]> dataList = new ArrayList<>();
                for (Record record : records) {
                    // 获取分类
                    String category = null;
                    String subCategory = null;
                    String remark = record.getRemark();
                    if (StringUtils.isNotBlank(remark)) {
                        String content = categoryMap.get(StringUtils.strip(remark));
                        if (StringUtils.isNotBlank(content)) {
                            String[] str = content.split(",");
                            category = str[0];
                            subCategory = str[1];
                        }
                    }

                    String[] item = new String[]{record.getTransactionType(), formatter.format(record.getDate()), category, subCategory, record.getAccountType(), null, record.getAmount().toString(), record.getMember(), null, null, record.getRemark()};
                    dataList.add(item);
                }
                excelSheet.setDatasList(dataList);
                break;
            }
            dataSet.setSheetList(sheetList);
            writeExcelPOI("/Users/xujunjie3/Work/IdeaProjects/toolkit/src/main/resources/thirdParty/template1.xls", dataSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
