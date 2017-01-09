package com.dl.commonutils;

import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DuanLu on 2016/11/10 15:19
 *
 * @author DuanLu
 * @version 1.0.0
 * @class FileUtils
 * @describe 关于文件的处理
 */
public class FileUtils {
    private final String TAG = "FileUtils";

    private FileUtils() {

    }

    /**
     * SD卡是否存在
     *
     * @return
     */
    public static boolean isExistSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    public static boolean isSDCardUser() {
        if (FileUtils.isExistSDCard()) {
            return FileUtils.isExistSDCard() && FileUtils.getSDFreeSize() > 2;
        } else {
            return false;
        }
    }

    /**
     * 判断SD卡上的文件夹是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isFile(String fileName) {
        File file = new File(fileName);
        return file.isFile();
    }

    /**
     * SD卡剩余空间
     *
     * @return
     */
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        @SuppressWarnings("deprecation")
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        @SuppressWarnings("deprecation")
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        // return freeBlocks * blockSize; //单位Byte
        // return (freeBlocks * blockSize)/1024; //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 获取文件大小(单位：byte),文件不存在时返回-1
     *
     * @param filePath 文件路径
     * @return
     */
    public static long getFileSize(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return -1;
        }
        File file = new File(filePath);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    /**
     * 删除文件或目录
     *
     * @param path 文件或文件夹路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFileOrDirs(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        //为一个文件夹时递归删除
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFileOrDirs(f.getAbsolutePath());
            }
        }
        //删除完文件夹里面的内容后删除文件夹
        return file.delete();
    }

    /**
     * 判断该路径是否是一个已存在的文件
     *
     * @param filePath 需要判断的路径
     * @return 是已存在的文件时返回true，否则返回false
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    /**
     * 判断该路径是否是一个已存在的文件夹
     *
     * @param directoryPath 需要判断的路径
     * @return 是已存在的文件夹时返回true，否则返回false
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }
        File dir = new File(directoryPath);
        return dir.exists() && dir.isDirectory();
    }

    /**
     * 创建一个目录
     *
     * @param filePath 目录路径
     * @return
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFileOrFolderName(filePath);
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File folder = new File(folderName);
        return folder.exists() && folder.isDirectory() ? true : folder.mkdirs();
    }

    /**
     * 创建一个文件夹
     *
     * @param folderPath 文件夹路径
     * @return
     */
    public static boolean makeFolders(String folderPath) {
        return makeDirs(folderPath);
    }

    /**
     * 获取文件或文件夹名
     *
     * @param filePath 文件或文件夹路径
     * @return
     */
    public static String getFileOrFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int filePosi = filePath.lastIndexOf(File.separator);
        return filePosi == -1 ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 按行读取文件，返回ArrayList
     *
     * @param filePath    文件路径
     * @param charsetName 文件名
     * @return
     */
    @Nullable
    public static List<String> readFileToList(String filePath, String charsetName) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        BufferedReader bufReader = null;
        InputStreamReader isReader = null;
        List<String> fileContent = new ArrayList<>();
        try {
            isReader = new InputStreamReader(new FileInputStream(file), charsetName);
            bufReader = new BufferedReader(isReader);
            String line = null;
            while ((line = bufReader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 读取文件
     *
     * @param filePath    文件路径
     * @param charsetName 文件名
     * @return 返回读取的StringBuilder对象
     */
    @Nullable
    public static StringBuilder readFile(String filePath, String charsetName) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        BufferedReader bufReader = null;
        InputStreamReader isReader = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            isReader = new InputStreamReader(new FileInputStream(file), charsetName);
            bufReader = new BufferedReader(isReader);
            String line;
            while ((line = bufReader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isReader != null) {
                try {
                    isReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 写入文件
     *
     * @param filePath 文件路径
     * @param content  需要写入的内容
     * @param append   设置该该流对文件的操作是否为续写
     * @return 写入成功返回true，反之则为false
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(content)) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 写入文件
     *
     * @param filePath    文件路径
     * @param contentList 需要写入的内容(List<String>格式)
     * @param append      设置该该流对文件的操作是否为续写
     * @return 写入成功返回true，反之则为false
     */
    public static boolean writeFile(String filePath, List<String> contentList, boolean append) {
        if (TextUtils.isEmpty(filePath) || contentList == null) {
            return false;
        }
        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 写入文件
     *
     * @param filePath    文件路径
     * @param inputStream 需要写入的内容(InputStream对象)
     * @param append      设置该该流对文件的操作是否为续写
     * @return 写入成功返回true，反之则为false
     */
    public static boolean writeFile(String filePath, @NonNull InputStream inputStream, boolean append) {
        if (TextUtils.isEmpty(filePath) || inputStream == null) {
            return false;
        }
        OutputStream os = null;
        try {
            makeDirs(filePath);
            os = new FileOutputStream(new File(filePath), append);
            byte data[] = new byte[1024];
            int length;
            while ((length = inputStream.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * copyFile
     *
     * @param sourceFilePath 源文件路径
     * @param destFilePath   目标文件路径
     * @return copy succeed return true，else return false.
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {
        InputStream is = null;
        try {
            is = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return writeFile(destFilePath, is, false);
    }

}
