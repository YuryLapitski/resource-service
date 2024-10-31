package com.epam.learn.resource_service.service.meta_data;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.function.Function;

@FunctionalInterface
public interface MetaDataExtractor extends Function<MultipartFile, Map<String, String>> {
}
