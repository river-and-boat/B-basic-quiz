package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.domain.Education;
import com.thoughtworks.capability.gtb.entrancequiz.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
public class EducationController {

    private static final String PATTERN_ID = "/^[1-9]\\d*$/";

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/users/{id}/educations")
    @ResponseStatus(HttpStatus.OK)
    public List<Education> getEducationByUserId(@PathVariable @Validated
                                                @Pattern(regexp = PATTERN_ID) Long id) {
        return educationService.getEducationByUserId(id);
    }

    @PostMapping("/users/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public Education createEducation(@RequestBody @Valid Education education,
                                     @PathVariable @Validated @Pattern(regexp = PATTERN_ID) Long id) {
        education.setUserId(id);
        return educationService.saveEducation(education);
    }
}
