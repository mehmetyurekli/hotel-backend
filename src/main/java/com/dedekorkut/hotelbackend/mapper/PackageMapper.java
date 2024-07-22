package com.dedekorkut.hotelbackend.mapper;

import com.dedekorkut.hotelbackend.dto.PackageDto;
import com.dedekorkut.hotelbackend.entity.Package;

public class PackageMapper {

    public static PackageDto map(Package aPackage){
        return PackageDto.builder()
                .id(aPackage.getId())
                .name(aPackage.getName())
                .build();
    }

    public static Package map(PackageDto aPackage){
        return Package.builder()
                .id(aPackage.getId())
                .name(aPackage.getName())
                .build();
    }
}
