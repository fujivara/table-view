package com.example.tableview.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileIO {
    public static List<List<String>> readExcel(Path filePath) {
        List<List<String>> table = new ArrayList<>();

        try {
            File file = new File(filePath.toString());
            FileInputStream fis = new FileInputStream(file);

            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int rowIndex = 0;

            for (Row row : sheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                table.add(new ArrayList<>());

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case CellType.STRING:
                            table.get(rowIndex).add(cell.getStringCellValue());
                            break;
                        case CellType.BLANK:
                            table.get(rowIndex).add("");
                            break;
                        default:
                            table.get(rowIndex).add(cell.getNumericCellValue()+"");
                    }
                }
                rowIndex++;
                System.out.println();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return table;
    }

    public static List<List<String>> readTxt(Path path) {
        List<List<String>> tableData = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] rowValues = line.split("\t");

                List<String> row = new ArrayList<>(Arrays.asList(rowValues));

                tableData.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tableData;

    }
}
