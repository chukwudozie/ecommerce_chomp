package com.chompfooddeliveryapp.controller;

import com.chompfooddeliveryapp.dto.ImageDTO;
import com.chompfooddeliveryapp.model.meals.Images;
import com.chompfooddeliveryapp.service.serviceInterfaces.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class ImageController {

    private final ImageService imageService;
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @PostMapping("/addimage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addAndSaveImages(@RequestBody ImageDTO imageDTO) throws IOException {

        final String image = imageService.saveImages(imageDTO.getImageName());
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}