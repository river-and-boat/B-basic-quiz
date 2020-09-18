package com.thoughtworks.capability.gtb.entrancequiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// GTB: - 这里的 Education 其实不属于 domain，是个 DTO
public class EducationDto {
    private Long userId;
    @NotNull
    @Min(1900)
    @Max(2020)
    private Long year;
    @NotEmpty
    @Size(min = 1, max = 256)
    private String title;
    @NotEmpty
    @Size(min = 1, max = 4096)
    private String description;
}
