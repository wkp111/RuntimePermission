package com.wkp.runtimepermissions.util;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringDef;
import android.support.v4.content.ContextCompat;

import com.wkp.runtimepermissions.activity.InvisibleActivity;
import com.wkp.runtimepermissions.callback.PermissionCallBack;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by user on 2017/7/24.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class RuntimePermissionUtil {
    /**
     * READ_CALENDAR , WRITE_CALENDAR
     */
    public static final String CALENDAR = Manifest.permission_group.CALENDAR;
    /**
     * CAMERA
     */
    public static final String CAMERA = Manifest.permission_group.CAMERA;
    /**
     * READ_CONTACTS , WRITE_CONTACTS , GET_ACCOUNTS
     */
    public static final String CONTACTS = Manifest.permission_group.CONTACTS;
    /**
     * ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION
     */
    public static final String LOCATION = Manifest.permission_group.LOCATION;
    /**
     * RECORD_AUDIO
     */
    public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
    /**
     * READ_PHONE_STATE ,CALL_PHONE, READ_CALL_LOG, WRITE_CALL_LOG, ADD_VOICEMAIL, USE_SIP, PROCESS_OUTGOING_CALLS
     */
    public static final String PHONE = Manifest.permission_group.PHONE;
    /**
     * BODY_SENSORS
     */
    public static final String SENSORS = Manifest.permission_group.SENSORS;
    /**
     * SEND_SMS ,RECEIVE_SMS ,READ_SMS, RECEIVE_WAP_PUSH, RECEIVE_MMS
     */
    public static final String SMS = Manifest.permission_group.SMS;
    /**
     * READ_EXTERNAL_STORAGE ,WRITE_EXTERNAL_STORAGE
     */
    public static final String STORAGE = Manifest.permission_group.STORAGE;

    public static final String KEY_PERMISSION = "permission";
    public static final String KEY_CALL_BACK = "call_back";
    private static PermissionCallBack sCallBack;

    @StringDef({CALENDAR,CAMERA,CONTACTS,LOCATION,MICROPHONE,PHONE,SENSORS,SMS,STORAGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionGroup {}

    /**
     * 检测动态权限
     * @param context
     * @param permissionGroup
     * @param callBack
     */
    public static void checkPermissions(Context context, @PermissionGroup String permissionGroup, PermissionCallBack callBack) {
        sCallBack = callBack;
        if (hasPermission(context, getPermission(permissionGroup))) {
            callBack.onCheckPermissionResult(true);
        } else {
            Intent intent = new Intent(context, InvisibleActivity.class);
            intent.putExtra(KEY_PERMISSION, getPermission(permissionGroup));
            context.startActivity(intent);
        }
    }

    /**
     * 获取回调
     * @return
     */
    public static PermissionCallBack getCallBack() {
        return sCallBack;
    }

    /**
     * 判断权限
     * @param context
     * @param permission
     * @return
     */
    private static boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限
     * @param permissionGroup
     * @return
     */
    private static String getPermission(@PermissionGroup String permissionGroup) {
        String permission = null;
        switch (permissionGroup) {
            case CALENDAR:
                permission = Manifest.permission.READ_CALENDAR;
                break;
            case CAMERA:
                permission = Manifest.permission.CAMERA;
                break;
            case CONTACTS:
                permission = Manifest.permission.READ_CONTACTS;
                break;
            case LOCATION:
                permission = Manifest.permission.ACCESS_FINE_LOCATION;
                break;
            case MICROPHONE:
                permission = Manifest.permission.RECORD_AUDIO;
                break;
            case PHONE:
                permission = Manifest.permission.READ_PHONE_STATE;
                break;
            case SENSORS:
                permission = Manifest.permission.BODY_SENSORS;
                break;
            case SMS:
                permission = Manifest.permission.READ_SMS;
                break;
            case STORAGE:
                permission = Manifest.permission.READ_EXTERNAL_STORAGE;
                break;
        }
        return permission;
    }
}
