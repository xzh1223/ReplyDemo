package com.xzh.replydemo;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by zhenghangxia on 17-5-25.
 */

public class CommentAdapter extends ArrayAdapter {

    /* --------- 数据源----------- */
    //记录回复说说用户的集合
    private ArrayList<String> name;
    //记录被回复说说用户的集合
    private ArrayList<String> toName;
    //记录评论内容的集合
    private ArrayList<String> content;

    private Context context;

    public CommentAdapter(ArrayList<String> name, ArrayList<String> toName, ArrayList<String> content, Context context) {
        super(context, 0, content);
        this.name = name;
        this.toName = toName;
        this.content = content;
        this.context = context;
    }

    @Override
    public int getCount() {
        int ret = 0;
        if (name != null&&name.size()!=0)
            ret = name.size();
        return ret;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView txt_comment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //其实评论一般都是文字，高级点的带有图片评论，光文字的话复用不复用就没什么大区别了
        View view = null;
        if(convertView!=null)
        {
            view = convertView;
        }
        else
        {
            view = LayoutInflater.from(context).inflate(R.layout.listview_item, parent,false);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null)
        {
            holder = new ViewHolder();
            holder.txt_comment = (TextView) view.findViewById(R.id.txt_comment);

            view.setTag(holder);
        }
        //给相应位置的文字赋内容
        if (name != null && name.size()!=0) {
            StringBuilder actionText = new StringBuilder();

            //谁回复
            actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='#1468a3'>"
                    + name.get(position)  + "</font> </a>");

            // 回复谁，被回复的人可能不存在。
            if(toName.get(position)!=null&&toName.get(position).length()>0) {
                actionText.append("回复");
                actionText.append("<font color='#1468a3'><a style=\"text-decoration:none;\" href='toName'>"
                        + toName.get(position) + " " + " </a></font>");
            }
            // 内容
            actionText.append("<font color='#484848'><a style=\"text-decoration:none;\" href='content'>"
                    + ":" + content.get(position) + " " + " </a></font>");

            holder.txt_comment.setText(Html.fromHtml(actionText.toString()));
            holder.txt_comment.setMovementMethod(LinkMovementMethod
                    .getInstance());
            CharSequence text = holder.txt_comment.getText();
            int ends = text.length();
            Spannable spannable = (Spannable) holder.txt_comment.getText();
            URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
            SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
            stylesBuilder.clearSpans();

            for (URLSpan url : urlspan) {
                FeedTextViewURLSpan myURLSpan = new FeedTextViewURLSpan(url.getURL(),
                        context,name.get(position),toName.get(position),content.get(position));
                stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                        spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.txt_comment.setText(stylesBuilder);
            holder.txt_comment.setFocusable(false);
            holder.txt_comment.setClickable(false);
            holder.txt_comment.setLongClickable(false);

        }

        return view;
    }

    static class FeedTextViewURLSpan extends ClickableSpan {
        private String clickString;
        private Context context;
        // 回复人的名字
        private String name;
        // 被回复人的名字
        private String toName;
        // 评论内容
        private String content;

        public FeedTextViewURLSpan(String clickString, Context context, String name, String toName, String content) {
            this.clickString = clickString;
            this.context = context;
            this.name = name;
            this.toName = toName;
            this.content = content;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            //给标记的部分 的文字 添加颜色
            if(clickString.equals("toName")){
                ds.setColor(context.getResources().getColor(R.color.colorPrimary));
            }else if(clickString.equals("name")){
                ds.setColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }

        @Override
        public void onClick(View widget) {
            // 根据文字的标记 来进行相应的 响应事件
            if (clickString.equals("toName")) {
                //可以再次进行跳转activity的操作
                Toast.makeText(context,"点击了"+toName,Toast.LENGTH_SHORT).show();
            } else if (clickString.equals("name")) {
                //可以再次进行跳转activity的操作
                Toast.makeText(context,"点击了"+name,Toast.LENGTH_SHORT).show();
            } else if(clickString.equals("content")){
                //可以再次进去回复评论的操作
                Toast.makeText(context,"点击了"+content,Toast.LENGTH_SHORT).show();
            }
        }
    }


}
