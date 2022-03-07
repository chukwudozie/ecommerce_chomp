package com.chompfooddeliveryapp.service.serviceImpl;

import com.chompfooddeliveryapp.configuration.CloudinaryConfig;
import com.chompfooddeliveryapp.service.serviceInterfaces.ImageService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {


    private final CloudinaryConfig cloudinaryConfig;

    public ImageServiceImpl( CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public String saveImages(String imageName) throws IOException {
            String imageURL = cloudinaryConfig.createImage(imageName);
            return imageURL;
    }
}
