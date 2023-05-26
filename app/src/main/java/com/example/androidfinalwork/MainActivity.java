package com.example.androidfinalwork;

import android.util.Log;
import com.example.androidfinalwork.Service.UrlService;
import com.example.androidfinalwork.entity.Account;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {
    private Button submitButton;
    private TextView username;
    private TextView password;
    //初始化volley的队列

    //private final UrlService urlService = new UrlService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取button
        submitButton=findViewById(R.id.loginButton);
        //获取输入框
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);

        UrlService LoginService = UrlService.getOKhttpService();

        //设置监听器
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("firstclick", "onClick: firstclick");
                //获取输入框的值
                String usernameValue=username.getText().toString();
                String passwordValue=password.getText().toString();
                //判断输入框的值是否正确
                //封装username和password为json数据 发送到服务器验证
                Account account = new Account();
                account.setUsername(usernameValue);
                account.setPassword(passwordValue);
                Log.d("click", "onClick: "+account.toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = LoginService.PostLogin(account);
                        if(result){
                            //跳转到index页面
                            Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).start();
            }
        });
    }


}

