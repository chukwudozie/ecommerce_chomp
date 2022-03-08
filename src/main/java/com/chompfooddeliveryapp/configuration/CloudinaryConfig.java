package com.chompfooddeliveryapp.configuration;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

//    private final Logger log = LoggerFactory.getLogger(CloudinaryConfig.class);
//
//    @Value("${cloudinary_api_key}")
//    private String cloudinary_api_key;
//
//    @Value("${cloudinary_api_secret}")
//    private String cloudinary_api_secret;
//
//    @Value("${cloud_name}")
//    private String cloud_name;


    public String createImage(String imagename) throws IOException {

        Map uploadResult;

        Cloudinary cloudinary;
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "chomp-food-app");
        config.put("api_key", "957191478298491");
        config.put("api_secret", "XSgjbMDGMFmkPwgraPQUKfj2Ubs");
        cloudinary = new Cloudinary(config);
        try {
            File file = new File(imagename);
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            System.out.println(uploadResult.toString());
            System.out.println(uploadResult.get("url"));
        } catch (Exception exception) {
            throw new BadRequestException("Something went wrong: " + exception.getMessage());
        }
        return uploadResult.get("url").toString();
    }

}
