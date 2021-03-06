# RuntimePermission [ ![Download](https://api.bintray.com/packages/wkp/maven/RuntimePermission/images/download.svg) ](https://bintray.com/wkp/maven/RuntimePermission/_latestVersion)
Android6.0动态权限管理库，回调模式管理动态权限，无需关注申请权限各种状态，只用关心回调是否有权限，然后做不同逻辑处理！
## 演示图
![DragGridView](https://github.com/wkp111/RuntimePermission/blob/master/RuntimePermission.gif "演示图")
## Gradle集成
```groovy

dependencies{
      compile 'com.wkp:RuntimePermission:1.0.6'
      //Android Studio3.0+可用以下方式
      //implementation 'com.wkp:RuntimePermission:1.0.6'
}
```
Note：可能存在Jcenter还在审核阶段，这时会集成失败！
<br>
<br>
## 使用举例
申请CAMERA权限<br><br>
1.manifest配置<br>
```xml
    <!--权限配置-->
    <uses-permission android:name="android.permission.CAMERA"/>
```
Note：举例权限，根据自己需求选取！
<br>
<br>
2.代码示例<br>
```java
//api 23
@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 点击获取权限
     * @param view
     */
    public void applyPermission(final View view) {
        //权限检查，回调是权限申请结果
        RuntimePermissionUtil.checkPermissions(this, RuntimePermissionUtil.CAMERA, new PermissionCallBack() {
            @Override
            public void onCheckPermissionResult(boolean hasPermission) {
                if (hasPermission) {
                    //直接做具有权限后的操作
                    Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
                    ((TextView) view).setText("权限申请成功");
                }else {
                    //显示权限不具备的界面
                    Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
                    ((TextView) view).setText("权限申请失败");
                }
            }
        });
    }
}
```
Note：权限申请采用回调模式，不用在意Android版本，内置判断所有，总是有回调，低版本默认true！
<br/>
## 寄语<br/>
控件支持直接代码创建，还有更多API请观看<a href="https://github.com/wkp111/RuntimePermission/blob/master/runtimepermissions-lib/src/main/java/com/wkp/runtimepermissions/util/RuntimePermissionUtil.java">RuntimePermissionUtil.java</a>内的注释说明。<br/>
欢迎大家使用，感觉好用请给个Star鼓励一下，谢谢！<br/>
大家如果有更好的意见或建议以及好的灵感，请邮箱作者，谢谢！<br/>
QQ邮箱：1535514884@qq.com<br/>
163邮箱：15889686524@163.com<br/>
Gmail邮箱：wkp15889686524@gmail.com<br/>

## 版本更新<br/>
* v1.0.6<br/>
修复多权限申请时部分权限不显示BUG<br/><br/>
* v1.0.5<br/>
内置透明界面移入库中，方便操作<br/><br/>
* v1.0.4<br/>
改善内置界面显示效果，达到透明效果<br/><br/>
* v1.0.3<br/>
加入内置权限申请界面<br/><br/>
* v1.0.2<br/>
新创建Android6.0动态权限管理库<br/>
## License

   Copyright 2017 wkp

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

