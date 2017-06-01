package librarySystem.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadUtil {
    public static String dealUpload(MultipartFile file, HttpServletRequest request) throws IOException {
        String filePath = request.getServletContext().getRealPath("/uploads");
        String fileName = file.getOriginalFilename();
        if (fileName.lastIndexOf("\\") != -1) {
            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        InputStream in = file.getInputStream();
        String noExtFileName = fileName.substring(0, fileName.lastIndexOf("."));
        noExtFileName = noExtFileName.replace(".", "")//去除文件名中的逗号
                .replace("-", "")//去除减号
                .replace(" ", "")
                .replace("_", "");
        String extName = fileName.substring(fileName.lastIndexOf("."));
        fileName = noExtFileName + System.currentTimeMillis() + extName;
        FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.close();
        return fileName;
    }
}
