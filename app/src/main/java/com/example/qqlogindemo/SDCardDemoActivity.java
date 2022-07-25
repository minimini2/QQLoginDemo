package com.example.qqlogindemo;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SDCardDemoActivity extends Activity implements View.OnClickListener {

    private Button writeDataBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置布局
        setContentView(R.layout.activity_sd_card);
        checkNeedPermissions();

        //找到控件
        writeDataBtn = this.findViewById(R.id.write_data_2_sd_card_btn);
        //设置点击事件
        writeDataBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == writeDataBtn){
            //写数据到sd卡上面
//            File filePath = new File("/mnt/sdcard");
            File file = new File("/mnt/sdcard/info.txt");


            try {
//                fos = new FileOutputStream(file);
                FileOutputStream fos = new FileOutputStream(file);

                //以特定的格式来存储
                //例如：我们的账号***我们的密码
                fos.write(("accountText passwordText").getBytes());

                fos.close();
//                Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void checkNeedPermissions(){
        //6.0以上需要动态申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //多个权限一起申请
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }
    }

}
