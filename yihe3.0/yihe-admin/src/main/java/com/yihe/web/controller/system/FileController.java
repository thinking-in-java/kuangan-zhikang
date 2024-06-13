package com.yihe.web.controller.system;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;

import com.ibm.icu.util.Calendar;
import com.yihe.common.utils.*;
import com.yihe.system.domain.FileDO;
import com.yihe.system.service.FileService;
import com.yihe.system.utils.Result;
import com.yihe.web.controller.tool.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 文件上传
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-19 16:02:20
 */
@Controller
@RequestMapping("/sysFile")
public class FileController{
    
	static final int BUFFER_LENGTH = 10240;
	static final String START_FIELD = "start";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	static final String FILE_NAME_FIELD = "name";
	static final String FILE_SIZE_FIELD = "size";
	static final String TOKEN_FIELD = "token";
	static final String SERVER_FIELD = "server";
	static final String SUCCESS = "success";
	static final String MESSAGE = "message";
	static final boolean isCrossed = false;
	static final boolean isDeleteFinished=false;
	static final Pattern RANGE_PATTERN = Pattern.compile("bytes \\d+-\\d+/\\d+");
	static final String getCrossServer = "http://customers.duapp.com";
	static String dicName="UpFile";//服务器上传文件夹名称
	@Autowired
	private FileService sysFileService;

	@GetMapping()
	@RequiresPermissions("cmd:system:file")
	String sysFile(Model model) {
		Map<String, Object> params = new HashMap<>(16);
		return "file/file";
	}

	@ResponseBody
	@GetMapping("/list")

	public PageUtils list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<FileDO> sysFileList = sysFileService.list(query);
		int total = sysFileService.count(query);
		PageUtils pageUtils = new PageUtils(sysFileList, total);
		return pageUtils;
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")

	public Result info(@PathVariable("id") Long id) {
		FileDO sysFile = sysFileService.get(id);
		return Result.ok(sysFile);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")

	public Result save(FileDO sysFile) {
		if (sysFileService.save(sysFile) > 0) {
			return Result.ok();
		}
		return Result.error();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")

	public Result update(@RequestBody FileDO sysFile) {
		sysFileService.update(sysFile);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public Result remove(Long id, HttpServletRequest request) {

		String urlImg = sysFileService.get(id).getUrl();
		if (sysFileService.remove(id) > 0) {
			boolean b = deleteFile(urlImg);
			if (!b) {
				return Result.error("数据库记录删除成功，文件删除失败");
			}
			return Result.ok();
		} else {
			return Result.error();
		}
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	public Result remove(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids[]");
		Long[] num = new Long[ids.length];
		if(ids.length>0){
			for (int idx = 0; idx < ids.length; idx++) {
				    num[idx] = Long.parseLong(ids[idx]);
				    String urlImg = sysFileService.get(num[idx]).getUrl();
				    boolean b = deleteFile(urlImg);
		    }
			System.out.println(num);
			int batchRemove = sysFileService.batchRemove(num);
			if(batchRemove>0){
				return Result.ok();
			}
		}else{
			return Result.error("请选择要删除的文件");
		}
		return Result.error("删除失败");
	}
    
	/**
	 * @return 
	 * @Title: downloadFile  
	 * @Description: TODO(在浏览器端下载文件)  
	 * @param @param request
	 * @param @param response
	 * @param @param url
	 * @param @return
	 * @param @throws Exception    设定文件  
	 * @return ResponseEntity<Object>    返回类型  
	 * @throws
	 */
	@RequestMapping("/download")
	ResponseEntity<Object> downloadFile(HttpServletRequest request,HttpServletResponse response,String url) throws Exception{
	        //当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)
		    URL file = new URL (url);
		    URLConnection conn = file.openConnection();
            String fileName = new String(url.substring(url.lastIndexOf("/")+1,url.length()).getBytes("UTF-8"), "iso-8859-1");
            long fileTotalsize = conn.getContentLength();
            System.out.println("fileTotalsize = " + fileTotalsize);
            response.reset();
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
            response.addHeader("Content-Length", String.valueOf(fileTotalsize));
            ServletOutputStream sos = response.getOutputStream();
		    InputStream in = conn.getInputStream();
            BufferedOutputStream outputStream = new BufferedOutputStream(sos);
            byte[] b = new byte[1024 * 2];//每次传输2K
            long filecomplateSize = 0L;
            double rateSpeed = 0;//速度
            long rateProcess = 0L;//进度
            int readSize = 0;
            int i = 0;
            int nbytes = 0;
            long startTime = System.nanoTime();
            //这里是下载进度和速度的简单实现 没有使用单独的线程进行速度计算
            while ((readSize = in.read(b)) > 0) {
                if (i % 20 == 0) {
                    startTime = System.nanoTime();
                }
                filecomplateSize = filecomplateSize + readSize;
                outputStream.write(b, 0, readSize);
                nbytes = readSize + nbytes;
                if (i % 20 == 19) {
                    rateProcess = filecomplateSize / fileTotalsize;
                    //时间差  System.nanoTime()的返回值精确度是毫微秒
                    double currentTime = (System.nanoTime() - startTime);
                    //将速度转化成比较通用的MB/S
                    rateSpeed = ((nbytes / currentTime) * 1000 * 1000 * 1000) / 1024 / 1024;
                    String strrateSpeed = String.format("%.2f", rateSpeed);
                    rateSpeed = Double.valueOf(strrateSpeed);

                    nbytes = 0;
                }
                i++;
            }
            outputStream.flush();
            outputStream.close();
            sos.close();
		    in.close();
	        return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/upload", method = {RequestMethod.GET, RequestMethod.POST})
	void upload( HttpServletRequest request,HttpServletResponse resp,MultipartFile file) throws Exception {
		 boolean isGet = request.getMethod().toLowerCase().equals("get"); 
		 doOptions(request, resp);

		 final String token = request.getParameter(TOKEN_FIELD);
		 final String size = request.getParameter(FILE_SIZE_FIELD);
		 String fileName = request.getParameter(FILE_NAME_FIELD);
		 fileName = renameToUUID(fileName);
		 if(isGet){
			/** TODO: validate your token. */
		    PrintWriter writer = resp.getWriter();
			JSONObject json = new JSONObject();
			long start = 0;
			boolean success = true;
			String message = "";
			try {
				File f = saveFile(dicName,token);
				start = f.length();
				/** file size is 0 bytes. */
				if (token.endsWith("_0") && "0".equals(size) && 0 == start)
					f.renameTo(getFile(fileName,dicName));
			} finally {
				try {
					if (success)
					json.put(START_FIELD, start);
					json.put(SUCCESS, success);
					json.put(MESSAGE, message);
				} catch (Exception e) {}
				writer.write(json.toString());
				close(writer);
			}
	  		 
	  	 }else{
	  	    PrintWriter writer = resp.getWriter();
			Range range = parseRange(request);
			OutputStream out = null;
			InputStream content = null;
			/** TODO: validate your token. */
			JSONObject json = new JSONObject();
			long start = 0;
			boolean success = true;
			String message = "";
			File f = saveFile(dicName,token);
			try {
				if (f.length() != range.getFrom()) {
					/** drop this uploaded data */
					throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
				}
				out = new FileOutputStream(f, true);
				content = request.getInputStream();
				int read = 0;
				final byte[] bytes = new byte[BUFFER_LENGTH];
				while ((read = content.read(bytes)) != -1)
					out.write(bytes, 0, read);
				    start = f.length();
			} catch (StreamException se) {
				success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
				message = "Code: " + se.getCode();
			} catch (FileNotFoundException fne) {
				message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
				success = false;
			} catch (IOException io) {
				message = "IO Error: " + io.getMessage();
				success = false;
			} finally {
				close(out);
				close(content);
				if (range.getSize() == start) {
					/** fix the `renameTo` bug */
//					File dst = IoUtil.getFile(fileName);
//					dst.delete();
					// TODO: f.renameTo(dst); 重命名在Windows平台下可能会失败，stackoverflow建议使用下面这句
					try {
						// 先删除
						deleteFile(fileName);
						Files.move(f.toPath(), f.toPath().resolveSibling(fileName));
						System.out.println("TK: `" + token + "`, NE: `" + fileName + "`");
						/** if `STREAM_DELETE_FINISH`, then delete it. */
						if (isDeleteFinished) {
							deleteFile(fileName);
						}
					} catch (IOException e) {
						success = false;
						message = "Rename file error: " + e.getMessage();
					}
				}
				try {
					if (success)
				    json.put(START_FIELD, start);
					json.put(SUCCESS, success);
					json.put(MESSAGE, message);
					Calendar cale = null;
					cale = Calendar.getInstance();
					String year = String.valueOf(cale.get(Calendar.YEAR));
					String month = String.valueOf(cale.get(Calendar.MONTH) + 1);
					String saveFilePath = dicName+"/"+year+"/"+month+"/";
					String url2=request.getScheme()+"://"+ request.getServerName();//+request.getRequestURI();
					String imgPath1=url2+"/"+saveFilePath;
					FileDO sysFile = new FileDO(0,fileType(fileName), imgPath1 + fileName, new Date());
					sysFileService.save(sysFile);
				} catch (Exception e) {}

				writer.write(json.toString());
				close(writer);
			}

	  	 }


	}

	/**
	 * close the IO stream.
	 *
	 * @param stream
	 */
	public static void close(Closeable stream) {
		try {
			if (stream != null)
				stream.close();
		} catch (IOException e) {
		}
	}

	public static String renameToUUID(String fileName) {
		return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
	}



	public static File getFile(String filename,String dicName) throws IOException {
		if (filename == null || filename.isEmpty())
			return null;
		String pic_path;
		String tomcat_path = System.getProperty("catalina.home");
		System.out.println(tomcat_path);
		//获取Tomcat服务器所在路径的最后一个文件目录
		String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("/") + 1, tomcat_path.length());
		System.out.println(bin_path+"--------------获取Tomcat服务器所在路径的最后一个文件目录");
		//若最后一个文件目录为bin目录，则服务器为手动启动
		if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
			//获取保存上传图片的文件路径
			pic_path = tomcat_path.substring(0, System.getProperty("catalina.home").lastIndexOf("/")) + "/webapps/";
		} else {//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
			pic_path = tomcat_path + "/webapps/";
		}
		Calendar cale = null;
		cale = Calendar.getInstance();
		String year = String.valueOf(cale.get(Calendar.YEAR));
		String month = String.valueOf(cale.get(Calendar.MONTH) + 1);
		String saveFilePath = dicName+"/"+year+"/"+month+"/";
		/* 构建文件目录 */
		File f = new File(pic_path + saveFilePath+ File.separator + filename);
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		if (!f.exists())
			f.createNewFile();

		return f;
	}





	public static File saveFile(String dicName,String key) throws IOException {
		String pic_path;
		String tomcat_path = System.getProperty("catalina.home");
		System.out.println(tomcat_path);
		//获取Tomcat服务器所在路径的最后一个文件目录
		String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("/") + 1, tomcat_path.length());
		System.out.println(bin_path+"--------------获取Tomcat服务器所在路径的最后一个文件目录11");
		//若最后一个文件目录为bin目录，则服务器为手动启动
		if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
			//获取保存上传图片的文件路径
			pic_path = tomcat_path.substring(0, System.getProperty("catalina.home").lastIndexOf("/")) + "/webapps/";
		} else {//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
			pic_path = tomcat_path + "/webapps/";
		}
		Calendar cale = null;
		cale = Calendar.getInstance();
		String year = String.valueOf(cale.get(Calendar.YEAR));
		String month = String.valueOf(cale.get(Calendar.MONTH) + 1);
		String saveFilePath = dicName+"/"+year+"/"+month+"/";
		/* 构建文件目录 */
		File fileDir = new File(pic_path + saveFilePath+ File.separator + key);
		if (!fileDir.getParentFile().exists())
			fileDir.getParentFile().mkdirs();
		if (!fileDir.exists()) {
			fileDir.createNewFile();
		}
		return fileDir;
	}



	@RequestMapping("/tk")
	public void getToken(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String name = req.getParameter(FILE_NAME_FIELD);
		String size = req.getParameter(FILE_SIZE_FIELD);
		String token = generateToken(name, size);
		PrintWriter writer = resp.getWriter();
		JSONObject json = new JSONObject();
		try {
			json.put(TOKEN_FIELD, token);
			if (isCrossed)
				json.put(SERVER_FIELD, getCrossServer);
			json.put(SUCCESS, true);
			json.put(MESSAGE, "");
		} catch (Exception e) {
		}
		/** TODO: save the token. */

		writer.write(json.toString());
	}

	public static int fileType(String fileName) {
		if (fileName == null) {
			fileName = "文件名为空！";
			return 500;

		} else {
			// 获取文件后缀名并转化为写，用于后续比较
			String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
			// 创建图片类型数组0
			String[] img = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
					"cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
			for (int i = 0; i < img.length; i++) {
				if (img[i].equals(fileType)) {
					return 0;
				}
			}

			// 创建文档类型数组1
			String[] document = { "txt", "doc", "docx", "xls","xlsx", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt" };
			for (int i = 0; i < document.length; i++) {
				if (document[i].equals(fileType)) {
					return 1;
				}
			}
			// 创建视频类型数组2
			String[] video = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
			for (int i = 0; i < video.length; i++) {
				if (video[i].equals(fileType)) {
					return 2;
				}
			}
			// 创建音乐类型数组3
			String[] music = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
					"m4a", "vqf" };
			for (int i = 0; i < music.length; i++) {
				if (music[i].equals(fileType)) {
					return 3;
				}
			}

		}
		//4
		return 99;
	}


	public static boolean deleteFile(String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		String pic_path;
		String tomcat_path = System.getProperty("catalina.home");
		System.out.println(tomcat_path);
		//获取Tomcat服务器所在路径的最后一个文件目录
		String bin_path = tomcat_path.substring(tomcat_path.lastIndexOf("/") + 1, tomcat_path.length());
		//若最后一个文件目录为bin目录，则服务器为手动启动
		if (("bin").equals(bin_path)) {//手动启动Tomcat时获取路径为：D:\Software\Tomcat-8.5\bin
			//获取保存上传图片的文件路径
			pic_path = tomcat_path.substring(0, System.getProperty("catalina.home").lastIndexOf("/")) + "/webapps/";
		} else {//服务中自启动Tomcat时获取路径为：D:\Software\Tomcat-8.5
			pic_path = tomcat_path + "/webapps/";
		}
		Calendar cale = null;
		cale = Calendar.getInstance();
		String year = String.valueOf(cale.get(Calendar.YEAR));
		String month = String.valueOf(cale.get(Calendar.MONTH) + 1);
		String saveFilePath = dicName+"/"+year+"/"+month+"/";
		/* 构建文件目录 */
		File file = new File(pic_path +  saveFilePath + fileName);
		System.out.println(pic_path+ saveFilePath+ fileName+"--------------删除文件名");
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}



	/**
	 * 生成Token， A(hashcode>0)|B + |name的Hash值| +_+size的值
	 * @param name
	 * @param size
	 * @return
	 * @throws Exception
	 */
	public static String generateToken(String name, String size)
			throws IOException {
		if (name == null || size == null)
			return "";
		int code = name.hashCode();
		try {
			String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
			/** TODO: store your token, here just create a file */
			//创建文件
			//IoUtil.storeToken(token);

			return token;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}



	/**
	 * 获取Range参数
	 *
	 * @param req
	 * @return
	 * @throws IOException
	 */
	public static Range parseRange(HttpServletRequest req) throws IOException {
		String range = req.getHeader(FileController.CONTENT_RANGE_HEADER);
		Matcher m = RANGE_PATTERN.matcher(range);
		if (m.find()) {
			range = m.group().replace("bytes ", "");
			String[] rangeSize = range.split("/");
			String[] fromTo = rangeSize[0].split("-");

			long from = Long.parseLong(fromTo[0]);
			long to = Long.parseLong(fromTo[1]);
			long size = Long.parseLong(rangeSize[1]);

			return new Range(from, to, size);
		}
		throw new IOException("Illegal Access!");
	}


	protected void doOptions(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}
}
