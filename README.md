# RuntimePermission
动态权限管理库
<br>
<br>
## Gradle集成
compile 'com.wkp:RuntimePermission:1.0.4'
<br>
<br>
## 使用举例
申请CAMERA权限<br><br>
1.manifest配置<br>
\<!--权限配置--><br>
`<uses-permission android:name="android.permission.CAMERA"/>`<br>
\<!--动态权限申请界面--><br>
`<activity android:name="com.wkp.runtimepermissions.activity.InvisibleActivity" android:theme="@style/InvisibleThem"/>`
<br>
<br>
2.代码示例<br>
<code>//api 23

@RequiresApi(api = Build.VERSION_CODES.M)

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //权限检查，回调是权限申请结果
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.CAMERA, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    //直接做具有权限后的操作
                    Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
                }else {
                    //显示权限不具备的界面
                    Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}</code>
