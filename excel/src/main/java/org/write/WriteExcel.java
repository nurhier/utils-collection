package org.write;

import com.alibaba.excel.EasyExcel;

import java.util.List;

/**
 * @author nurhier
 * @date 2020/1/14
 */
public class WriteExcel {

    public static <T> void write(String fileName, String sheetName, Class<T> clazz, List<T> dataList) {
        EasyExcel.write(fileName, clazz).sheet(sheetName).doWrite(dataList);
    }
}
