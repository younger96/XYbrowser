package com.example.xybrowser;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActionMenuView.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.xybrowser.base.BaseFragmentActivity;
import com.example.xybrowser.sq.SQLDatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;


public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Fragment homepagefragment;
    private ListView listMenu;
    private SimpleAdapter menu_simadapter;
    private List<Map<String, Object>> menu_datalist;
    private int[] menu_icon = {R.mipmap.setting, R.drawable.share, R.drawable.download, R.drawable.bh, R.drawable.add, R.drawable.exit};
    private String[] menu_iconname = {"设置", "分享", "下载", "书签历史", "添加到", "退出"};
    private PopupWindow menu_popupwindow;
    private AutoCompleteTextView website;
    private EditText chinese;
    private SQLiteDatabase sqLiteDatabase;
    private SQLiteDatabase db;
    private SQLDatabaseHelper dbHelper;

    private Button btnMore;


    @Override
    protected void init() {
        dbHelper = new SQLDatabaseHelper(this, "Bookmark.db", null, 1);
        dbHelper.getWritableDatabase();


        //Fragment
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        HomepageFragment homepageFragment = new HomepageFragment();
        ft.add(R.id.fm_fragment, homepageFragment);
        ft.commit();

        btnMore = findViewById(R.id.btn_more);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAsDropDown();
            }
        });

        //Button
        Button menubutton = (Button) findViewById(R.id.bt_menu);

        website = (AutoCompleteTextView) findViewById(R.id.actv_website);


        //监听事件
        menubutton.setOnClickListener(this);


        WebFragment webFragment = new WebFragment();
        String url = getIntent().getStringExtra("url");
        int id = getIntent().getIntExtra("id", 0);
        if (id == 1) {
            website.setText(url);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fm_fragment, webFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    private void showAsDropDown() {
        //设置contentView  利用LayoutInflater获取R.layout.popupwindow对应的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        //用构造函数来生成menu_popupwindow
        menu_popupwindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        //Menu的GridView
        listMenu = (ListView) contentView.findViewById(R.id.list_menu_main);
        menu_datalist = new ArrayList<>();
        menu_simadapter = new SimpleAdapter(this, getMenuData(),
                R.layout.item_menu, new String[]{"image", "text"}, new int[]{R.id.img_item, R.id.tv_item
        });
        listMenu.setAdapter(menu_simadapter);


        //菜单里的GridView的点击事件  设置popupwindow里的控件点击事件
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(MainActivity.this, "此功能暂未实现", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this, "此功能暂未实现", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "此功能暂未实现", Toast.LENGTH_SHORT).show();
                        break;

                    case 3://书签历史记录
                        Intent intent = new Intent(MainActivity.this, BHActivity.class);
                        startActivity(intent);

                        break;
                    case 4:
                        if (website.getText().toString().equals("")) {
                            Toast.makeText(MainActivity.this, "网址为空，无法添加网页", Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            final LinearLayout dialog_layout = (LinearLayout) getLayoutInflater().inflate(R.layout.alertdialog, null);
                            website = (AutoCompleteTextView) findViewById(R.id.actv_website);
                            chinese = (EditText) dialog_layout.findViewById(R.id.et_AD);
                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put("url", website.getText().toString());
                                    values.put("chinese", chinese.getText().toString());
                                    Log.i("TAG", "get " + chinese.getText().toString());
                                    db.insert("Bookmarks", null, values);
                                    values.clear();
                                    Toast.makeText(MainActivity.this, "已添加至书签", LENGTH_SHORT).show();
                                }
                            });
                            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.setView(dialog_layout);
                            dialog.create();
                            dialog.show();
                            dialog.setCancelable(false);
                        }

                        break;
                    case 5://退出
                        finish();
                        break;

                }
            }
        });
        //指定popupwindow的显示控件
        menu_popupwindow.showAsDropDown(btnMore,0,0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            website.setText("");
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu:
                showAsDropDown();
                break;


        }

    }

    //Menu数据源
    private List<Map<String, Object>> getMenuData() {
        for (int i = 0; i < menu_icon.length; i++) {
            Map<String, Object> menumap = new HashMap<String, Object>();
            menumap.put("image", menu_icon[i]);
            menumap.put("text", menu_iconname[i]);
            menu_datalist.add(menumap);
        }
        return menu_datalist;

    }

}
