package com.dedekorkut.hotelbackend.dto.input;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewPackageServiceDto {
    private Long packageId;
    private Long[] serviceId;
}
