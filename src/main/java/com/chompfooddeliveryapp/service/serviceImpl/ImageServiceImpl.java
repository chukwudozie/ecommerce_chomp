package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.configuration.CloudinaryConfig;
import com.chompfooddeliveryapp.dto.ImageDTO;
import com.chompfooddeliveryapp.exception.BadRequestException;
import com.chompfooddeliveryapp.model.meals.Images;
import com.chompfooddeliveryapp.repository.ImageRepository;
import com.chompfooddeliveryapp.service.serviceInterfaces.ImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final CloudinaryConfig cloudinaryConfig;

    public ImageServiceImpl(ImageRepository imageRepository, CloudinaryConfig cloudinaryConfig) {
        this.imageRepository = imageRepository;
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public Images saveImages(ImageDTO imageDTO) throws IOException {
            String imageURL = cloudinaryConfig.createImage(imageDTO.getImageName());


        Optional<Images> image = imageRepository.findImagesByImageURL(imageDTO.getImageName());

        if(image.isPresent()){
            throw new BadRequestException("Image already exists");
        }else{

            Images newImage = new Images(imageDTO.getImageName(), imageURL, imageDTO.getProduct());

            return imageRepository.save(newImage);
        }

    }
}
