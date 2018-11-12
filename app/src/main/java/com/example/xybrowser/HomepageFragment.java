package com.example.xybrowser;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by xy on 2017/4/6.
 */

public class HomepageFragment extends Fragment {

    private AutoCompleteTextView website;
    private Button search;
    private GridView homepage;
    private SimpleAdapter simadapter;
    private List<Map<String, Object>> datalist;
    private int[] icon = {R.drawable.baidu, R.drawable.sogo, R.drawable.souhu, R.drawable.neteasy, R.drawable.fenghuangwang, R.drawable.tencent, R.drawable.sina, R.drawable.ctrip, R.drawable.youku, R.drawable.iqiyi, R.drawable.taobao, R.drawable.amazon, R.drawable.bilibili, R.drawable.koreandramas};
    private String[] iconname = {"百度", "搜狗", "搜狐", "网易", "凤凰网", "腾讯", "新浪", "携程", "优酷", "爱奇艺", "淘宝", "亚马逊", "哔哩哔哩", "韩剧网"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, container, false);


        //主页GridView和SimpleAdapter及数据源
        homepage = (GridView) view.findViewById(R.id.gv_Homepage);
        datalist = new ArrayList<Map<String, Object>>();
        simadapter = new SimpleAdapter(getActivity(), getData(), R.layout.item_homepage, new String[]{"image", "text"}, new int[]{R.id.imv_imageView, R.id.tv_text});
        homepage.setAdapter(simadapter);


        //为主页GridView设置监听器
        homepage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replaceFragment(new WebFragment());
                AutoCompleteTextView website = (AutoCompleteTextView) getActivity().findViewById(R.id.actv_website);
                switch (position) {
                    case 0:
                        website.setText(R.string.baidu);
                        Log.d(TAG, "1111 ");
                        break;
                    case 1:
                        website.setText(R.string.sougou);
                        break;
                    case 2:
                        website.setText(R.string.souhu);
                        break;
                    case 3:
                        website.setText(R.string.neteasy);
                        break;
                    case 4:
                        website.setText(R.string.fenghuangwang);
                        break;
                    case 5:
                        website.setText(R.string.tencent);
                        break;
                    case 6:
                        website.setText(R.string.sina);
                        break;
                    case 7:
                        website.setText(R.string.ctrip);
                        break;
                    case 8:
                        website.setText(R.string.youku);
                        break;
                    case 9:
                        website.setText(R.string.iqiyi);
                        break;
                    case 10:
                        website.setText(R.string.taobao);
                        break;
                    case 11:
                        website.setText(R.string.amazon);
                        break;
                    case 12:
                        website.setText(R.string.bilibili);
                        break;
                    case 13:
                        website.setText(R.string.koreandramas);
                        break;
                }
            }
        });
        search = (Button) getActivity().findViewById(R.id.bt_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new WebFragment());
            }
        });
        return view;
    }

    //数据源
    private List<Map<String, Object>> getData() {
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconname[i]);
            datalist.add(map);
        }
        return datalist;
    }

    //动态替换碎片
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fm_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


}

