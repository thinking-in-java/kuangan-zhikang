package com.yihe.system.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.javascript.host.Set;

import sun.misc.BASE64Encoder;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 
 * @author yx
 *
 */
public class FileUtils {
	
	private static final String UPLOADDIR = "uploadDir/";  
	
	public static final String NOPICPATH = "/resources/images/nopic_97d6179f.png";

	public static void copyFlie(File srcFile, String dstFilename) {
		FileChannel srcChannel = null;
		FileChannel dstChannel = null;
		try {
			srcChannel = (new FileInputStream(srcFile)).getChannel();
			dstChannel = (new FileOutputStream(dstFilename)).getChannel();
			dstChannel.transferFrom(srcChannel, 0L, srcChannel.size());
			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
			// log.error( "copy [ " + srcFilename + "] to [ " + dstFilename +
			// "] except: " + e);
		} finally {
			if (null != srcChannel) {
				try {
					srcChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != dstChannel) {
				try {
					dstChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * // 取得文件大小
	 * 
	 * @param f
	 * @return
	 * @throws Exception
	 */
	public static float getFileSizeByK(File f) throws Exception {
		float s = 0F;
		if (f.exists()) {
			Long si = new Long((new FileInputStream(f)).available());
			s = (float) (Math.round((float) (si.floatValue() / 1000.0) * 100)) / 100;
		} else {
			f.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	public static Float getFileSizeByM(File f) throws Exception {
		Float s = 0F;
		if (f.exists()) {
			Long si = new Long((new FileInputStream(f)).available());
			if (si < 10000) {
				s = 0.01F;
			} else {
				s = (float) (Math
						.round((float) (si.floatValue() / 1000000.0) * 100)) / 100;
			}

		} else {
			f.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	public static String readTextFile(String textPath) {
		String line = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader(textPath));
			try {
				while (null != in.readLine()) {
					line += in.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return line;
	}

	public static String readTextFileByEndcoding(String textPath) {
		return readTextFileByEndcoding(textPath, "GB2312");
	}

	public static String readTextFileByEndcoding(String textPath,
			String encoding) {
		String lineTXT = "";
		if (null == encoding || "".equals(encoding.trim())) {
			encoding = "GBK"; // 字符编码(可解决中文乱码问题 )
		}
		try {
			File file = new File(textPath);
			if (file.isFile() && file.exists()) {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String tmp = bufferedReader.readLine();
				while (null != tmp) {
					lineTXT += tmp;
					tmp = bufferedReader.readLine();
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件！");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容操作出错");
			e.printStackTrace();
		}
		return lineTXT;
	}

	/**
	 * 删除空文件夹
	 * @param dir 文件夹路径
	 */
	public static void doDeleteEmptyDir(String dir) {

		boolean success = (new File(dir)).delete();

		if (success) {
			System.out.println("Successfully deleted empty directory: " + dir);
		} else {
			System.out.println("Failed to delete empty directory: " + dir);
		}

	}


	/**
	 * 递归删除非空文件夹
	 * @param dir 文件夹对象
	 * @return
	 */
	public  static boolean deleteDir(File dir) {

		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
	
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}
	
	/**
	 * 计算图片尺寸大小等信息：
	 * width:图片宽度（像素）
	 * height:图片高度（像素）
	 * size:图片大小（KB）
	 * rate:宽高比（Double小数）
	 * 
	 * @param imgpath
	 *            图片路径
	 * @return 图片信息map
	 */
	public static Map<String,Double> getImgInfo(File imageFile ) {
		Map<String, Double> map = new HashMap<String, Double>(3);
		try {
			FileInputStream fis = new FileInputStream(imageFile);
			BufferedImage buff = ImageIO.read(imageFile);
			map.put("width", buff.getWidth() * 1.0);
			map.put("height", buff.getHeight() * 1.0);
			map.put("size",(double)imageFile.length()/1024);
			if(buff.getHeight()!=0){				
				map.put("rate",(double)buff.getWidth()/buff.getHeight());
			}else{
				map.put("rate",0.0);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			System.err.println("所给的图片文件" + imageFile.getPath()
					+ "不存在！计算图片尺寸大小信息失败！");
			map = null;
		} catch (IOException e) {
			System.err.println("计算图片" + imageFile.getPath() + "尺寸大小信息失败！");
			map = null;
		}
		return map;
	}
	
	  /** 
	     * 下载 
	     * @param request 
	     * @param response 
	     * @param storeName 
	     * @param contentType 
	     * @param realName 
	     * @throws Exception 
	     */  
	 public static void download(HttpServletRequest request,  
	            HttpServletResponse response, String downLoadPath, String contentType,  
	            String realName) throws Exception {  
	        response.setContentType("text/html;charset=UTF-8");  
	        request.setCharacterEncoding("UTF-8");  
	        BufferedInputStream bis = null;  
	        BufferedOutputStream bos = null;  
	  
	        long fileLength = new File(downLoadPath).length();  
	        response.setContentType(contentType);  
	        response.setHeader("Content-disposition", "attachment; filename="  
	                + new String(realName.getBytes("gbk"), "ISO8859-1"));  
	        response.setHeader("Content-Length", String.valueOf(fileLength));  
	        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
	        bos = new BufferedOutputStream(response.getOutputStream());  
	        byte[] buff = new byte[2048];  
	        int bytesRead;  
	        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
	            bos.write(buff, 0, bytesRead);  
	        }  
	        bis.close();  
	        bos.close();  
	    }
	 
	 /**
		 * @方法名: download
		 * @描述:通用下载
		 * @作者:yx
		 * @时间:2014-5-7 上午10:07:14
		 * @参数:@param request
		 * @参数:@param response
		 * @参数:@param savePath
		 * @参数:@param realName
		 * @参数:@throws Exception
		 * @返回值：void
		 */
		public static void downloadFile(HttpServletRequest request,
				HttpServletResponse response, String savePath, String realName) {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				response.setContentType("text/html;charset=UTF-8");
				request.setCharacterEncoding("UTF-8");
				String downloadPath = request.getSession().getServletContext()
						.getRealPath(savePath);
				long fileLength = new File(downloadPath).length();

				response.setContentType("application/octet-stream");
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String(realName.getBytes("GBK"), "ISO8859-1"));
				response.setHeader("Content-Length", String.valueOf(fileLength));

				bis = new BufferedInputStream(new FileInputStream(downloadPath));
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				//e.printStackTrace();//如果取消下载，这里会捕捉到异常
			}finally{			
				try {
					bos.close();
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/** 
		* @方法名: downloadFile
		* @描述:通过文件输入流下载文件，用于文件导出
		* @作者:阳雄
		* @时间:2020-5-29 下午5:10:22
		* @参数:@param request
		* @参数:@param response
		* @参数:@param is
		* @参数:@param realName 
		* @返回值：void
		* 参数要对上
		*/
		public static void downloadFile(HttpServletRequest request,
				HttpServletResponse response,InputStream is,String contentType,String realName) {
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				//response.setContentType("text/html;charset=UTF-8");
				request.setCharacterEncoding("UTF-8");
				long fileLength = is.available();
				
				response.setContentType(contentType);
					realName=new String(realName.getBytes("GBK"), "ISO8859-1");
				response.setHeader("Content-disposition", "attachment; filename="
						+realName);
				response.setHeader("Content-Length", String.valueOf(fileLength));
				bis = new BufferedInputStream(is);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
			} catch (Exception e) {
				//e.printStackTrace();//如果取消下载，这里会捕捉到异常
			}finally{			
				try {
					bos.close();
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	 
	 
	 /**
	  * 宽度65  高度65
	  */
	 public static String RESIZETYPE_W65_H65 = "_65";
	 /**
	  * 宽度140 高度
	  */
	 public static String RESIZETYPE_W140_H105 = "_140";
	 /**
	  * 宽度80 高度60
	  */
	 public static String RESIZETYPE_W200_H150 ="_200";
	 /**
	  * 最小图片
	  */
	 public static String RESIZETYPE_300_225 = "_s";
	 /**
	  * 缩略图
	  */
	 public static String RESIZETYPE_TAILOR="_tailor";
	
		/**
		 * 将图片裁剪区域保存为新图片
		 * 
		 * @param img
		 *            图片源路径
		 * @param dest
		 *            裁剪后图片的保存路径
		 * @param x1
		 *            裁剪起点的x坐标
		 * @param y1
		 *            裁剪起点的y坐标
		 * @param width
		 *            裁剪区域宽度
		 * @param height
		 *            裁剪区域高度
		 * @return
		 * @throws IOException
		 */
		public static boolean saveImage(File img, File dest, int x1, int y1,
				int width, int height) throws IOException {
			if (!dest.getParentFile().exists())
				dest.getParentFile().mkdirs();
			String ext = dest.getName().substring(dest.getName().lastIndexOf(".") + 1);
			BufferedImage bi = (BufferedImage) ImageIO.read(img);
			height = Math.min(height, bi.getHeight());
			width = Math.min(width, bi.getWidth());
			if (height <= 0)
				height = bi.getHeight();
			if (width <= 0)
				width = bi.getWidth();
			y1 = Math.min(Math.max(0, y1), bi.getHeight() - height);
			x1 = Math.min(Math.max(0, x1), bi.getWidth() - width);

			BufferedImage bi_cropper = bi.getSubimage(x1, y1, width, height);
			return ImageIO.write(bi_cropper,ext,dest);
		}	 
		/**
		 * 下载文件时，针对不同浏览器，进行附件名的编码
		 * 
		 * @param filename
		 *            下载文件名
		 * @param agent
		 *            客户端浏览器
		 * @return 编码后的下载附件名
		 * @throws IOException
		 */
		public static String encodeDownloadFilename(String filename, String agent)
				throws IOException {
			if (agent.contains("Firefox")) { // 火狐浏览器
				filename = "=?UTF-8?B?"
						+ new BASE64Encoder().encode(filename.getBytes("utf-8"))
						+ "?=";
				filename = filename.replaceAll("\r\n", "");
			} else { // IE及其他浏览器
				filename = URLEncoder.encode(filename, "utf-8");
				filename = filename.replace("+"," ");
			}
			return filename;
		}


}
