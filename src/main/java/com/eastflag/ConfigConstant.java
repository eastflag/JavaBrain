package com.eastflag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by eastflag on 2017-07-21.
 */
@Component
public class ConfigConstant {
    @Value("${backend.host}")
    public String backendHost;

    @Value("${front.host}")
    public String frontHost;

    @Value("${upload.root_folder}")
    public String uploadRootFolder;

    @Value("${news_image_folder}")
    public String news_image_folder;
}
