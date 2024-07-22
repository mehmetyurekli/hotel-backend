package com.dedekorkut.hotelbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPackageServiceDto {
    private long packageId;
    private long[] serviceId;
}
