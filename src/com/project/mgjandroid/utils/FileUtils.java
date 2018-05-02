package com.project.mgjandroid.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.project.mgjandroid.base.FileCache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class FileUtils {

    public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
    public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
    public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
    public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值

    /**
     * 反序列化获取Object对象，文件不存在时返回NULL
     *
     * @return
     * @author jian
     */
    public static Object unserializeObject(String path) {
        File sectionFile = new File(path);
        // 直接反序列化
        if (!sectionFile.exists()) {
            return null;
        }
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(path));
            o = ois.readObject();
        } catch (Exception e) {
            sectionFile.delete();
            return null;
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
        }
        return o;
    }

    public static byte[] readFileHeader(File file) {
        if (file == null) {
            return null;
        }
        byte[] result = null;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            int fileSize = (int) file.length();
            if (fileSize >= 15) {
                fileSize = 15;
            }
            result = new byte[fileSize];

            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            bis.read(result, 0, fileSize);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static boolean serializeObject(String path, Object o) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(path));
            oos.writeObject(o);
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                file.delete();
            }
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public static byte[] readFile(File file) {
        if (file == null) {
            return null;
        }
        byte[] result = null;
        try {
            int fileSize = (int) file.length();
            result = new byte[fileSize];
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(result, 0, fileSize);
            bis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        file.setLastModified(System.currentTimeMillis());
        return result;
    }

    public static boolean isFileExist(String path) {
        try {
            File file = new File(path);
            return file.exists();
        } catch (Exception e) {
        }
        return false;
    }

    public static void delFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }

    }

    public static void delAllFiles(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists() || dir.isFile()) {
                return;
            }

            for (File file : dir.listFiles()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }

    public static void deleteAllCacheFile() {
        try {
            File imagefile = new File(FileCache.getAppImageCacheDirectory());// 图片缓存
            if (imagefile != null && imagefile.exists())
                delete(imagefile);
        } catch (Exception e) {
        }
    }

    private static void delete(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
        }
    }

    public static String getAutoCacheSize() {
        long imageSize = 0;

        try {
            File imageFile = new File(FileCache.getAppImageCacheDirectory());
            if (imageFile.isDirectory()) {
                imageSize = getFileSizes(imageFile);
            } else {
                imageSize = getFileSize(imageFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.gc();
        }

        return FormetFileSize(imageSize);
    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file == null) {
            return 0;
        }
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }

    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        if (f == null) {
            return 0;
        }
        File flist[] = f.listFiles();
        if (flist == null || flist.length <= 0) {
            return 0;
        }
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    private static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    /**
     * 转换文件大小,指定转换的类型
     *
     * @param fileS
     * @param sizeType
     * @return
     */
    private static double FormetFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZETYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZETYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZETYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZETYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }

    // sd卡根目录
    public final static String ROOT_DIR = Environment.getExternalStorageDirectory() + "/maguanjia/data_cache/";

    public static String getDirUrl(String pDir) {
        File dir = new File(FileCache.getAppDataCacheDirectory());

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File pFileDir = new File(dir + File.separator + pDir);
        if (!pFileDir.exists()) {
            pFileDir.mkdirs();
        }

        return pFileDir.getPath();
    }

    public static String getFileUrl(String pDir, String name) {

        String pFileDir = getDirUrl(pDir);

        File f = new File(pFileDir + File.separator + name + ".dat");
        if (!f.exists()) {
            try {
                f.createNewFile();

                // Log.d("wdp", "create new file success!");
            } catch (IOException e) {
                e.printStackTrace();
                // Log.e("wdp", "failed exception :" + e);
            }
        }
        return f.getPath();
    }

    public static String getFilePath(String dir, String name) {
        File filedir = new File(dir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        File f = new File(filedir + File.separator + name + ".dat");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f.getPath();
    }

    public static File getApkFileUrl(String name) {

        File dir = new File(FileCache.getAppDataCacheDirectory());

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File f = new File(dir + File.separator + name + ".apk");
        if (!f.exists()) {
            try {
                f.createNewFile();

                // Log.d("wdp", "create new file success!");
            } catch (IOException e) {
                e.printStackTrace();
                // Log.e("wdp", "failed exception :" + e);
            }
        }
        return f;
    }

    public static File getApkSDUrl(String name) {

        File dir = new File(FileCache.getSDPath());

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File f = new File(dir + File.separator + name + ".apk");
        if (!f.exists()) {
            try {
                f.createNewFile();

                // Log.d("wdp", "create new file success!");
            } catch (IOException e) {
                e.printStackTrace();
                // Log.e("wdp", "failed exception :" + e);
            }
        }
        return f;
    }

    public static File createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * get file md5
     *
     * @param file
     * @return
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getFileMD5(File file) throws NoSuchAlgorithmException, IOException {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        digest = MessageDigest.getInstance("MD5");
        in = new FileInputStream(file);
        while ((len = in.read(buffer, 0, 1024)) != -1) {
            digest.update(buffer, 0, len);
        }
        in.close();
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
