package com.code.filesystem.controller;

import com.code.filesystem.dao.File;
import com.code.filesystem.dao.OSS;
import com.code.filesystem.util.CommonUtil;
import com.code.filesystem.util.Enum;
import com.code.filesystem.util.UploadAliYunFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MaJian on 18/5/5.
 *
 * file 文件的上传  base64 文件的上传
 *
 */
@RestController
@RequestMapping("")
public class FileController {

    @Autowired
    private OSS oss;

    @ResponseBody
    @PostMapping(value = "/{Project}/Upload")
    public Map Upload(@PathVariable String Project,@RequestParam MultipartFile file,HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = new HashMap<>(2);
        if(!file.isEmpty()){
            String suffix=CommonUtil.getFileSuffix(file);
            String fileName = CommonUtil.get32UUID() +"."+ suffix;
            String type=String.valueOf(CommonUtil.getExtType(suffix));
            String DirName=CommonUtil.getDirName(type);
            if(oss.isOpen()){
                String url= UploadAliYunFile.UploadAliYunFileService(oss,DirName,Project,suffix,file.getInputStream());
                File f=new File();
                f.setUrl(url);
                f.setSize(file.getSize());
                f.setOSS(true);
                returnMap= CommonUtil.SuccessReturn("文件上传成功",f);
            }else{
                String Time=CommonUtil.sdfDate.format(new Date());
                String RealPath = CommonUtil.FileExist(CommonUtil.getAbsolutePhth(request) + "static/FileUpload/"+Project+"/"+DirName+"/"+Time+"/") + fileName;
                try {
                    InputStream stream = file.getInputStream();
                    OutputStream bos = new FileOutputStream(RealPath);
                    int bytesRead = 0;
                    byte[] buffer = new byte[10*1024];
                    while ( (bytesRead = stream.read(buffer, 0, 10240)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                    bos.close();
                    stream.close();
                    File f=new File();
                    f.setPath(RealPath);
                    //CommonUtil.getProjectBaseUrl(request)+
                    f.setUrl(CommonUtil.getProjectBaseUrl(request)+"/FileUpload/"+Project+"/"+DirName+"/"+Time+"/"+fileName);
                    f.setSize(file.getSize());
                    f.setOSS(false);
                    returnMap= CommonUtil.SuccessReturn("文件上传成功",f);
                }catch(Exception e){
                    e.printStackTrace();
                    returnMap= CommonUtil.ErrorReturn("存储异常请稍后重试");
                }
            }
        }else{
            returnMap= CommonUtil.ErrorReturn("请上传文件");
        }
        return returnMap;
    }

    /**
     * 只支持图片文件
     * @param Project
     * @param data
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping(value = "/{Project}/UploadBase64")
    public Map Upload(@PathVariable String Project,@RequestParam String data,HttpServletRequest request) throws Exception {
        Map<String, Object> returnMap = new HashMap<>(2);
        if(data!=null){
            String suffix="png";
            String fileName = CommonUtil.get32UUID() +"."+ suffix;
            String type=String.valueOf(CommonUtil.getExtType(suffix));
            String DirName=CommonUtil.getDirName(type);
            if(oss.isOpen()){
                String url= UploadAliYunFile.UploadAliYunFileService(oss,DirName,Project,suffix,CommonUtil.BaseToInputStream(data));
                File f=new File();
                f.setUrl(url);
                f.setOSS(true);
                returnMap= CommonUtil.SuccessReturn("文件上传成功",f);
            }else{
                String Time=CommonUtil.sdfDate.format(new Date());
                String RealPath = CommonUtil.FileExist(CommonUtil.getAbsolutePhth(request) + "static/FileUpload/"+Project+"/"+DirName+"/"+Time+"/") + fileName;
                boolean isBase = CommonUtil.GenerateImageBase64(data.split(",")[1], RealPath);
                if(isBase){
                    File f=new File();

                    f.setUrl(CommonUtil.getProjectBaseUrl(request)+"/FileUpload/"+Project+"/"+DirName+"/"+Time+"/"+fileName);
                    f.setOSS(true);
                    returnMap= CommonUtil.SuccessReturn("文件上传成功",f);
                }else{
                    returnMap= CommonUtil.ErrorReturn("存储异常请稍后重试");
                }
            }
        }else{
            returnMap= CommonUtil.ErrorReturn("请上传文件");
        }
        return returnMap;
    }

}
