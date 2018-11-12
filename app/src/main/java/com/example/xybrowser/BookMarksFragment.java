package com.example.xybrowser;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by xy on 2017/4/10.
 */

public class BookMarksFragment extends Fragment {
    private ListView listView;
    private SimpleAdapter list_simp_adapter;
    private List<Map<String, Object>> list_datalist;
    private SQLiteDatabase db;
    private SQLDatabaseHelper dbHelper;
    private AutoCompleteTextView website;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View bookmarksview = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        ListView listView = (ListView) bookmarksview.findViewById(R.id.lv_bookmark);
        website = (AutoCompleteTextView) getActivity().findViewById(R.id.actv_website);


        list_datalist = new ArrayList<Map<String, Object>>();
        list_simp_adapter = new SimpleAdapter(getContext(), getListViewData(), R.layout.item_bookmark,
                new String[]{"pic", "name", "chinese"}, new int[]{R.id.imv_bookmarks, R.id.tv_bookmarks, R.id.tv_bookchinese});
        list_simp_adapter.notifyDataSetChanged();
        listView.setAdapter(list_simp_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str_url = list_datalist.get(position).get("name").toString();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("id", 1);
                intent.putExtra("url", str_url);
                startActivity(intent);
                Toast.makeText(getContext(), "" + str_url, Toast.LENGTH_SHORT).show();
            }
        });


        return bookmarksview;
    }

    private List<Map<String, Object>> getListViewData() {
        list_datalist = new ArrayList<>();
        dbHelper = new SQLDatabaseHelper(getContext(), "Bookmark.db", null, 1);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Bookmarks", null, null, null, null, null, null);
        Map<String, Object> listmap;
        cursor.moveToFirst();
        do {
            listmap = new HashMap<>();
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String chinese = cursor.getString(cursor.getColumnIndex("chinese"));

            listmap.put("pic", R.mipmap.bookmarks);
            listmap.put("name", url);
            listmap.put("chinese", chinese + ":");
            list_datalist.add(listmap);
        } while (cursor.moveToNext());

        cursor.close();
        return list_datalist;
    }
}






