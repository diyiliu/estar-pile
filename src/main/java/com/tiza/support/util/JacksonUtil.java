package com.tiza.support.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: JacksonUtil
 * Author: DIYILIU
 * Update: 2016-03-22 9:25
 */
public class JacksonUtil {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 如果为HashMap(key-value)格式;
     * key自动转为字符串类型
     *
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        String rs = null;
        try {
            rs = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        return rs;
    }

    public <T> T toObject(String content, Class<T> clazz) throws IOException {

        return mapper.readValue(content, clazz);
    }

    public List toList(String content, Class clazz) throws IOException {
        JavaType javaType = getCollectionType(ArrayList.class, clazz);

        return mapper.readValue(content, javaType);
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 设置日期格式
     *
     * @param dateFormat
     */
    public void setDateFormat(DateFormat dateFormat){

        mapper.setDateFormat(dateFormat);
    }

    /**
     * 设置名称格式
     *
     * @param namingStrategy
     */
    public void setPropertyNamingStrategy(PropertyNamingStrategy namingStrategy){

        mapper.setPropertyNamingStrategy(namingStrategy);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }
}
