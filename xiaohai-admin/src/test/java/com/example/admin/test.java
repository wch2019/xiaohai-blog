package com.example.admin;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.net.MalformedURLException;


@Slf4j
public class test {
    public static void main(String[] args) {
        getRemoteFile();
    }

    public static void getRemoteFile() {
        InputStream in = null;
        try {
            // 创建远程文件对象
            // smb://用户名:密码/共享的路径/...
            // smb://ip地址/共享的路径/...
            String url = "smb://192.168.68.207/data/files/wangchenghai/视频/Other/md[132部]/";
            String userName = "root";
            String password = "wch-1998";
            String domain = null;
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, userName, password);
            SmbFile remoteFile = new SmbFile(url, auth);
            remoteFile.connect();//尝试连接
            if (remoteFile.exists()) {
                // 获取共享文件夹中文件列表
                SmbFile[] smbFiles = remoteFile.listFiles();
                for (SmbFile smbFile : smbFiles) {
                    doRecursiveLookup(smbFile);
                }
            } else {
                log.error("文件不存在！");
            }
        } catch (Exception e) {
            log.error("访问远程文件夹出错：" + e.getLocalizedMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (Exception e) {
                log.error("关闭资源错误");
            }
        }
    }

    public static void doRecursiveLookup(SmbFile smb) {
        try {
            if (smb.isDirectory()) {
                System.out.println(smb.getName());
                int count = 0;
                for (SmbFile f : smb.listFiles()) {
                    if (f.getName().endsWith(".ts")) {
                        count++;
                    }
                }
                System.out.println("===========================================" + count);
                for (SmbFile f : smb.listFiles()) {
                    if (f.isDirectory()) {
                        doRecursiveLookup(f);
                    } else {
                        System.out.println("\t:" + f.getName());
                        if (f.getName().endsWith(".nfo")) {
                            deleteFile(f);
                        }
                        if (count == 1) {
                            if (f.getName().endsWith(".ts")) {
                                updateFile(f, smb.getName() + smb.getName().substring(0, smb.getName().length() - 1) + ".TS");
                            }
                            if (f.getName().endsWith(".jpg")) {

                                updateFile(f, smb.getName() + smb.getName().substring(0, smb.getName().length() - 1) + ".jpg");
                            }
                        }

                    }
                }
            } else {
                System.out.println("\t:" + smb.getName());
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除文件
     */
    public static void deleteFile(SmbFile f) {
        String fileName = f.getName().replace(".TS", "");
        try {
            if (f.exists()) {
                f.renameTo(new SmbFile(f.getURL()));
                f.delete();
                System.out.println("删除文件成功\t:" + f.getName());
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重命名
     *
     * @param f
     */
    public static void updateFile(SmbFile f, String name) {
        String url = "smb://192.168.68.207/data/files/wangchenghai/视频/Other/md[132部]/"+name;
        String userName = "root";
        String password = "wch-1998";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, userName, password);
        try {
            SmbFile remoteFile = new SmbFile(url, auth);
            if (f.exists()) {
                f.renameTo(remoteFile);
                System.out.println("修改文件名成功\t:" + f.getName());
            }
        } catch (SmbException e) {
            e.printStackTrace();

        } catch (
                MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
