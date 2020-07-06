package com.coderpig.drysister;

import android.util.Log;

import com.coderpig.drysister.bean.Sister;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2020/7/6 13:02.
 **/
public class SisterApi {
    private static final String TAG = "Network";
    private static final String BASE_URL = "https://gank.io/api/data/";

    /**
     * 查询妹子信息
     */
    public ArrayList<Sister> fetchSister(int count, int page) {
        String fetchUrl = "https://gank.io/post/5e512d8107d934eade79461c";
        ArrayList<Sister> sisters = new ArrayList<>();
        try {
            String s=URLEncoder.encode("福利","UTF-8");
            fetchUrl=BASE_URL+s+"/"+count+"/"+page;

            URL url = new URL(fetchUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            Log.v(TAG, "Server response：" + fetchUrl);
            if (code == 200) {
                InputStream in = conn.getInputStream();
                byte[] data = readFromStream(in);
                String result = new String(data, "UTF-8");
                sisters = parseSister(result);
            } else {
                Log.e(TAG,"请求失败：" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sisters;
    }


    /**
     * 解析返回Json数据的方法
     */
    public ArrayList<Sister> parseSister(String content) throws Exception {
        ArrayList<Sister> sisters = new ArrayList<>();
        JSONObject object = new JSONObject(content);
        JSONArray array = object.getJSONArray("results");
        for (int i = 0; i < array.length(); i++) {
            JSONObject results = (JSONObject) array.get(i);
            Sister sister = new Sister();
            sister.set_id(results.getString("_id"));
            sister.setCreateAt(results.getString("createdAt"));
            sister.setDesc(results.getString("desc"));
            sister.setPublishedAt(results.getString("publishedAt"));
            sister.setSource(results.getString("source"));
            sister.setType(results.getString("type"));
            sister.setUrl(results.getString("url"));
            sister.setUsed(results.getBoolean("used"));
            sister.setWho(results.getString("who"));
            sisters.add(sister);
        }
        return sisters;
    }

    /**
     * 读取流中数据的方法
     */
    public byte[] readFromStream(InputStream inputStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len ;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

}
