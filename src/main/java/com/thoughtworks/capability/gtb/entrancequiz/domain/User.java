package com.thoughtworks.capability.gtb.entrancequiz.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    @Min(value = 17)
    private Long age;

    @NotEmpty
    @Size(min = 8, max = 512)
    private String avatar;

    @Size(max = 1024)
    @Builder.Default
    private String description = "";
}
