package com.xiaohai.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.utils.DateUtils;
import com.xiaohai.common.utils.FileUtils;
import com.xiaohai.common.utils.StringUtils;
import com.xiaohai.file.dao.FileManagerMapper;
import com.xiaohai.file.pojo.dto.FileDto;
import com.xiaohai.file.pojo.dto.FileManagerDto;
import com.xiaohai.file.pojo.dto.FileMarkdownDto;
import com.xiaohai.file.pojo.entity.FileManager;
import com.xiaohai.file.pojo.vo.FileManagerVo;
import com.xiaohai.file.pojo.vo.UploadVo;
import com.xiaohai.file.service.FileManagerService;
import com.xiaohai.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import static com.xiaohai.common.constant.Constants.SHA_256;

/**
 * @author wangchenghai
 * @date 2023/03/16 15:01:24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileConfig fileConfig;

    private final FileManagerService fileManagerService;

    private final FileManagerMapper fileManagerMapper;

    @Override
    public String uploadAvatar(MultipartFile file) {
        //根据用户区分文件夹
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.AVATAR_FILE;
        //计算hash
        String hash = FileUtils.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtils.isNotBlank(url)) {
            return url;
        }
        return addFileImage(path, file, hash);
    }

    @Override
    public String uploadLogo(MultipartFile file) {
        String path = fileConfig.getProfile() + FileConstants.SYSTEM_FILE;
        //计算hash
        String hash = FileUtils.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtils.isNotBlank(url)) {
            return url;
        }
        return addLogo(path, file, hash);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        //指定markdown图片上传目录,根据用户区分文件夹
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE;
        //计算hash
        String hash = FileUtils.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtils.isNotBlank(url)) {
            return url;
        }
        return addFileImage(path, file, hash);
    }

    @Override
    public Integer deleteImage(String pathName) {
        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE + File.separator + pathName;
        boolean isTrue = FileUtils.deleteFile(path);
        Assert.isTrue(isTrue, "当前图片:" + pathName + ",删除失败");
        path = path.replace(fileConfig.getProfile(), File.separator);
        return fileManagerService.deletePath(path);
    }

    @Override
    public String uploadBing(MultipartFile file, String path, String fileName) {
        //计算hash
        String hash = FileUtils.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtils.isNotBlank(url)) {
            return url;
        }
        return addFile(path, file, fileName, hash);
    }

    /**
     * 添加图片文件
     *
     * @param path 文件保存路径
     * @param file 要添加的文件
     * @param hash hash
     * @return 添加的文件路径
     */
    public String addFileImage(String path, MultipartFile file, String hash) {
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // 获取文件后缀名
        String fileExtension = FileUtils.getFileExtension(originalFilename);

        // 判断文件类型是否为图片
        if (!FileUtils.isImageExtension(fileExtension)) {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(FileConstants.IMAGE_EXTENSION));
        }

        // 生成唯一的文件名
        String fileName = FileUtils.generateUniqueFileName(fileExtension);

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, fileName, file);
        filePath = filePath.replace(fileConfig.getProfile(), File.separator);
        log.info("保存图片--------->{}", filePath);
        FileManagerVo fileManagerVo = new FileManagerVo();
        //查询父类
        FileManager manager = fileManagerService.findByPath(FileUtils.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
        fileManagerVo.setParentId(manager.getId());
        fileManagerVo.setFilePath(FileUtils.normalizeFilePath(filePath));
        fileManagerVo.setFileName(fileName);
        fileManagerVo.setFileSize((int) file.getSize());
        fileManagerVo.setFileHash(hash);
        fileManagerVo.setFileType(0);
        fileManagerService.add(fileManagerVo);
        return fileManagerVo.getFilePath();
    }

    /**
     * 添加文件
     *
     * @param path     文件保存路径
     * @param file     要添加的文件
     * @param fileName 自定义文件名称
     * @param hash     hash
     * @return
     */
    public String addFile(String path, MultipartFile file, String fileName, String hash) {
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // 获取文件后缀名
        String fileExtension = FileUtils.getFileExtension(originalFilename);

        //没有传入
        if (org.apache.commons.lang3.StringUtils.isBlank(fileName)) {
            // 生成唯一的文件名
            fileName = FileUtils.generateUniqueFileName(fileExtension);
        }

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, fileName, file);
        filePath = filePath.replace(fileConfig.getProfile(), File.separator);
        log.info("保存文件--------->{}", filePath);
        FileManagerVo fileManagerVo = new FileManagerVo();
        //查询父类
        FileManager manager = fileManagerService.findByPath(FileUtils.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
        fileManagerVo.setParentId(manager.getId());
        fileManagerVo.setFilePath(FileUtils.normalizeFilePath(filePath));
        fileManagerVo.setFileName(fileName);
        fileManagerVo.setFileSize((int) file.getSize());
        fileManagerVo.setFileHash(hash);
        fileManagerVo.setFileType(0);
        fileManagerService.add(fileManagerVo);
        return fileManagerVo.getFilePath();
    }

    /**
     * 添加Logo
     *
     * @param path 文件保存路径
     * @param file 要添加的文件
     * @param hash hash
     * @return 添加的文件路径
     */
    public String addLogo(String path, MultipartFile file, String hash) {
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        // 获取文件后缀名
        String fileExtension = FileUtils.getFileExtension(originalFilename);

        // 判断文件类型是否为图片
        if (!FileUtils.isImageExtension(fileExtension)) {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(FileConstants.IMAGE_EXTENSION));
        }

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, FileConstants.LOGO, file);
        filePath = filePath.replace(fileConfig.getProfile(), File.separator );
        log.info("保存图片--------->{}", filePath);
        FileManagerVo fileManagerVo = new FileManagerVo();
        FileManager fileManager = fileManagerService.findByPath(FileUtils.normalizeFilePath(filePath));
        if (fileManager == null) {
            //查询父类
            FileManager manager = fileManagerService.findByPath(FileUtils.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
            fileManagerVo.setParentId(manager.getId());
            fileManagerVo.setFilePath(FileUtils.normalizeFilePath(filePath));
            fileManagerVo.setFileName(FileConstants.LOGO);
            fileManagerVo.setFileSize((int) file.getSize());
            fileManagerVo.setFileHash(hash);
            fileManagerVo.setFileType(0);
            fileManagerService.add(fileManagerVo);
        } else {
            fileManagerVo.setId(fileManager.getId());
            fileManagerVo.setFileSize((int) file.getSize());
            fileManagerVo.setFileHash(hash);
            fileManagerService.updateData(fileManagerVo);
        }
        return fileManagerVo.getFilePath();
    }

    @Override
    public String upload(UploadVo vo) {
        MultipartFile file = vo.getFile();
        String path = fileConfig.getFilePath() + vo.getPath();
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            throw new ServiceException("文件为空");
        }

        // 获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        //文件夹以年份/月份/分割
        //        String folder = LocalDateTime.now().getYear() + "/" + LocalDateTime.now().getMonth() + "/";

        // 保存文件并返回文件路径
        String filePath = FileUtils.saveFile(path, originalFilename, file);

        filePath = File.separator + filePath.replace(fileConfig.getProfile(), "");
        log.info("保存图片--------->{}", filePath);
        //前端展示需要处理
        return filePath.replace("\\", "/");
    }

    @Override
    public List<FileDto> getPathList(String path) {
        List<FileDto> list = new ArrayList<>();
        // 指定文件夹路径
        String folderPath = fileConfig.getProfile() + path;
        File folder = new File(folderPath);
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                FileDto fileDto = new FileDto();
                fileDto.setNameSuffix("");
                fileDto.setPath(File.separator + file.getPath().replace(fileConfig.getProfile(), ""));
                fileDto.setPath(fileDto.getPath().replace("\\", "/"));
                if (!file.isDirectory()) {
                    fileDto.setUpdateTime(DateUtils.millisToDateTime(file.lastModified()));
                    fileDto.setSize(FileUtils.formatFileSize(file.length()));
                    fileDto.setNameSuffix(FileUtils.getFileExtension(file.getName()));
                }
                fileDto.setCreateTime(FileUtils.fileCreationTime(file.getPath()));
                fileDto.setName(file.getName());
                list.add(fileDto);
            }
        }

        return list.stream().sorted(Comparator.comparing(FileDto::getNameSuffix)).toList();
    }

    @Override
    public ReturnPageData<FileManagerDto> getMarkdownImageListByPage() {
        // 指定文件夹路径
        String folderPath = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE;
        FileManager fileManager=fileManagerService.findByPath(FileUtils.normalizeFilePath(folderPath.replace(fileConfig.getProfile(), File.separator)));
        if(fileManager==null){
            return new ReturnPageData<>();
        }
        return  fileManagerService.getParentIdPath(fileManager.getId());
//        File folder = new File(folderPath);
//        if (folder.isDirectory()) {
//            File[] files = folder.listFiles();
//            assert files != null;
//            for (File file : files) {
//                if (!file.isDirectory()) {
//                    //                    RcAttachmentInfo attachmentInfo=new RcAttachmentInfo();
//                    //                    FileUtils.imageProperty(file.getPath(),attachmentInfo);
//                    FileMarkdownDto fileDto = new FileMarkdownDto();
//                    fileDto.setPath("");
//                    fileDto.setUpdateTime(DateUtils.millisToDateTime(file.lastModified()));
//                    fileDto.setSize(FileUtils.formatFileSize(file.length()));
//                    //                    fileDto.setImageSize(attachmentInfo.getWidth()+"x"+attachmentInfo.getHeight());
//                    fileDto.setNameSuffix(FileUtils.getFileExtension(file.getName()));
//                    fileDto.setCreateTime(FileUtils.fileCreationTime(file.getPath()));
//                    fileDto.setName(file.getName());
//                    list.add(fileDto);
//                }
//            }
//        }
//
//        return list.stream().sorted(Comparator.comparing(FileMarkdownDto::getCreateTime).reversed()).toList();
    }

    @Override
    public Integer deletePath(String path) {
        String pathFile = FileUtils.systemFilePath(fileConfig.getProfile() + path);
        boolean isTrue = FileUtils.deleteFile(pathFile);
        Assert.isTrue(isTrue, "当前路径:" + path + ",删除失败");
        return fileManagerService.deletePath(path);
    }

    /**
     * 判断当前文件hash是否存在，存在返回路径，不存在创建文件路径
     *
     * @param path
     * @param hash
     * @return
     */
    public String getFile(String path, String hash) {
        //获取相同的文件信息
        FileManager fileManager = fileManagerService.findByHash(hash);
        if (fileManager != null) {
            return fileManager.getFilePath();
        }
        //判断当前文件夹是否存在，不存在就创建当前文件夹
        path = path.replace(fileConfig.getProfile(), "");
        // 使用 File.separator 进行分割
        String[] listPatch = path.split(Pattern.quote(File.separator));
        //父文件
        StringBuilder parentPath = new StringBuilder();
        //子文件
        StringBuilder parent = new StringBuilder();
        for (String patch : listPatch) {
            parent.append(File.separator).append(patch);
            FileManager manager = fileManagerService.findByPath(FileUtils.normalizeFilePath(parent.toString()));
            if (manager == null) {
                FileManagerVo fileManagerVo = new FileManagerVo();
                //当前文件夹不存在，查询有没有父类文件夹
                if (StringUtils.isNotBlank(parentPath.toString())) {
                    manager = fileManagerService.findByPath(FileUtils.normalizeFilePath(parentPath.toString()));
                    fileManagerVo.setParentId(manager.getId());
                }
                fileManagerVo.setFilePath(FileUtils.normalizeFilePath(parent.toString()));
                fileManagerVo.setFileName(patch);
                fileManagerVo.setFileType(1);
                fileManagerService.add(fileManagerVo);
            }
            parentPath.append(File.separator).append(patch);
        }
        return null;
    }

}
