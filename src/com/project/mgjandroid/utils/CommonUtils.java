package com.project.mgjandroid.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.Selection;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.project.mgjandroid.R;
import com.project.mgjandroid.base.App;
import com.project.mgjandroid.ui.activity.SmsLoginActivity;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {

    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
    public final static String yyyy_MM_dd = "yyyy-MM-dd";
    public final static String MM_dd_HH_mm = "MM-dd HH:mm";

    public static boolean isSupportSinaSSO(Context context) {
        Intent intent = new Intent("com.sina.weibo.remotessoservice");
        List<ResolveInfo> list = context.getPackageManager().queryIntentServices(intent, PackageManager.GET_INTENT_FILTERS);
        return list.size() > 0;
    }

    public static synchronized boolean isNetworkConnected() {
        boolean isConnected = false;

        Context context = App.getInstance();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isConnected = true;
                    }
                }
            }
        }
        return isConnected;

    }

    public static synchronized boolean isWifiConnected() {
        ConnectivityManager connManager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                int networkInfoType = networkInfo.getType();
                if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
                    return networkInfo.isConnected();
                }
            }
        }
        return false;
    }

    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connManager = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                int networkInfoType = networkInfo.getType();
                if (networkInfoType == ConnectivityManager.TYPE_MOBILE) {
                    return networkInfo.isConnected();
                }
            }
        }
        return false;
    }

    public static boolean isAllowOfflineDownload(int networkType) {
        switch (networkType) {
            case ConnectivityManager.TYPE_ETHERNET:
            case ConnectivityManager.TYPE_WIFI:
                return true;
            default:
                return false;
        }
    }

    /**
     * 当前应用版本号
     *
     * @return
     */
    public static int getCurrentVersionCode() {
        try {
            App app = App.getInstance();
            PackageManager packageManager = app.getPackageManager();
            String packageName = app.getPackageName();
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            return info.versionCode;
        } catch (Exception e) {
        }
        return 1;
    }

    public static int getStatusBarHeight() {
        Resources res = Resources.getSystem();
        int resId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            return res.getDimensionPixelSize(resId);
        }
        return 0;
    }

    /**
     * 获取当前应用版本名称
     *
     * @param
     * @return
     */
    public static String getCurrentVersionName() {
        try {
            App app = App.getInstance();
            PackageManager packageManager = app.getPackageManager();
            String packageName = app.getPackageName();
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (Exception e) {
        }
        return "4.0.0";

    }

    public static void hideKeyBoard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyBoard2(View view) {
        InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyBoard(final View view) {
        final InputMethodManager imm = (InputMethodManager) App.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }, 100);


    }

    public static void setFullscreen(Activity activity, boolean on) {
        if (activity != null) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    public static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        // final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        // if (on) {
        // winParams.flags |= bits;
        // } else {
        // winParams.flags &= ~bits;
        // }
        win.setAttributes(winParams);
    }

    public static void setTranslucentNavigation(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        // final int bits =
        // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        // if (on) {
        // winParams.flags |= bits;
        // } else {
        // winParams.flags &= ~bits;
        // }
        win.setAttributes(winParams);
    }

    // 没有2g 3g判断，手机网络统一为2
    public static int getNetworkType() {
        int type = 0;
        if (isWifiConnected()) {
            type = 1;
        } else if (isMobileNetworkConnected()) {
            type = 2;
        }
        return type;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return "";
    }

    /**
     * 判断时间是否超时
     *
     * @param
     * @return
     */
//	public static boolean isTimeOut(long lastUpdate, String flag) {
//		if (lastUpdate == 0) {
//			return true;
//		}
//		long time = new Date().getTime();
//		long interval = time - lastUpdate;
//		if (ActionConstants.HALF_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_HALF_HOUR) {// 超过半小时
//			return true;
//		} else if (ActionConstants.FIVE_MINUTE.equals(flag) && interval > ActionConstants.INTERVAL_FIVE_MINUTE) {// 超过5分钟
//			return true;
//		} else if (ActionConstants.ONE_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_ONE_HOUR) {// 超过1小时
//			return true;
//		} else if (ActionConstants.SIX_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_SIX_HOUR) {// 超过6小时
//			return true;
//		} else if (ActionConstants.TWELVE_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_TWELVE_HOUR) {// 超过12小时
//			return true;
//		} else if (ActionConstants.TWO_HOUR.equals(flag) && interval > ActionConstants.INTERVAL_TWO_HOUR) {// 超过2小时
//			return true;
//		} else if (ActionConstants.TEN_MINUTE.equals(flag) && interval > ActionConstants.INTERVAL_TEN_MINUTE) {// 超过10分钟
//			return true;
//		}
//		return false;
//
//	}
    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String date = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        return date;
    }

    public static String getFormatDate(String date, String format, String parser) {
        try {
            Date d = new SimpleDateFormat(parser).parse(date);
            date = new SimpleDateFormat(format).format(d);
        } catch (Exception e) {
        }
        return date;
    }

    public static ArrayList<String[]> transStr2Points(String pointsStr) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        if (!CheckUtils.isEmptyStr(pointsStr)) {
            String[] pointsArr = pointsStr.split(";");
            for (String point : pointsArr) {
                list.add(point.split(","));
            }
        }
        return list;
    }

    public static String transPoints2Str(List<String[]> points) {
        String pointStr = "";
        if (!CheckUtils.isEmptyList(points)) {
            StringBuilder sb = new StringBuilder();
            for (String[] pointArr : points) {
                if (pointArr.length == 2) {
                    sb.append(pointArr[0]).append(",").append(pointArr[1]).append(";");
                }
            }
            pointStr = sb.substring(0, sb.length() - 1);
        }
        return pointStr;
    }


    public static boolean isServiceWorked(Class<?> mClass) {
        ActivityManager myManager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(mClass.getName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isServiceWorked(String className) {
        ActivityManager myManager = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().toString().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查版本号是否最新
     *
     * @param updateCode
     * @param act
     * @return
     */
    // public static boolean isVersionInvalid(String updateCode,Activity act){
    // if(CheckUtils.isEmptyStr(updateCode)){
    // return false;
    // }
    // List<PackageInfo> list = act.getPackageManager()
    // .getInstalledPackages(0);
    // int versionCode = 0;
    // for (int i = 0; i < list.size(); i++) {
    // PackageInfo packageInfo = list.get(i);
    // if (packageInfo.packageName.equals(act
    // .getString(R.string.app_packname))) {
    // versionCode = packageInfo.versionCode;
    // break;
    // }
    // }
    // if (String.valueOf(versionCode).compareTo(updateCode) <= -1) {
    // return true;
    // } else {
    // return false;
    // }
    //
    // }

    /**
     * 获取屏幕的高度
     *
     * @param manager
     * @return
     */
    public static int getScreenHeight(WindowManager manager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成三位随机数
     *
     * @return
     */
    public static int generateThreeRandomNum() {
        Random random = new Random();
        int num = (int) (random.nextDouble() * (1000 - 100) + 100);
        return num;
    }

    /**
     * 生成图片随机id
     *
     * @return
     */
    public static String generateImgID() {
        return new StringBuilder().
                append(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())).
                append(generateThreeRandomNum()).toString();
    }

    /**
     * 弹出底部菜单
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog getMenuDialog(Activity context, View view) {
        final Dialog dialog = new Dialog(context, R.style.MenuDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        int screenW = getScreenWidth(context.getWindowManager());
        lp.width = screenW;
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.MenuDialogAnimation); // 添加动画
        return dialog;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param manager
     * @return
     */
    public static int getScreenWidth(WindowManager manager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static String BigDecimal2Str(BigDecimal bigDecimal) {
        String str = "";
//		if(bigDecimal.remainder(BigDecimal.ONE).floatValue()>0){
        DecimalFormat df = new DecimalFormat("0.0"); // 保留1位小数
        str = df.format(bigDecimal);
//		}else{
//			DecimalFormat df = new DecimalFormat("0"); // 取整
//			str = df.format(bigDecimal);
//		}
        return str;
    }

    public static boolean checkLogin(Activity mActivity) {
        if (!App.isLogin()) {
            mActivity.startActivityForResult(new Intent(mActivity, SmsLoginActivity.class), 5);
            return false;
        }
        return true;
    }

    /**
     * @param time
     * @return
     */
    public static String formatTime(long time, String format) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 验证用户名
     *
     * @param username 用户名
     * @return boolean
     */
    public static boolean checkUsername(String username) {
        String regex = "([a-zA-Z0-9\\u4e00-\\u9fa5]{2,12})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    /**
     * 验证职位名
     *
     * @param username 用户名
     * @return boolean
     */
    public static boolean checkJobname(String username) {
        String regex = "([a-zA-Z0-9\\u4e00-\\u9fa5]{1,8})";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean checkSalary(String c) {
        String regex = "[-0-9]{1}";

        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(c);
        return m.matches();
    }

    /**
     * 自动安装apk
     *
     * @param file
     */
    public static void openFile(Context context, File file) {
        if (file == null || !file.exists()) {
            return;
        }
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * md5加密
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 读取assets中的文件
     */
    public static String readFileFromAssets(Context context, String fileName) {
        String txt = null;
        try {
            InputStream in = context.getAssets().open(fileName);  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
            int size = in.available();//取得数据流的数据大小
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            txt = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    private static boolean isToday(Date leaveTime, Date serviceTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leaveTime);
        int leaveYear = calendar.get(Calendar.YEAR);
        int leaveMonth = calendar.get(Calendar.MONTH);
        int leaveDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(serviceTime);
        int serviceYear = calendar.get(Calendar.YEAR);
        int serviceMonth = calendar.get(Calendar.MONTH);
        int serviceDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (leaveYear == serviceYear && leaveMonth == serviceMonth && leaveDay == serviceDay) {
            return true;
        }
        return false;
    }

    public static boolean canCall(Date leaveTime, Date serviceTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leaveTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        long leave = calendar.getTime().getTime();
        calendar.setTime(serviceTime);
        long now = calendar.getTime().getTime();
        return leave < now;
    }

    private static boolean isTomorrow(Date leaveTime, Date serviceTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leaveTime);
        int leaveYear = calendar.get(Calendar.YEAR);
        int leaveMonth = calendar.get(Calendar.MONTH);
        int leaveDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(serviceTime);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int serviceYear = calendar.get(Calendar.YEAR);
        int serviceMonth = calendar.get(Calendar.MONTH);
        int serviceDay = calendar.get(Calendar.DAY_OF_MONTH);
        if (leaveYear == serviceYear && leaveMonth == serviceMonth && leaveDay == serviceDay) {
            return true;
        }
        return false;
    }

    public static String getTime(Date leaveTime, Date serviceTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(leaveTime);
        int leaveMonth = calendar.get(Calendar.MONTH);
        int leaveDay = calendar.get(Calendar.DAY_OF_MONTH);
        int leaveHour = calendar.get(Calendar.HOUR_OF_DAY);
        String hour = "";
        if (leaveHour < 10) {
            hour = "0" + leaveHour;
        } else {
            hour = "" + leaveHour;
        }
        int leaveMin = calendar.get(Calendar.MINUTE);
        if (isToday(leaveTime, serviceTime)) {
            if (leaveMin < 10) {
                return "今天 (" + (leaveMonth + 1) + "月" + leaveDay + "日) " + hour + ":0" + leaveMin;
            }
            return "今天 (" + (leaveMonth + 1) + "月" + leaveDay + "日) " + hour + ":" + leaveMin;
        }
        if (isTomorrow(leaveTime, serviceTime)) {
            if (leaveMin < 10) {
                return "明天 (" + (leaveMonth + 1) + "月" + leaveDay + "日) " + hour + ":0" + leaveMin;
            }
            return "明天 (" + (leaveMonth + 1) + "月" + leaveDay + "日) " + hour + ":" + leaveMin;
        }
        if (leaveMin < 10) {
            return (leaveMonth + 1) + "月" + leaveDay + "日 " + hour + ":0" + leaveMin;
        }
        return (leaveMonth + 1) + "月" + leaveDay + "日 " + hour + ":" + leaveMin;
    }

    public static boolean isBeyondSpring(Date currentTime) {
        String overTime = "2017-02-12 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        try {
            Date over = sdf.parse(overTime);
            return currentTime.getTime() > over.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String getOrderIntro(List<String> name, int count) {
        if (CheckUtils.isNoEmptyList(name)) {
            if (name.size() == 1) {
                return name.get(0) + num2Chinese(count) + "件商品";
            }
            return name.get(0) + "等" + num2Chinese(count) + "件商品";
        }
        return "";
    }

    public static String getCouponCode(int count) {
        if (count != 0) {
            return "券码" + num2Chinese(count);
        }
        return "";
    }

    static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
            "十亿", "百亿", "千亿", "万亿"};
    static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    private static String num2Chinese(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    continue;
                } else {
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    }

    public static int getCurrentCursorLine(EditText editText) {
        int selectionStart = Selection.getSelectionStart(editText.getText());
        Layout layout = editText.getLayout();
        if (selectionStart != -1) {
            return layout.getLineForOffset(selectionStart) + 1;
        }
        return -1;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR2)
    public static boolean checkViewVisibleInWindow(Activity activity, View view) {
        Point p = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(p);
        int screenWidth = p.x;
        int screenHeight = p.y;
        Rect rect = new Rect(0, 0, screenWidth, screenHeight);
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return view.getLocalVisibleRect(rect);
    }


    /**
     * 计算两点之间真实距离
     *
     * @return 千米(km)
     */
    public static double getDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        // 纬度
//        double lat1 = (Math.PI / 180) * latitude1;
//        double lat2 = (Math.PI / 180) * latitude2;
//        // 经度
//        double lon1 = (Math.PI / 180) * longitude1;
//        double lon2 = (Math.PI / 180) * longitude2;
//        // 地球半径
//        double R = 6371;

        //地球半径
        double R = 6371.004;

        Double x = (longitude2 - longitude1) * Math.PI * R * Math.cos(((latitude1 + latitude2) / 2) * Math.PI / 180) / 180;
        Double y = (latitude2 - latitude1) * Math.PI * R / 180;


        Double distance = Math.hypot(x, y);   //得到两点之间的直线距离


        // 两点间距离 km,如果想要米的话,结果*1000
//        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        return distance;
    }


    public static String getLatLngDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
        //地球半径
        double R = 6371.004;

        Double x = (longitude2 - longitude1) * Math.PI * R * Math.cos(((latitude1 + latitude2) / 2) * Math.PI / 180) / 180;
        Double y = (latitude2 - latitude1) * Math.PI * R / 180;


        Double distance = Math.hypot(x, y);   //得到两点之间的直线距离


        //两点间距离 m，如果想要米的话，结果*1000就可以了
//        double dis = Math.acos(Math.sin(latitude1) * Math.sin(latitude2) + Math.cos(latitude1) * Math.cos(latitude2) * Math.cos(longitude2 - longitude1)) * R;
        NumberFormat nFormat = NumberFormat.getNumberInstance();  //数字格式化对象
        if (distance < 1) {               //当小于1千米的时候用,用米做单位保留一位小数
            nFormat.setMaximumFractionDigits(1);    //已可以设置为0，这样跟百度地图APP中计算的一样
            distance *= 1000;
            return nFormat.format(distance) + "m";
        } else {
            nFormat.setMaximumFractionDigits(2);
            return nFormat.format(distance) + "km";
        }

    }
}
