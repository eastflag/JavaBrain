package com.eastflag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by eastflag on 2017-07-21.
 */
@Component
public class ConfigConstant {
    @Value("${naver.successUrl}")
    public String naverSuccessUrl;
}
