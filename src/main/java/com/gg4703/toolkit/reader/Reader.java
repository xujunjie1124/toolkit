package com.gg4703.toolkit.reader;

/**
 * 读取器
 * @author 须俊杰
 */
public interface Reader<T> {
    T read(String xml, Class<T> clazz) throws Exception;
}
