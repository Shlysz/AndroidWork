package com.example.androidfinalwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidfinalwork.Adapter.ChatAdapter;
import com.example.androidfinalwork.Factory.Gfactory;
import com.example.androidfinalwork.entity.SendKey;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class IndexActivity extends AppCompatActivity {

    private TextView sendText;
    private Button sendButton;
    private ListView chatList;
    private String key;
    private List<String> chatMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        sendText=findViewById(R.id.inputEditText);
        sendButton=findViewById(R.id.sendButton);
        chatList=findViewById(R.id.chatListView);
        chatMessage=new ArrayList<>();
        ChatAdapter chatAdapter = new ChatAdapter(chatMessage, this);
        chatList.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message=sendText.getText().toString();

                chatMessage.add(message);
                chatAdapter.notifyDataSetChanged();
                //禁用发送键
                sendButton.setEnabled(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        key= SendKey.key;
                        //发送消息到服务器
                        String response = Gfactory.getUrlService().PostResponse(key,message);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chatMessage.add(response);
                                //启用发送键
                                sendButton.setEnabled(true);
                                chatAdapter.notifyDataSetChanged();
                                sendText.setText("");
                            }
                        });
                    }
                }).start();
            }
        });

    }
}