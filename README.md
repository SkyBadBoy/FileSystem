# FileSystem(文件上传工具)
纯文件上传工具 支持本地和阿里云文件上传

> 使用技术:
>
> Spring Boot 

上传方式：
> 上传方式:
>
> Base64(仅支持图片)
> 
> File(支持所有文件)

请求方式只支持：POST


## Base64 上传方式
请求地址：http://127.0.0.1/FileSystem/{Project}/UploadBase64

{Project}:使用者的项目名，非空

post数据：key:data   value:{base64的图片地址}

由于base64数据字符较长，请使用form-data 方式提交

示例：

```sh
var base64="";//这边是你图片的信息
var formData = new FormData();
formData.append('data', base64);
$.ajax({
    url: 'http://127.0.0.1/FileSystem/ProjectName/UploadBase64',
    type: 'POST',
    data: formData,
    async: true,
    cache: false,
    contentType: false,
    processData: false,
    success: function (json) {
        console.log(json)
    },
    error: function (json) {
    }
});
```

## File 上传方式
请求地址：http://127.0.0.1/FileSystem/{Project}/Upload

{Project}:使用者的项目名，非空

post数据：key:file   value:file文件


## 返回值

给你个眼神慢慢体会

## 阿里云OSS配置
千万别用我的，容量比较少，想用我的☞我一声

以下修改为自己的参数

```sh
# OPEN 是否启动阿里云上传
ali.oss.Open = false
ali.oss.AccessKeyID =LTAI4GR0SnDhHOy6
ali.oss.AccessKeySecret =pVWCeStIebIjtq9atQw4VU0IYL50YV
ali.oss.Endpoint =oss-cn-hangzhou.aliyuncs.com
ali.oss.BucketName =majian
```

end ! ! !  bye bye my name is 马健




