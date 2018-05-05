package com.code.filesystem.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by MaJian on 18/5/5.
 */
public class CommonUtil {

    public  static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    public  static  SimpleDateFormat sdfDate2 = new SimpleDateFormat("yyyy/MM/dd");
    public  static  SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public  static  SimpleDateFormat sdfDate_zn = new SimpleDateFormat("yyyy年MM月dd日");

    public static Map ErrorReturn(String msg){
        Map returnMap=new HashMap<>(2);
        returnMap.put("code",Enum.CodeType.Error.seq);
        returnMap.put("message",msg);
        return returnMap;
    }

    public static Map SuccessReturn(String msg, Object o){
        Map returnMap=new HashMap<>(4);
        returnMap.put("code",Enum.CodeType.Success.seq);
        returnMap.put("message",msg);
        returnMap.put("data",o);
        return returnMap;
    }

    public static String get32UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0]+idd[1]+idd[2]+idd[3]+idd[4];
    }

    public static String getAbsolutePhth(HttpServletRequest request){
        String path =request.getRealPath("/WEB-INF/classes/");
        return path;
    }

    /**
     * 获取文件后缀
     * @param file
     * @return
     */
    public static String getFileSuffix(MultipartFile file){
        String suffix=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        return suffix;
    }

    /**
     * //判断文件夹是否存在,如果不存在则创建文件夹
     * @param path
     * @return
     */
    public static String FileExist(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static int getExtType(String name){
        String PicExt="bmp,jpg,jpeg,png,tiff,gif,pcx,tga,exif,fpx,svg,psd,cdr,pcd,dxf,ufo,eps,ai,raw,WMF,webp";
        String VideoExt="rm,rmvb,mpeg,mp4,mov,mtv,dat,wmv,avi,3gp,amv,dmv,flv";
        String AudioExt="MP3,MWA,M4A,AAC,FLAC,MP4,AVI,FLV";
        String OfficeExt="TXT,DOC,DOCX,PPT,PPTX,XLS,XLSX,RT";
        if(PicExt.toLowerCase().contains(name.toLowerCase())){
            return 3;
        }else if(VideoExt.toLowerCase().contains(name.toLowerCase())){
            return 2;
        }else if(AudioExt.toLowerCase().contains(name.toLowerCase())){
            return 4;
        }else if(OfficeExt.toLowerCase().contains(name.toLowerCase())){
            return 1;
        }else{
            return 5;
        }

    }

    public static boolean GenerateImageBase64(String imgStr,String path)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            String imgFilePath = path;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static InputStream BaseToInputStream(String base64string){
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
        }
        return stream;
    }
    public static String getDirName(String type){
        String DirName="OtherFile";
        if("1".equals(type)){
            DirName="Offices";
        }else if("2".equals(type)){
            DirName="Videos";
        }else if("3".equals(type)){
            DirName="Pictures";
        }else if("4".equals(type)){
            DirName="Audios";
        }
        return DirName;
    }

    public static String getProjectBaseUrl(HttpServletRequest request){
        String ContextPath = request.getContextPath();
        if(ContextPath.length()>1&& ContextPath.substring(0,1).equals("/")){
            ContextPath = ContextPath.substring(1,ContextPath.length())+"/";
        }
        String basePath=getProjectBaseUrlNoProject(request)+ ContextPath  ;
        return basePath;
    }

    public static String getProjectBaseUrlNoProject(HttpServletRequest req){
        String scheme=req.getHeader("X-Forwarded-Scheme");
        if(scheme==null || scheme.equals("")){
            scheme=req.getScheme();
        }
        String port="";
        if(req.getServerPort()!=80 && req.getServerPort()!=443){
            port = ":"+req.getServerPort();
        }
        String basePath=req.getScheme() + "://"
                + req.getServerName() +port+ "/";
        return basePath;
    }

}
