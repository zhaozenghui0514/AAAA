package com.bwie.zhaozenghui1026.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.zhaozenghui1026.R;
import com.bwie.zhaozenghui1026.model.ZZSYBean;
import com.bwie.zhaozenghui1026.util.MImageLoadr;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class Myadatper extends BaseAdapter {

    List<ZZSYBean.ResultBean> list = new ArrayList<>();
    LayoutInflater layoutInflater;
    Context context;
    public Myadatper(Activity activity, LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
        context = activity;
    }

    public void setData(List<ZZSYBean.ResultBean> resultBeans) {
        list.clear();
        list.addAll(resultBeans);
        notifyDataSetChanged();

    }


    public void addDta(List<ZZSYBean.ResultBean> resultBeans) {
        if (resultBeans != null){
            list.addAll(resultBeans);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder vh;
        if (convertView == null){
             convertView = layoutInflater.inflate(R.layout.xlistviewdata,null);
             vh = new viewHolder(convertView);
        }else {
            vh = (viewHolder) convertView.getTag();
        }
            vh.BindData(list.get(position));
        return convertView;
    }
    class viewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;

        public viewHolder(View convertView) {
            imageView =  convertView.findViewById(R.id.imageView);
            textView1 =  convertView.findViewById(R.id.textView_name);
            textView2 =  convertView.findViewById(R.id.textView_lost);
            convertView.setTag(this);
        }

        public void BindData(ZZSYBean.ResultBean resultBean) {
            ImageLoader.getInstance().displayImage(resultBean.getImageUrl(),imageView,MImageLoadr.getDisplayImageOptions(context));
            textView1.setText(resultBean.getName());
            textView2.setText(resultBean.getSummary());
        }
    }

}
