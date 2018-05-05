package com.code.filesystem.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by MaJian on 18/5/5.
 */
@Setter
@Getter
@Component
public class OSS {

    @Value("${ali.oss.Open}")
    public  boolean Open;

    @Value("${ali.oss.AccessKeyID}")
    public  String AccessKeyID;

    @Value("${ali.oss.AccessKeySecret}")
    public  String AccessKeySecret;

    @Value("${ali.oss.Endpoint}")
    public  String Endpoint;

    @Value("${ali.oss.BucketName}")
    public  String BucketName;
}
