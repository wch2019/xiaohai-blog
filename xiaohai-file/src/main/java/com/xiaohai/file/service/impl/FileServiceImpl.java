package com.xiaohai.file.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaohai.common.confing.FileConfig;
import com.xiaohai.common.constant.Constants;
import com.xiaohai.common.constant.FileConstants;
import com.xiaohai.common.daomain.ReturnPageData;
import com.xiaohai.common.exception.ServiceException;
import com.xiaohai.common.server.Disk;
import com.xiaohai.common.utils.FileUtil;
import com.xiaohai.common.utils.MarkdownUtils;
import com.xiaohai.common.utils.StringUtil;
import com.xiaohai.file.pojo.dto.FileManagerDto;
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
import java.util.Arrays;
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

    @Override
    public String uploadAvatar(MultipartFile file) {
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        //根据用户区分文件夹
        String path = fileConfig.getFilePath() + userId + File.separator + FileConstants.AVATAR_FILE;
        //计算hash
        String hash = FileUtil.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtil.isNotBlank(url)) {
            return url;
        }
        Disk disk = fileManagerService.getUserHardDiskSize(userId);
        if (disk.getFreeNoUnit() < file.getSize()) {
            throw new ServiceException("容量不足");
        }
        return addFileImage(path, file, hash);
    }

    @Override
    public String uploadLogo(MultipartFile file) {
        String path = fileConfig.getProfile() + FileConstants.SYSTEM_FILE;
        //计算hash
        String hash = FileUtil.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtil.isNotBlank(url)) {
            return url;
        }
        return addLogo(path, file, hash);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        if (file.getSize() / 1024 / 1024 > 2) {
            throw new ServiceException("只能上传图片大小小于2M");
        }
        Integer userId = Integer.valueOf((String) StpUtil.getLoginId());
        //指定markdown图片上传目录,根据用户区分文件夹
        String path = fileConfig.getFilePath() + userId + File.separator + FileConstants.MARKDOWN_FILE;
        //计算hash
        String hash = FileUtil.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtil.isNotBlank(url)) {
            return url;
        }
        Disk disk = fileManagerService.getUserHardDiskSize(userId);
        if (disk.getFreeNoUnit() < file.getSize()) {
            throw new ServiceException("容量不足");
        }
        return addFileImage(path, file, hash);
    }

    //    @Override
    //    public Integer deleteImage(String pathName) {
    //        String path = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE + File.separator + pathName;
    //        boolean isTrue = FileUtils.deleteFile(path);
    //        Assert.isTrue(isTrue, "当前图片:" + pathName + ",删除失败");
    //        path = path.replace(fileConfig.getProfile(), File.separator);
    //        return fileManagerService.deletePath(path);
    //    }

    @Override
    public String uploadBing(MultipartFile file, String path, String fileName) {
        //计算hash
        String hash = FileUtil.extractChecksum(file, SHA_256);
        //验证是否存在当前文件
        String url = getFile(path, hash);
        if (StringUtil.isNotBlank(url)) {
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
        String fileExtension = FileUtil.getFileExtension(originalFilename);

        // 判断文件类型是否为图片
        if (!FileUtil.isImageExtension(fileExtension)) {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(FileConstants.IMAGE_EXTENSION));
        }

        // 生成唯一的文件名
        //        String fileName = FileUtils.generateUniqueFileName(fileExtension);
        //验证当前目录文件名是否唯一
        String fileName = FileUtil.getUniqueFileName(path, originalFilename);

        // 保存文件并返回文件路径
        String filePath = FileUtil.saveFile(path, fileName, file);
        filePath = filePath.replace(fileConfig.getProfile(), File.separator);
        log.info("保存图片--------->{}", filePath);
        FileManagerVo fileManagerVo = new FileManagerVo();
        //查询父类
        FileManager manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
        fileManagerVo.setParentId(manager.getId());
        fileManagerVo.setFilePath(FileUtil.normalizeFilePath(filePath));
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
        String fileExtension = FileUtil.getFileExtension(originalFilename);

        //没有传入
        if (org.apache.commons.lang3.StringUtils.isBlank(fileName)) {
            // 生成唯一的文件名
            fileName = FileUtil.generateUniqueFileName(fileExtension);
        } else {
            //验证当前目录文件名是否唯一
            fileName = FileUtil.getUniqueFileName(path, originalFilename);
        }

        // 保存文件并返回文件路径
        String filePath = FileUtil.saveFile(path, fileName, file);
        filePath = filePath.replace(fileConfig.getProfile(), File.separator);
        log.info("保存文件--------->{}", filePath);
        FileManagerVo fileManagerVo = new FileManagerVo();
        //查询父类
        FileManager manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
        if (manager != null) {
            fileManagerVo.setParentId(manager.getId());
        }
        fileManagerVo.setFilePath(FileUtil.normalizeFilePath(filePath));
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
        String fileExtension = FileUtil.getFileExtension(originalFilename);

        // 判断文件类型是否为图片
        if (!FileUtil.isImageExtension(fileExtension)) {
            throw new ServiceException("请查看图片类型是否正确" + Arrays.toString(FileConstants.IMAGE_EXTENSION));
        }

        // 保存文件并返回文件路径
        String filePath = FileUtil.saveFile(path, FileConstants.LOGO, file);
        filePath = filePath.replace(fileConfig.getProfile(), File.separator);
        log.info("保存图片--------->{}", filePath);
        FileManagerVo fileManagerVo = new FileManagerVo();
        FileManager fileManager = fileManagerService.findByPath(FileUtil.normalizeFilePath(filePath));
        if (fileManager == null) {
            //查询父类
            FileManager manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
            fileManagerVo.setParentId(manager.getId());
            fileManagerVo.setFilePath(FileUtil.normalizeFilePath(filePath));
            fileManagerVo.setFileName(FileConstants.LOGO);
            fileManagerVo.setFileSize((int) file.getSize());
            fileManagerVo.setFileHash(hash);
            fileManagerVo.setFileType(0);
            fileManagerService.add(fileManagerVo);
        } else {
            fileManagerVo.setId(fileManager.getId());
            fileManagerVo.setFilePath(FileUtil.normalizeFilePath(filePath));
            fileManagerVo.setFileSize((int) file.getSize());
            fileManagerVo.setFileHash(hash);
            fileManagerService.updateData(fileManagerVo);
        }
        return fileManagerVo.getFilePath();
    }

    @Override
    public String upload(UploadVo vo) {
        var userPath = userPath();
        var path = vo.getPath();
        if (!path.contains(userPath) && !StpUtil.hasRole(Constants.ADMIN)) {
            path = userPath + path;
        }
        path = path.replace("/", File.separator);

        MultipartFile file = vo.getFile();
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new ServiceException("文件为空");
        }
        if (file.getSize() / 1024 / 1024 > 500) {
            throw new ServiceException("只能上传大小小于500MB的文件");
        }
        path = path.isEmpty() ? path : path.substring(1);
        //根据用户区分文件夹
        path = fileConfig.getProfile() + path;
        //计算hash
        String hash = FileUtil.extractChecksum(file, SHA_256);
        if (!path.equals(fileConfig.getProfile())) {
            //验证是否存在当前文件
            String url = getFile(path, hash);
            if (StringUtil.isNotBlank(url)) {
                return url;
            }
        }
        Disk disk = fileManagerService.getUserHardDiskSize(Integer.valueOf((String) StpUtil.getLoginId()));
        if (disk.getFreeNoUnit() < file.getSize()) {
            throw new ServiceException("容量不足");
        }
        return addFile(path, file, file.getOriginalFilename(), hash);
    }

    @Override
    public String userPath() {
        var userPath = fileConfig.getFilePath() + StpUtil.getLoginId();
        return FileUtil.normalizeFilePath(userPath.replace(fileConfig.getProfile(), File.separator));
    }

    @Override
    public ReturnPageData<FileManagerDto> getPathList(String path) {
        var userPath = userPath();
        if (org.apache.commons.lang3.StringUtils.isBlank(path)) {
            if (StpUtil.hasRole(Constants.ADMIN)) {
                return fileManagerService.getParentIdPath(0, true);
            }
            path = userPath;
        }
        if (!path.contains(userPath) && !StpUtil.hasRole(Constants.ADMIN)) {
            throw new ServiceException("没有查看权限");
        }

        // 指定文件夹路径
        //        String folderPath = fileConfig.getProfile() + path;
        FileManager fileManager = fileManagerService.findByPath(FileUtil.normalizeFilePath(path));
        if (fileManager == null) {
            return new ReturnPageData<>();
        }
        return fileManagerService.getParentIdPath(fileManager.getId(), true);

        //        List<FileDto> list = new ArrayList<>();
        //        // 指定文件夹路径
        //        String folderPath = fileConfig.getProfile() + path;
        //        File folder = new File(folderPath);
        //        if (folder.isDirectory()) {
        //            File[] files = folder.listFiles();
        //            for (File file : files) {
        //                FileDto fileDto = new FileDto();
        //                fileDto.setNameSuffix("");
        //                fileDto.setPath(File.separator + file.getPath().replace(fileConfig.getProfile(), ""));
        //                fileDto.setPath(fileDto.getPath().replace("\\", "/"));
        //                if (!file.isDirectory()) {
        //                    fileDto.setUpdateTime(DateUtils.millisToDateTime(file.lastModified()));
        //                    fileDto.setSize(FileUtils.formatFileSize(file.length()));
        //                    fileDto.setNameSuffix(FileUtils.getFileExtension(file.getName()));
        //                }
        //                fileDto.setCreateTime(FileUtils.fileCreationTime(file.getPath()));
        //                fileDto.setName(file.getName());
        //                list.add(fileDto);
        //            }
        //        }
        //
        //        return list.stream().sorted(Comparator.comparing(FileDto::getNameSuffix)).toList();
    }

    @Override
    public ReturnPageData<FileManagerDto> getMarkdownImageListByPage() {
        // 指定文件夹路径
        String folderPath = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE;
        FileManager fileManager = fileManagerService.findByPath(FileUtil.normalizeFilePath(folderPath.replace(fileConfig.getProfile(), File.separator)));
        if (fileManager == null) {
            return new ReturnPageData<>();
        }
        return fileManagerService.getParentIdPath(fileManager.getId(), false);

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
    public Integer deletePathMarkdownImage(String path) {
        String pathFile = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.MARKDOWN_FILE + File.separator;
        pathFile = FileUtil.systemFilePath(pathFile + path);
        boolean isTrue = FileUtil.deleteFile(pathFile);
        pathFile = FileUtil.normalizeFilePath(pathFile);
        var profile = FileUtil.normalizeFilePath(fileConfig.getProfile());
        path = pathFile.replace(profile, "/");
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
        //判断路径
        FileManager manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(path.replace(fileConfig.getProfile(), File.separator)));
        if (manager != null) {
            //获取当前目录下相同的文件信息
            FileManager fileManager = fileManagerService.findByHash(manager.getId(), hash);
            if (fileManager != null) {
                return fileManager.getFilePath();
            }
        }
        createFolderIfNotExists(path);
        return null;
    }

    /**
     * 如果文件夹不存在，则创建它，并存入数据库
     *
     * @param path
     */
    public void createFolderIfNotExists(String path) {
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
            FileManager manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(parent.toString()));
            if (manager == null) {
                FileManagerVo fileManagerVo = new FileManagerVo();
                //当前文件夹不存在，查询有没有父类文件夹
                if (StringUtil.isNotBlank(parentPath.toString())) {
                    manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(parentPath.toString()));
                    fileManagerVo.setParentId(manager.getId());
                }
                fileManagerVo.setFilePath(FileUtil.normalizeFilePath(parent.toString()));
                fileManagerVo.setFileName(patch);
                fileManagerVo.setFileType(1);
                fileManagerService.add(fileManagerVo);
            }
            parentPath.append(File.separator).append(patch);
        }
        //创建目录
        FileUtil.directory(fileConfig.getProfile() + path);
    }

    @Override
    public String getCopyImage(String sourcePath, String newPath) {
        log.info("{}:{}", sourcePath, newPath);
        //计算hash
        String hash = FileUtil.extractChecksum(sourcePath, SHA_256);
        //验证是否存在当前文件
        String url = getFile(newPath, hash);
        if (StringUtil.isNotBlank(url)) {
            return url;
        }
        //新图片位置
        String filePath = MarkdownUtils.copyImage(sourcePath, newPath);
        File file = new File(filePath);
        //去掉前缀
        filePath = filePath.replace(fileConfig.getProfile(), File.separator);
        FileManagerVo fileManagerVo = new FileManagerVo();
        //查询父类
        FileManager manager = fileManagerService.findByPath(FileUtil.normalizeFilePath(newPath.replace(fileConfig.getProfile(), File.separator)));
        fileManagerVo.setParentId(manager.getId());
        fileManagerVo.setFilePath(FileUtil.normalizeFilePath(filePath));
        fileManagerVo.setFileName(file.getName());
        fileManagerVo.setFileSize((int) file.length());
        fileManagerVo.setFileHash(hash);
        fileManagerVo.setFileType(0);
        fileManagerService.add(fileManagerVo);
        return fileManagerVo.getFilePath();
    }

    @Override
    public List<FileManagerDto> getImportFiles() {
        // 指定文件夹路径
        String folderPath = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.TEMPORARY_FILE + File.separator + FileConstants.IMPORT_FILE;
        FileManager fileManager = fileManagerService.findByPath(FileUtil.normalizeFilePath(folderPath.replace(fileConfig.getProfile(), File.separator)));
        if (fileManager == null) {
            return List.of();
        }
        return fileManagerService.getParentIdPathList(fileManager.getId(), false);
    }

    @Override
    public List<FileManagerDto> getExportFiles() {
        // 指定文件夹路径
        String folderPath = fileConfig.getFilePath() + StpUtil.getLoginId() + File.separator + FileConstants.TEMPORARY_FILE + File.separator + FileConstants.EXPORT_FILE;
        FileManager fileManager = fileManagerService.findByPath(FileUtil.normalizeFilePath(folderPath.replace(fileConfig.getProfile(), File.separator)));
        if (fileManager == null) {
            return List.of();
        }
        return fileManagerService.getParentIdPathList(fileManager.getId(), false);
    }

}
