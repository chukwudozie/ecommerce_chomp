package com.chompfooddeliveryapp.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    public String createImage(String imagename) throws IOException {
        Cloudinary cloudinary;
        Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "chomp-food-app");
        config.put("api_key", "957191478298491");
        config.put("api_secret", "XSgjbMDGMFmkPwgraPQUKfj2Ubs");
        cloudinary = new Cloudinary(config);



        File file = new File("images/" + imagename);
        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        System.out.println(uploadResult.toString());
        System.out.println(uploadResult.get("url"));
        return uploadResult.get("url").toString();
    }
}
