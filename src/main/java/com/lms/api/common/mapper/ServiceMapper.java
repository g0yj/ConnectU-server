package com.lms.api.common.mapper;

import com.lms.api.common.code.YN;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class)
public interface ServiceMapper {

    default Boolean toBoolean(YN yn) {
        return YN.to(yn);
    }
    default YN toYN(Boolean bool) {
        return YN.of(bool);
    }
}
