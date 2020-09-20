package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.dto.EducationDto;
import com.thoughtworks.capability.gtb.entrancequiz.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping("/users/{id}/educations")
    @ResponseStatus(HttpStatus.OK)
    public List<EducationDto> getEducationByUserId(@PathVariable Long id) {
        return educationService.getEducationByUserId(id);
    }

    @PostMapping("/users/{id}/educations")
    @ResponseStatus(HttpStatus.CREATED)
    public EducationDto createEducation(@RequestBody @Valid EducationDto educationDto,
                                        @PathVariable Long id) {
        return educationService.saveEducation(id, educationDto);
    }
}
