package com.bwie.zhaozenghui1026.ui.fragment;



import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bwie.zhaozenghui1026.R;
import com.bwie.zhaozenghui1026.adapter.Myadatper;
import com.bwie.zhaozenghui1026.model.ZZSYBean;
import com.bwie.zhaozenghui1026.util.MyHttpUtil;
import com.google.gson.Gson;

import java.util.List;

import me.maxwin.view.XListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentJJSY extends Fragment {

    private View view;
    private XListView xListView;
    int page = 1;
    private Myadatper myadatper;


    public FragmentJJSY() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragment_jjsy, container, false);
        xListView = view.findViewById(R.id.xListView_jjsy);
        myadatper = new Myadatper(getActivity(),getActivity().getLayoutInflater());
        if (MyHttpUtil.HasNewWork(getActivity())){
            questData(page);
        }else {
            Toast.makeText(getActivity(),"没有网络",Toast.LENGTH_SHORT).show();
        }


        xListView.setPullRefreshEnable(true);
        xListView.setPullLoadEnable(true);

        xListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                questData(page);
            }

            @Override
            public void onLoadMore() {
                page++;
                questData(page);
            }
        });
        return view;
    }
    private void questData(int page) {
        String s = "http://172.17.8.100/movieApi/movie/v1/findHotMovieList?count=10&page="+page;
        AsyncTask<String,Void,List<ZZSYBean.ResultBean>> asyncTask = new AsyncTask<String, Void, List<ZZSYBean.ResultBean>>() {
            @Override
            protected List<ZZSYBean.ResultBean> doInBackground(String... strings) {
                //  List<ZZSYBean.ResultBean> x = MyHttpUtil.getGson(strings[0], ZZSYBean.class);
                String http = MyHttpUtil.getHttp(strings[0]);
                ZZSYBean zzsyBean = new Gson().fromJson(http, ZZSYBean.class);
                List<ZZSYBean.ResultBean> result = zzsyBean.getResult();
                return result;
            }

            @Override
            protected void onPostExecute(List<ZZSYBean.ResultBean> resultBeans) {
                super.onPostExecute(resultBeans);

                setUpate(resultBeans);
                setRefresh();

                xListView.setAdapter(myadatper);

            }
        }.execute(s);
    }

    private void setRefresh() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
    }

    private void setUpate(List<ZZSYBean.ResultBean> resultBeans) {
        if (resultBeans.size() == 0){
            Toast.makeText(getActivity(),"没有更多数据",Toast.LENGTH_SHORT).show();
            xListView.setPullLoadEnable(false);
        }
        if (page == 1){
            myadatper.setData(resultBeans);
        }else {
            myadatper.addDta(resultBeans);
        }

    }

}
