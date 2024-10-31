package com.epam.learn.resource_service.service.meta_data;

import org.apache.tika.metadata.Metadata;

import java.util.Map;
import java.util.function.Function;

public class MetaDataMapper implements Function<Metadata, Map<String, String>> {
    private static final String NAME = "title";
    private static final String ARTIST = "xmpDM:artist";
    private static final String ALBUM = "xmpDM:album";
    private static final String LENGTH = "xmpDM:duration";
    private static final String YEAR = "xmpDM:releaseDate";

    @Override
    public Map<String, String> apply(Metadata metadata) {
        return Map.of(
                "name", getOrDefault(metadata, NAME),
                "artist", getOrDefault(metadata, ARTIST),
                "album", getOrDefault(metadata, ALBUM),
                "length", getOrDefault(metadata, LENGTH),
                "year", getOrDefault(metadata, YEAR)
        );
    }

    private String getOrDefault(Metadata metadata, String key) {
        String value = metadata.get(key);
        return value != null ? value : "";
    }
}
