package com.neznayka.www.service;

import com.neznayka.www.dao.config.ConfigDictionaryDAOIntf;
import com.neznayka.www.hibernate.Logging;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExportService {
    private static final String CLASS_NAME = ExportService.class.getName();
    private static final Logger log = Logger.getLogger(CLASS_NAME);

    private final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    @Qualifier("ConfigDAO")
    ConfigDictionaryDAOIntf configDAO;

    public void exportToExcel(HttpServletResponse response){
        try {

            final File file = getFileExportToExcel();
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setContentLength((int) file.length());
            response.setHeader(
                    "Content-Disposition", "attachment; filename*=utf-8''"
                            + UriUtils.encodeScheme("Вопросы и Ответы.xlsx", "UTF-8")
            );
            response.setHeader("Set-Cookie", "fileDownload=true; path=/");

            FileCopyUtils.copy(
                    new FileInputStream(file), response.getOutputStream()
            );
        } catch (final IOException e) {
          log.info("Ошибка экспорта "+e.getMessage());
        }
    }

    private File getFileExportToExcel() {
        try {
            final SXSSFWorkbook workbook = new SXSSFWorkbook();
            List<Logging> logs = configDAO.getLogging();
            fillWorkbookIncoming(logs, workbook);
            final File file = File.createTempFile("logs", "export");
            final FileOutputStream out = new FileOutputStream(file);
            workbook.write(out);
            return file;
        } catch (final IOException e) {
            log.info("Ошибка экспорта "+e.getMessage());
            return  null;
        }
    }

    private void fillWorkbookIncoming(List<Logging> logs, SXSSFWorkbook workbook) {
        createTemplateExcelIncoming("Вопросы и ответы", workbook);

        Sheet sheet = workbook.getSheet("Вопросы и ответы");
        int rowIndex = 3;
        int colIndex = 0;
        Row row = null;
        CellStyle cellStyle = getCellStyle(workbook, true, true, true, true, true, true, true, false);
        for(Logging log:logs){
            row = sheet.createRow(rowIndex++);
            colIndex = 0;
            createCell(colIndex++, log.getQuestion(), row, cellStyle);
            createCell(colIndex++, log.getMessage(), row, cellStyle);
            createCell(colIndex++, formatDate(log.getTime()), row, cellStyle);
            createCell(colIndex++, ""+log.getId(), row, cellStyle);
        }
    }

    private void createTemplateExcelIncoming(String sheetName, SXSSFWorkbook workbook) {

        Sheet sheet = workbook.createSheet(sheetName);
        Row row1 = sheet.createRow(0);
        Row row2 = sheet.createRow(1);
        Row row3 = sheet.createRow(2);
        int index = 0;
        sheet.setColumnWidth(index++, 50*256);           // Вопрос
        sheet.setColumnWidth(index++, 62*256);           // Ответ
        sheet.setColumnWidth(index++, 21*256);           // Время
        sheet.setColumnWidth(index++, 21*256);           // Id пользователя


        CellStyle cellStyle = getCellStyle(workbook, true, true, true, true, true, true, true, false);
        row1.setHeightInPoints(50);
        row2.setHeightInPoints(50);
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, --index);
        sheet.addMergedRegion(region);
        createCell(index++, "", row1, getCellStyle(workbook, false, false, true, false, false, false, false, false));
        createCell(0, "Лог вопросов и ответов", row1, getCellStyle(workbook, false, false, true, false, true, true, true, true));
        for (int i = 0; i < index; i++) {
            region = new CellRangeAddress(1, 2, i, i);
            sheet.addMergedRegion(region);
            row3.createCell(i).setCellStyle(getCellStyle(workbook, false, true, true, true, true, true, true, false));
        }

        index = 0;
        createCell(index++, "Вопрос", row2, cellStyle);
        createCell(index++, "Ответ", row2, cellStyle);
        createCell(index++, "Время", row2, cellStyle);
        createCell(index++, "Id пользователя", row2, cellStyle);



    }


    Cell createCell(int indexCell, String valueCell, Row row, CellStyle cellStyle) {
        Cell cell = row.createCell(indexCell);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellValue(valueCell);

        return cell;
    }


    CellStyle getCellStyle(final SXSSFWorkbook workbook, final boolean top, final boolean bottom, final boolean right, final boolean left, final boolean valigncenter, final boolean aligncenter, final boolean wrapText, final boolean bold) {
        final CellStyle cellStyle = workbook.createCellStyle();
        if (top) cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        if (bottom) cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        if (right) cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        if (left) cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        if (valigncenter) cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        if (aligncenter) cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        if (wrapText) cellStyle.setWrapText(true);
        if (bold) {
            Font font = workbook.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            cellStyle.setFont(font);
        }
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private String formatDate(Date date) {
        if (date != null) {
            return sdf.format(date);
        }
        return "";
    }
}
