package com.gdtc.oasystem.utils;

/**
 * Created by wangjiawei on 2017-9-1.
 */

public class CheckVersionUtils {

//    private static CheckVersionUtils mCheckVersionUtils;
//
//    private CheckVersionUtils() {
//    }
//
//    public static CheckVersionUtils getInstance() {
//        if (mCheckVersionUtils == null) {
//            mCheckVersionUtils = new CheckVersionUtils();
//        }
//        return mCheckVersionUtils;
//    }
//
//    /**
//     * 访问网络检查版本号码
//     *
//     * @param isToastNoVersion 是否弹出"当前版本已为最新版本"的Toast
//     * @param activity         弹窗需要的依赖的Activity
//     */
//    public void checkVersionToServer(final boolean isToastNoVersion, final Activity activity) {
//        x.http().get(new RequestParams(ConstantValue.CHECK_VERSION_JSON_URL), new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.e("CheckVersionActivity", "CheckVersionActivity onSuccess()" + result);
//                processData(result, activity, isToastNoVersion);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
//
//    }
//
//
//    /**
//     * 返回版本号
//     */
//    private int getVersionCode() {
//        return BuildConfig.VERSION_CODE;
//    }
//
//
//    /**
//     * 解析JSON
//     *
//     * @param json
//     * @param isToastNoVersion
//     */
//    private void processData(String json, Activity activity, boolean isToastNoVersion) {
//        Gson gson = new Gson();
//        CheckVersionBean checkVersionBean = gson.fromJson(json, CheckVersionBean.class);
//        /**
//         * 保存在手机的位置
//         */
//        String saveSDPath = EXB_SD_PATH_APK + checkVersionBean.getFileName();
//        //判断是否需要更新
//        if (getVersionCode() < checkVersionBean.getVersionCode()) {
//            showUpdateDialog(checkVersionBean, saveSDPath, activity);
//        } else {
//            if (isToastNoVersion) {
//                //不更新
//                Toast.makeText(activity, "当前版本已为最新版本", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//    /**
//     * 显示更新对话框
//     *
//     * @param checkVersionBean
//     */
//    private void showUpdateDialog(final CheckVersionBean checkVersionBean, final String saveSDPath, final Activity activity) {
//
//        //创建文件,判断是否存在
//        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        AlertDialog alertDialog = builder.create();
//
//
//        //更新描述信息
//        String updateLog = checkVersionBean.getUpdateLog();
//        if (TextUtils.isEmpty(updateLog)) {
//            updateLog = "新版本，欢迎更新";
//        }
//        //版本名称
//        String versionName = checkVersionBean.getVersionName();
//        if (TextUtils.isEmpty(versionName)) {
//            versionName = "1.1";
//        }
//
//        alertDialog.setTitle("新版本" + versionName);
//        alertDialog.setMessage(updateLog);
//
//
//        //判断是否要强制更新
//        if (checkVersionBean.getIsForceUpdate() == 1) {
//            //强制更新
//            //设置外部点了没有效果
//            alertDialog.setCanceledOnTouchOutside(false);
//            //禁用返回键
//            alertDialog.setCancelable(false);
//
//            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "更新", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    //更新
//                    downloadApk(checkVersionBean.getDownloadUrl(), saveSDPath, activity);
//                }
//            });
//
//        } else {
//            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//
//            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "更新", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //更新
//                    downloadApk(checkVersionBean.getDownloadUrl(), saveSDPath, activity);
//                }
//            });
//        }
//
//        alertDialog.show();
//    }
//
//
//    /**
//     * 下载apk
//     *
//     * @param downurl
//     */
//    private void downloadApk(String downurl, String path, final Activity activity) {
//        final ProgressDialog progressDialog = new ProgressDialog(activity);
//        RequestParams requestParams = new RequestParams(downurl);
//        requestParams.setSaveFilePath(path);
//
//        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
//            @Override
//            public void onWaiting() {
//            }
//            @Override
//            public void onStarted() {
//            }
//            @Override
//            public void onLoading(long total, long current, boolean isDownloading) {
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//                progressDialog.setMessage("拼命下载中...");
//                progressDialog.show();
//                progressDialog.setMax(100);
//                double fcurrent = current;
//                progressDialog.setProgress((int) ((fcurrent / total) * 100));
//            }
//            @Override
//            public void onSuccess(File result) {
//                Toast.makeText(NiceyooApplication.getNiceyooApp(), "下载完成", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//                installApk(activity, result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                ex.printStackTrace();
//                Toast.makeText(NiceyooApplication.getNiceyooApp(), "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
//                progressDialog.dismiss();
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//            }
//
//            @Override
//            public void onFinished() {
//            }
//        });
//    }
//
//
//    /**
//     * 安装对应apk
//     *
//     * @param activity
//     * @param file     安装文件
//     */
//    private void installApk(Activity activity, File file) {
//        //系统应用界面,源码,安装apk入口
//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        //设置安装的类型
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        activity.startActivityForResult(intent, ConstantValue.INSTALL_NEW_VERSION_SUCCESS);
//    }
}
