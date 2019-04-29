package wsz.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wsz.redis.common.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @description: 文件管理
 * @author: WSZ
 * @create: 2019-04-29 09:12
 **/
@Slf4j
@Service
public class FileService {

    @Value(value = "${uploadFileUrl}")
    private String uploadFileUrl;

    /**
     * 上传文件
     * @param file
     * @param request
     * @return String 文件保存目录
     * @throws Exception
     */
    public String importFile(MultipartFile file, HttpServletRequest request) throws Exception{
        String dirsName = CommonUtils.getDateString();
        String realPath = uploadFileUrl + dirsName;
        File folder = new File(realPath);
        if(!folder.exists()){
            folder.mkdirs();
        }
        String fileName = file.getOriginalFilename();
        file.transferTo(new File(folder, fileName));
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/upload" + dirsName + fileName;
        log.info(realPath);
        return url;
    }
}