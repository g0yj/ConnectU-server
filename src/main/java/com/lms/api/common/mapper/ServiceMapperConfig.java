package com.lms.api.common.mapper;

import org.mapstruct.*;

@MapperConfig(
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE
)
public interface ServiceMapperConfig {

}
