package com.gg4703.toolkit.reader;

import com.gg4703.toolkit.support.Record;

import java.util.List;

/**
 * 读取器
 * @author 须俊杰
 */
public interface Reader {
    List<Record> read(String file) throws Exception;
}
