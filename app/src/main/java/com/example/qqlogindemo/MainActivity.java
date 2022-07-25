package com.example.qqlogindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private File fileDir;
    private File cacheDir;
    private String info;
    private String[] splits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一步：找到控件
        initView();

        //第二步：给登录按钮设置点击事件
        initListener();
    }

    /**
     * 这个方法就是给我们的按钮设置监听
     */
    private void initListener() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "点击了登录按钮...");
                handlerLoginEvent(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        fileDir = this.getFilesDir();
//        File saveFile = new File(fileDir, "info.txt");

        try {
            FileInputStream fileInputStream = this.openFileInput("info.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            info = bufferedReader.readLine();

            //fos.write((accountText + "***" + passwordText).getBytes());
            //上面这行是之前保存的数据形式，也就是说我们拿到数据以后要进行切割。
            splits = info.split("\\*\\*\\*");        //正则表达式中的转义字符"\\"
            String account = splits[0];
            String password = splits[1];

            //回显数据
            mAccount.setText(account);
            mPassword.setText(password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理登录事件
     *
     * @param v
     */
    private void handlerLoginEvent(View v) {

        //第三步：我们要拿到界面上的内容，包括账号和密码
        //账号
        String accountText = mAccount.getText().toString();
        //密码
        String passwordText = mPassword.getText().toString();

        //对帐号进行检查。在实际开发中，我们需要对用户的账号进行合法性检查。比如说：账号的长度、账号有没有敏感词。
        //密码也一样，一般来说只对密码的复杂度进行检查。
        //这里只对密码和账号是否为空进行检查。
//        if (accountText.length() == 0){
//            //判断账号为空
//            Toast.makeText(this,"你的账号为空",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (passwordText.length()==0) {
//            //判断密码为空
//            Toast.makeText(this,"你的密码为空",Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (TextUtils.isEmpty(accountText)) {
            Toast.makeText(this, "你的账号为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passwordText)) {
            Toast.makeText(this, "你的密码为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //把账号和密码保存起来
        saveUserInfo(accountText, passwordText);

    }

    /**
     * 用于账号和密码的保存
     *
     * @param accountText
     * @param passwordText
     */
    private void saveUserInfo(String accountText, String passwordText) {

        Log.d(TAG, "保存用户信息...");

        /**
         * 怎么获取到文件保存的路径?/data/data/com.example.qqlogindemo/files
         * 输出结果：fileDir = /data/data/com.example.qqlogindemo/files
         * 也就是说getFilesDir()这个方法拿到的是/data/data/包名/files这个路径
         */
        fileDir = this.getFilesDir();
        File saveFile = new File(fileDir, "info.txt");
        Log.d(TAG, "fileDir = " + fileDir.toString());

        /**
         * 获取到缓存文件的存储路径
         * D/MainActivity: fileDir = /data/user/0/com.example.qqlogindemo/files
         * 上面这个路径是我们用于保存文件的，怎么样我们才可以清理呢？
         * 我们可以通过代码删除，也可以通过设置里的应用列表里的选项进行清理。
         * D/MainActivity: cacheDir = /data/user/0/com.example.qqlogindemo/cache
         * 上面这个路径是一个缓存路径，用于保存缓存文件，这个目录下的文件会由系统根据存储情况进行清理
         * 假设说不够用了，那么会清理。
         */
        cacheDir = this.getCacheDir();
        Log.d(TAG, "cacheDir = " + cacheDir);

        try {
//            File file = new File("/data/data/com.example.qqlogindemo/info.txt");
//
//            if (!file.exists()) {
//
//                file.createNewFile();
//
//            }

            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }

//            FileOutputStream fos = new FileOutputStream(file);
            FileOutputStream fos = new FileOutputStream(saveFile);
            //以特定的格式来存储
            //例如：我们的账号***我们的密码
            fos.write((accountText + "***" + passwordText).getBytes());

            fos.close();
            Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 这个方法我们用来找到对应的控件
     */
    private void initView() {

        mAccount = this.findViewById(R.id.et_account);
        mPassword = this.findViewById(R.id.et_password);
        mLogin = this.findViewById(R.id.bt_login);

    }

}