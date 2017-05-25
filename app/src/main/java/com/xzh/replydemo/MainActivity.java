package com.xzh.replydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* --------- 数据源----------- */
    //记录回复说说用户的集合
    private ArrayList<String> name;
    //记录被回复说说用户的集合
    private ArrayList<String> toName;
    //记录评论内容的集合
    private ArrayList<String> content;
    private NoScrollListView noScrollListView;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noScrollListView = (NoScrollListView) findViewById(R.id.listView);

        name = new ArrayList<>();
        toName = new ArrayList<>();
        content = new ArrayList<>();

        //添加数据 ,Demo只添加5条评论
        name.add("白雪公主");
        toName.add("小矮人");
        content.add("你们好啊~");

        name.add("小矮人");
        toName.add("白雪公主");
        content.add("白雪公主，早上好啊~白雪公主，早上好啊~白雪公主，早上好啊~白雪公主，早上好啊~白雪公主，早上好啊~");

        name.add("王子");
        toName.add("");
        content.add("这条说说很有道理的样子啊~");

        name.add("国王");
        toName.add("");
        content.add("我很喜欢这条说说~");

        name.add("白雪公主");
        toName.add("王子");
        content.add("你也是XX的朋友啊？");

        adapter = new CommentAdapter(name,toName,content,this);
        noScrollListView.setAdapter(adapter);

    }
}
