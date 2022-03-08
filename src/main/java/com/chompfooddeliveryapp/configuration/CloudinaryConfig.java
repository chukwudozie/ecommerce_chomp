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

    private final Logger log = LoggerFactory.getLogger(CloudinaryConfig.class);

    @Value("${cloudinary_api_key}")
    private String cloudinary_api_key;

    @Value("${cloudinary_api_secret}")
    private String cloudinary_api_secret;

    @Value("${cloud_name}")
    private String cloud_name;



    public String createImage(String imagename) throws IOException {

        log.info("This is the cloud name " + cloud_name);
        log.info("This is the cloudinary_api_key " + cloudinary_api_key);
        log.info("This is the cloud cloudinary_api_secret " + cloudinary_api_secret);

        Cloudinary cloudinary;
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", cloudinary_api_key);
        config.put("api_secret", cloudinary_api_secret);
        cloudinary = new Cloudinary(config);





        File file = new File( imagename);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        System.out.println(uploadResult.toString());
        System.out.println(uploadResult.get("url"));
        return uploadResult.get("url").toString();
    }
}
