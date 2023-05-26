package com.example.androidfinalwork.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidfinalwork.R;

import java.util.LinkedList;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private List<String> chatMessage;
    private LayoutInflater inflater;
    class ViewHolder {
        TextView messageTextView;
    }
    public ChatAdapter(List<String>chatMessage, Context context){
        this.chatMessage=chatMessage;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
       return chatMessage.size();
    }

    @Override
    public Object getItem(int i) {
      return chatMessage.get(i);
    }

    @Override
    public long getItemId(int i) {
         return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if(view==null){
            view=this.inflater.inflate(R.layout.list_view,viewGroup,false);
            holder=new ViewHolder();
            holder.messageTextView=view.findViewById(R.id.context);
            view.setTag(holder);
        }
        else{
            holder=(ViewHolder)view.getTag();
        }
        String message=chatMessage.get(i);
        holder.messageTextView.setText(message);
        return view;

    }
}
