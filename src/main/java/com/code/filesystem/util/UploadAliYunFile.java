package com.code.filesystem.util;


import com.aliyun.oss.OSSClient;
import com.code.filesystem.dao.OSS;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

public class UploadAliYunFile {
	/**
	 * 文件上传
	 * @param inputStream
	 * @param
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static String UploadAliYunFileService(OSS oss,String DirName, String Project,String Prefix,InputStream inputStream) throws FileNotFoundException{
		 String path =  "";
		String fileNameUp=CommonUtil.get32UUID()+"."+Prefix;
		path=Project+"/"+DirName+"/"+CommonUtil.sdfDate.format(new Date())+"/"+fileNameUp;
		// endpoint以杭州为例，其它region请按实际情况填写
		String endpoint = "https://"+oss.getEndpoint();
		// accessKey请登录https://ak-console.aliyun.com/#/查看
		String accessKeyId = oss.getAccessKeyID();
		String accessKeySecret = oss.getAccessKeySecret();
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(oss.BucketName, path, inputStream);
		// 关闭client
		ossClient.shutdown();
		return "https://" +oss.BucketName+"."+ oss.Endpoint+"/"+path;
	}


//	/**
//	 * 文件上传
//	 * @param inputStream
//	 * @param
//	 * @return
//	 * @throws FileNotFoundException
//	 */
//	public static String UploadAliYunBase64Service(WeChatInfo weChatInfo, InputStream inputStream) throws FileNotFoundException{
//		String path =  "";
//		if(weChatInfo!=null) {
//			String DirName="Pictures";
//			final UUID uuid = UUID.randomUUID();
//			final String fileNameUp=uuid.randomUUID().toString()+".png";
//			path=DirName+"/"+SmBaseGlobal.sdfDate.format(new Date())+"/"+fileNameUp;
//			// endpoint以杭州为例，其它region请按实际情况填写
//			String endpoint = "https://"+weChatInfo.getAccessToken();
//			// accessKey请登录https://ak-console.aliyun.com/#/查看
//			String accessKeyId = weChatInfo.getAppID();
//			String accessKeySecret = weChatInfo.getAppsecret();
//			// 创建OSSClient实例
//			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//			ossClient.putObject(weChatInfo.getTicket(), path, inputStream);
//			// 关闭client
//			ossClient.shutdown();
//		}
//		//System.out.println("http://readbank.oss-cn-hangzhou.aliyuncs.com/"+path);
//		return "https://" +weChatInfo.getTicket()+"."+ weChatInfo.getAccessToken()+"/"+path;
//	}
}
