package com.thoughtworks.capability.gtb.entrancequiz.common;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

// GTB: + 有单独的负责转换的工具类
public class ConvertTool {
    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    public static <T> T convert(Object source, Class<T> destinationClass) {
        return MAPPER.map(source, destinationClass);
    }
}
