package com.chompfooddeliveryapp.service.serviceInterfaces;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ImageService {

    String saveImages (String imageName) throws IOException;
}
