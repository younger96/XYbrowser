package com.example.xybrowser;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * Created by xy on 2017/4/6.
 */

public class WebFragment extends Fragment implements View.OnClickListener {

    private WebView webview;//声明
    private AutoCompleteTextView website;
    private View back;
    private View forward;
    private View refresh;
    private View homepage;
    private ImageButton error;
    private TextView errortext;
    private ProgressDialog pd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);


        webview = (WebView) view.findViewById(R.id.wb_web);  //绑定  控件得绑定  方法得实例化
        website = (AutoCompleteTextView) getActivity().findViewById(R.id.actv_website);
        back = getActivity().findViewById(R.id.bt_back);
        forward = getActivity().findViewById(R.id.bt_forward);
        refresh = getActivity().findViewById(R.id.bt_refresh);
        homepage = getActivity().findViewById(R.id.bt_homepage);

        errortext = (TextView) view.findViewById(R.id.tv_error);
        error = (ImageButton) view.findViewById(R.id.imb_error);


        //设置监听事件
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        refresh.setOnClickListener(this);
        error.setOnClickListener(this);
        homepage.setOnClickListener(this);


        //WebView
        String str_website = website.getText().toString();
        if (str_website.indexOf("http://") == -1) {
            str_website = "http://" + str_website;
        }
        if (str_website.indexOf(".com") == -1) {
            str_website = str_website + ".com";
        }
        webview.loadUrl(str_website);
        webview.requestFocus();
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //在WebView中使用Javascript
        webview.getSettings().setJavaScriptEnabled(true);
        //webView优化缓存
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        //网页出错
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_CONNECT || errorCode == ERROR_TIMEOUT)

                    //设置可见性
                    webview.setVisibility(View.GONE);
                errortext.setVisibility(View.VISIBLE);
            }
        });


        //进度条
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    closeDialog();
                    Log.i("TAG", "close dialog");
                } else {
                    openDialog(newProgress);
                    Log.i("TAG", "open dialog");
                }
            }

            public void closeDialog() {
                if (pd != null) {
                    if (pd.isShowing()) {
                        pd.dismiss();
                        pd = null;
                    }
                }
            }

            public void openDialog(int newProgress) {
                if (pd == null) {
                    pd = new ProgressDialog(getContext());
                    pd.setTitle("正在加载");
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.setProgress(newProgress);
                    pd.setCancelable(false);
                    pd.show();
                    website.setText(webview.getUrl());
                } else {
                    pd.setProgress(newProgress);
                }
            }
        });
        return view;
    }


    @Override
    public void onClick(View v) {
        //根据点击不同的按钮调用不同的方法
        switch (v.getId()) {
            case R.id.bt_back:
                Back();
                break;
            case R.id.bt_forward:
                Forward();
                break;
            case R.id.bt_refresh:
                Log.i("tag", "123");
                webview.reload();
                break;
            case R.id.imb_error:
                errortext.setVisibility(View.GONE);
                webview.setVisibility(View.VISIBLE);
                webview.reload();
                break;
            case R.id.bt_homepage:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HomepageFragment homepageFragment = new HomepageFragment();
                ft.addToBackStack(null);
                ft.replace(R.id.fm_fragment, homepageFragment);
                website.setText("");
                ft.commit();
                break;
        }
    }

    private void Back() {
        if (webview.canGoBack()) {
            webview.goBack();
        }
    }

    private void Forward() {
        if (webview.canGoForward()) {
            webview.goForward();
        } else {
            Toast.makeText(getContext(), "当前已是最后一页", Toast.LENGTH_SHORT).show();
        }
    }


}
