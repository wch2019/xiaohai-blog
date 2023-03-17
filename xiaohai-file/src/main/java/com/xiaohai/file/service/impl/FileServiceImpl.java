package com.xiaohai.file.service.impl;

import com.xiaohai.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangchenghai
 * @date 2023/03/16 15:01:24
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file,Integer type) {
//        //文件地址初始化
//        String filePath = "";
//        //首先判断不是空的文件
//        if (file != null && StringUtils.isNotBlank(file.getOriginalFilename())) {
//            //获取文件全名
//            file.getOriginalFilename();
//            //对文文件的全名进行截取然后在后缀名进行删选。
//            int begin = Objects.requireNonNull(file.getOriginalFilename()).indexOf(".");
//            int last = file.getOriginalFilename().length();
//            //获得文件后缀名
//            String a = file.getOriginalFilename().substring(begin, last);
//            //我这边需要的jpg文件所以说我这边直接判断就是了
//            if (a.endsWith(".jpg") || a.endsWith(".png")) {
//                //为了不重复，时间戳作为图片名称
//                String fileNameString = System.currentTimeMillis() + a;
//                File savedFile = new File(uploadPath, fileNameString);
//                try {
//                    filePath = savedFile.getPath().replace(System.getProperty("user.dir"),"");
//                    System.out.println("保存图片--------->" + filePath);
//                    FileUtils.copyInputStreamToFile(file.getInputStream(), savedFile);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                return JsonBean.returnResponse(false, ResultCode.SERVICE_ERR, "文件类型不对");
//            }
//            return JsonBean.returnResponse(filePath.replaceAll("\\\\","/"));
//        }
//        return JsonBean.returnResponse(false, ResultCode.SERVICE_ERR, "文件为空");
        return null;
    }
}
