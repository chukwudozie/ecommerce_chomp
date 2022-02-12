package com.chompfooddeliveryapp.service.serviceInterfaces;

import com.chompfooddeliveryapp.dto.ImageDTO;
import com.chompfooddeliveryapp.model.meals.Images;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ImageService {

    Images saveImages (ImageDTO imageDTO) throws IOException;
}
