package com.myframe.core.util;

import com.google.common.base.Optional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

/**
 * Excel导出工具。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class ExcelUtils {

    public static String getCellValue(Row r, int c) {
        Cell cell = r.getCell(c);
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                return String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()).trim();
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_ERROR:
                return ErrorEval.getText(cell.getErrorCellValue());
            default:
                return "";
        }
    }

    public static <T> Workbook export(final List<T> datas, List<Pair<String, String>> valueDescs) {
        return export(datas, valueDescs, null);
    }

    /**
     * 导出数据到excel。
     *
     * @param datas 要导出的数据。
     * @param valueDescs 字段和描述键值对。
     * @param os 输出流。
     * @param <T> 类类型
     * @return 工作簿对象
     */
    public static <T> Workbook export(final List<T> datas, List<Pair<String, String>> valueDescs, OutputStream os) {
        if (CollectUtils.isEmpty(datas)) {
            throw new RuntimeException("导出数据不能为空！");
        }
        if (CollectUtils.isEmpty(valueDescs)) {
            throw new RuntimeException("导出字段和描述映射不能为空！");
        }
        Class<?> classType = null;
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        // 生成头行
        int i = 0;
        Row header = sheet.createRow(0);
        for (Pair<String, String> entry : valueDescs) {
            Cell cell = header.createCell(i);
            String fieldDesc = entry.getValue();
            cell.setCellValue(fieldDesc);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            i++;
        }

        for (int rownum = 0, len = datas.size(); rownum < len; ++rownum) {
            T data = datas.get(rownum);
            if (null == classType) {
                classType = data.getClass();
            }
            Row row = sheet.createRow(rownum + 1);
            int colNum = 0;
            for (Pair<String, String> entry : valueDescs) {
                Cell cell = row.createCell(colNum);
                colNum++;
                String fieldName = entry.getKey();
                Object cellValue = ReflectUtils.invokeGetter(data, fieldName);
                if (null == cellValue) {
                    cell.setCellValue("");
                    continue;
                }
                Optional<Field> field = ReflectUtils.getAccessibleField(data, fieldName);
                if (!field.isPresent()) {
                    continue;
                }
                String fieldTypeName = field.get().getType().getSimpleName().toUpperCase();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if ("BOOLEAN".equals(fieldTypeName)) {
                    cell.setCellValue(cellValue.toString());
                } else if ("DATE".equals(fieldTypeName)) {
                    cell.setCellValue(DateUtils.formatDateTime((Date) cellValue));
                } else {
                    cell.setCellValue(cellValue.toString());
                }
            }
        }
        if (null != os) {
            try {
                workbook.write(os);
                os.flush();
                os.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return workbook;
    }

}
