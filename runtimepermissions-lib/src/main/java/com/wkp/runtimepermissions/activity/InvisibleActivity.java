package com.wkp.runtimepermissions.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.wkp.runtimepermissions.BuildConfig;
import com.wkp.runtimepermissions.callback.PermissionCallBack;
import com.wkp.runtimepermissions.util.RuntimePermissionUtil;
import com.wkp.runtimepermissions.util.SPUtils;

@RequiresApi(api = Build.VERSION_CODES.M)
public class InvisibleActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1998;
    private static final String KEY_VERSION_CODE = "version_code";
    private String mPermission;
    private PermissionCallBack mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPermission = getIntent().getStringExtra(RuntimePermissionUtil.KEY_PERMISSION);
        mCallBack = RuntimePermissionUtil.getCallBack();
        ActivityCompat.requestPermissions(this, mPermission.split(RuntimePermissionUtil.FLAG_CONNECTION), REQUEST_PERMISSION_CODE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mCallBack.onCheckPermissionResult(hasPermission(this, mPermission));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    mCallBack.onCheckPermissionResult(true);
                    finish();
                    return;
                }
            }
            boolean rationale = false;
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    rationale = true;
                    break;
                }
            }
            if (rationale) {
                mCallBack.onCheckPermissionResult(false);
                finish();
            } else {
                if (SPUtils.getInt(this, KEY_VERSION_CODE, 0) < BuildConfig.VERSION_CODE) {
                    SPUtils.put(this, KEY_VERSION_CODE, BuildConfig.VERSION_CODE);
                    mCallBack.onCheckPermissionResult(false);
                    finish();
                    return;
                }
                startAppInfoActivity();
//                    switch (Build.BRAND) {
//                        case "HUAWEI":
//                            startHuaWeiPermissionActivity();
//                            break;
//                        case "Meizu":
//                            startMeiZuPermissionActivity();
//                            break;
//                        case "Xiaomi":
//                            startXiaoMiPermissionActivity();
//                            break;
//                        case "Sony":
//                            startSonyPermissionActivity();
//                            break;
//                        case "OPPO":
//                            startOPPOPermissionActivity();
//                            break;
//                        case "LG":
//                            startLGPermissionActivity();
//                            break;
//                        case "Letv":
//                            startLetvPermissionActivity();
//                            break;
//                        case "Qiku":
//                            start360PermissionActivity();
//                            break;
//                        default:
//                            startAppInfoActivity();
//                            break;
//                    }
            }
        }
    }

    /**
     * 跳转应用程序信息设置界面
     */
    private void startAppInfoActivity() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(localIntent);
    }

    /**
     * 判断权限
     *
     * @param context
     * @param permission
     * @return
     */
    private boolean hasPermission(Context context, String permission) {
        String[] permissions = permission.split(RuntimePermissionUtil.FLAG_CONNECTION);
        for (String s : permissions) {
            if (ContextCompat.checkSelfPermission(context, s) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 跳转华为手机权限管理界面 HUAWEI
     */
    private void startHuaWeiPermissionActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        startActivity(intent);
    }

    /**
     * 跳转魅族手机权限管理界面 Meizu
     */
    private void startMeiZuPermissionActivity() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        startActivity(intent);
    }

    /**
     * 跳转小米手机权限管理界面 Xiaomi
     */
    private void startXiaoMiPermissionActivity() {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
        startActivity(intent);
    }

    /**
     * 跳转索尼手机权限管理界面 Sony
     */
    private void startSonyPermissionActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
        startActivity(intent);
    }

    /**
     * 跳转OPPO手机权限管理界面 OPPO
     */
    private void startOPPOPermissionActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        startActivity(intent);
    }

    /**
     * 跳转LG手机权限管理界面 LG
     */
    private void startLGPermissionActivity() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        startActivity(intent);
    }

    /**
     * 跳转乐视手机权限管理界面 Letv
     */
    private void startLetvPermissionActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
    }

    /**
     * 跳转360手机权限管理界面 Qiku
     */
    private void start360PermissionActivity() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
        startActivity(intent);
    }

    /**
     * 跳转本应用权限管理界面
     */
    private void startManagePermissionsActivity() {
        // start new activity to manage app permissions
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.putExtra(Intent.EXTRA_PACKAGE_NAME, getPackageName());
        intent.putExtra("hideInfoButton", true);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            startManagePermissionsActivity();
        }
    }
}
