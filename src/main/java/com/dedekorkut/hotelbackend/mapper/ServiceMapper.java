package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.ServiceDto;
import com.dedekorkut.hotelbackend.entity.Service;

public class ServiceMapper {

    public static ServiceDto map(Service service) {
        return ServiceDto.builder()
                .id(service.getId())
                .name(service.getName())
                .build();
    }

    public static Service convertToEntity(ServiceDto service) {
        return Service.builder()
                .id(service.getId())
                .name(service.getName())
                .build();
    }
}
