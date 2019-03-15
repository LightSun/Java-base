package com.heaven7.java.base.util;

import java.io.*;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * the file utils
 * @author heaven7
 * @since 1.1.7
 */
public class FileUtils {

    /**
     * the filename transformer
     */
    public interface FilenameTransformer{
        /**
         * transform the name
         * @param name the dir name or file name, exclude "/" and "\"
         * @return the result.
         */
        String transform(String name);
    }

    public static final FileFilter TRUE_FILE_FILTER =
            new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return true;
                }
            };

    /**
     * get the md5 for target file
     * @param path the file path
     * @return the file md5.
     */
    public static String getMD5Three(String path) {
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(path);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
        return bi.toString(16);
    }
    /**
     * create the file by base dir ,sub dirs and file name.
     * @param baseDir the base dir
     * @param simpleFileName the simple file name
     * @param subDirs the sub dirs
     * @return the file path.
     */
    public static String createFilePath(String baseDir, String simpleFileName, String...subDirs){
        StringBuilder sb = new StringBuilder();
        sb.append(baseDir);
        if (subDirs != null) {
            for (String dir : subDirs) {
                sb.append(File.separator).append(dir);
            }
        }
        return sb.append(File.separator).append(simpleFileName).toString();
    }

    /** get the file name only. exclude extension and dir.
     * @param path the file path.
     * */
    public static String getFileName(String path) {
        int index = path.lastIndexOf("/");
        if(index == -1){
            index = path.lastIndexOf("\\");
        }
        return path.substring(index + 1, path.lastIndexOf("."));
    }

    /**
     * get the simple file name
     * @param path the file path
     * @return the simple file name
     */
    public static String getSimpleFileName(String path){
        String extension = getFileExtension(path);
        if(extension != null){
            return getFileName(path) + "." + extension;
        }
        return getFileName(path);
    }

    /**
     * get the last path for target path
     * @param path the src path
     * @return the last path. if is dir return the last path.
     */
    public static String getLastPath(String path){
        int index = path.lastIndexOf("/");
        if(index == -1){
            index = path.lastIndexOf("\\");
        }
        return path.substring(0, index);
    }

    /**
     * get the file dir. like: 'empty/dinner/xxx.mp4' -> 'empty/dinner' or 'empty'.
     * @param filepath the file path
     * @param depth the depth of dir
     * @param fullPath true to get the full dir. false only return a simple dir
     * @return the file dir.
     */
    public static String getFileDir(String filepath, int depth, boolean fullPath){
        if(depth < 1) throw new IllegalArgumentException("depth must > 0");
        File file = new File(filepath);
        if(file.exists()){
            //can be file or dir
            File parent = file;
            while (depth > 0){
                depth --;
                parent = parent.getParentFile();
                if(parent == null){
                    return null;
                }
            }
            return fullPath ? parent.getAbsolutePath() :parent.getName();
        }
        Logger.d("FileUtils", "getFileDir", "file not exist. filepath = " + filepath);
        return null;
    }

    public static String encodeChinesePath(String path){
        return transformPath(path, new FilenameTransformer() {
            @Override
            public String transform(String name) {
                try {
                    return URLEncoder.encode(name, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    return name;
                }
            }
        });
    }
    public static String decodeChinesePath(String path){
        return transformPath(path, new FilenameTransformer() {
            @Override
            public String transform(String name) {
                try {
                    return URLDecoder.decode(name, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    return name;
                }
            }
        });
    }

    /** get file path which is encode by path
     * @param path  the path to transform
     * @param transformer the transformer to transform every name.
     * @return the transformed path
     * */
    public static String transformPath(String path, final FilenameTransformer transformer){
        List<String> names = new ArrayList<>();
        //c:\xxx\xxx1\xxx2.jpg -> c:\xxx\xxx1\xxx2
        String tmpPath = path.contains(".") ? path.substring(0, path.lastIndexOf(".")) : path;
        File file = new File(tmpPath);
        if(!file.exists()){
            Logger.d("", "transformPath", "path not exists. path = " + path);
        }
        File parent = file;
        do {
            names.add(parent.getName());
            parent = parent.getParentFile();
            if(parent == null){
                break;
            }
        }while (true);

        final StringBuilder sb = new StringBuilder();
        int index = path.indexOf(File.separator);
        if(index == -1){
            throw new IllegalArgumentException();
        }
        sb.append(path.substring(0, index));
        for (String s : names){
            sb.append(File.separator).append(transformer.transform(s));
        }
        if(path.contains(".")){
            sb.append(".").append(getFileExtension(path));

        }
        String s = sb.toString();
        if(s.contains(":\\\\")){
            s = s.replace(":\\\\", ":\\");
        }
        return s;
    }
    /**
     * write the content into target file
     * @param file the dest file
     * @param content the content to write.
     */
    public static void writeTo(String file, String content) {
        writeTo(new File(file), content);
    }

    /**
     * write the content into target file
     * @param dst the dest file
     * @param content the content to write.
     */
    public static void writeTo(File dst, String content) {
        if (dst.isDirectory()) {
            throw new IllegalStateException();
        }
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }
        if (dst.exists()) {
            dst.delete();
        }
        //empty
        if(content == null || content.length() == 0){
            System.out.println("no content for: " + dst.getAbsolutePath());
            return;
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(dst);
            fw.write(content);
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fw);
        }
    }

    /**
     * create the file for target path.
     * @param path the file path to
     * @param deleteIfExist true to delete if exist
     */
    public static boolean createFile(String path, boolean deleteIfExist){
        File file = new File(path);
        if(file.exists()){
            if(deleteIfExist) {
                file.delete();
            }else {
                return true;
            }
        }
        try {
            file.createNewFile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * get the files.
     * @param dir the file dir
     * @param extension the extension
     * @param filter the file filter
     * @return the files.
     */
    public static List<String> getFiles(File dir, String extension,
                                        FileFilter filter) {
        List<String> paths = new ArrayList<>();
        getFiles(dir, extension, filter, paths);
        return paths;
    }

    /**
     * get the files by target extension  Recursively.
     * @param dir the file dir
     * @param extension the extension
     * @return the files.
     */
    public static List<String> getFiles(File dir, String extension) {
        List<String> paths = new ArrayList<>();
        getFiles(dir, extension, TRUE_FILE_FILTER, paths);
        return paths;
    }

    /**
     * get the files by target extension Recursively.
     * @param dir the file dir
     * @param extension the extension
     * @param outFiles the out file list.
     */
    public static void getFiles(File dir, String extension, List<String> outFiles) {
        getFiles(dir, extension, TRUE_FILE_FILTER, outFiles);
    }

    /**
     * get the files Recursively. but exclude dirs.
     * @param dir the file dir
     * @param extension the extension to check.
     * @param filter the file filter
     * @param outFiles the out file list.
     */
    public static void getFiles(File dir, final String extension,
                                final FileFilter filter, List<String> outFiles) {
        File[] videoFiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) {
                    return false;
                }
                if (!filter.accept(pathname)) {
                    return false;
                }
                String extension2 = getFileExtension(pathname);
                return extension.equalsIgnoreCase(extension2);
            }
        });
        if (!Predicates.isEmpty(videoFiles)) {
            for (File file : videoFiles) {
                outFiles.add(file.getAbsolutePath());
            }
        }
        File[] dirs = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (!Predicates.isEmpty(dirs)) {
            for (File dir1 : dirs) {
                getFiles(dir1, extension, filter, outFiles);
            }
        }
    }

    /**
     * get the files from target dir Recursively.
     * @param dir the dir
     * @param filter the file filter
     * @param outFiles the out file list.
     */
    public static void getFiles(File dir, FileFilter filter, List<String> outFiles) {
        File[] videoFiles = dir.listFiles(filter);
        if (!Predicates.isEmpty(videoFiles)) {
            for (File file : videoFiles) {
                outFiles.add(file.getAbsolutePath());
            }
        }
        File[] dirs = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (!Predicates.isEmpty(dirs)) {
            for (File dir1 : dirs) {
                getFiles(dir1, filter, outFiles);
            }
        }
    }

    /**
     * get the files from target dir Recursively.
     * @param dir the dir
     * @param filter the file filter
     * @return the filtered files.
     */
    public static List<String> getFiles(File dir, FileFilter filter) {
        List<String> files = new ArrayList<>();
        getFiles(dir, filter, files);
        return files;
    }

    /**
     * delete the dir Recursively.
     * @param dir the dir
     * @return true of success.
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * get the file extension. which exclude "."
     * @param filename the file path
     */
    public static String getFileExtension(String filename) {
        try {
            return filename.substring(filename.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get the file extension. which exclude "."
     * @param file the file
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getAbsolutePath());
    }

    /**
     * copy files from src dir to dst fir
     * @param srcDir the src dir
     * @param dstDir the dest dir
     * @param filter the file filter
     * @return true .if success.
     */
    public static boolean copyFilesFromDir(File srcDir, File dstDir, FileFilter filter) {
        if(!srcDir.exists()){
            return false;
        }
        if(!dstDir.exists()){
            dstDir.mkdirs();
        }
        final String srcPath = srcDir.getAbsolutePath();
        List<String> files = getFiles(srcDir, filter);
        for (String s : files){
            int index = s.indexOf(srcPath);
            String target = dstDir.getAbsolutePath() + s.substring(index + srcPath.length());
            copyFile(new File(s), new File(target));
        }
        return true;
    }

    /**
     * copy file from src to dst.
     * @param src the src
     * @param dst the dest
     * @return true if copy success.
     */
    public static boolean copyFile(File src, File dst) {
        if(!src.exists()){
            return false;
        }
        if(!dst.getParentFile().exists()){
            dst.getParentFile().mkdirs();
        }else{
            if (dst.exists()) {
                dst.delete();
            }
        }
        try {
            dst.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            in = new BufferedInputStream(new FileInputStream(src));
            out = new BufferedOutputStream(new FileOutputStream(dst));
            byte[] buffer = new byte[1024 * 4];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return true;
    }

    /**
     * create the filter by last simple dir name
     * @param dir the name of last dir
     * @return the file filter.
     */
    public static FileFilter createLastDirFileFilter(String dir){
        Throwables.checkNull(dir);
        return new DirFileFilter(dir);
    }

    private static final class DirFileFilter implements FileFilter{
        private final String dir;

        DirFileFilter(String dir) {
            this.dir = dir;
        }
        @Override
        public boolean accept(File pathname) {
            return dir.equals(FileUtils.getFileDir(pathname.getAbsolutePath(), 1, false));
        }
    }
}
