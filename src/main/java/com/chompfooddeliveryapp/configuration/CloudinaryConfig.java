package com.chompfooddeliveryapp.configuration;

import com.chompfooddeliveryapp.exception.BadRequestException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary_api_key}")
    private String cloudinary_api_key;

    @Value("${cloudinary_api_secret}")
    private String cloudinary_api_secret;

    @Value("${cloud_name}")
    private String cloud_name;



    public String createImage(String imagename) throws IOException {


        Cloudinary cloudinary;
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", cloud_name);
        config.put("api_key", cloudinary_api_key);
        config.put("api_secret", cloudinary_api_secret);
        cloudinary = new Cloudinary(config);



        File file = new File("images/" + imagename);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        System.out.println(uploadResult.toString());
        System.out.println(uploadResult.get("url"));
        return uploadResult.get("url").toString();
    }
}
