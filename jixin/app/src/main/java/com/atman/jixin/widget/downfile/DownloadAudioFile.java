package com.atman.jixin.widget.downfile;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.atman.jixin.model.response.ConfigModel;
import com.atman.jixin.utils.FileUtils;
import com.base.baselibs.util.LogUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/5/5 11:54
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class DownloadAudioFile extends AsyncTask<String, Void, String> {
    private Context context;
    private onDownInterface down;

    public DownloadAudioFile(Context context, onDownInterface down){
        this.context = context;
        this.down = down;
    }

    public static String getFileName(String pathandname){

        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,end);
        }else{
            return null;
        }

    }

    @Override
    protected String doInBackground(String... params) {
        String _urlStr = params[0];
        String newFilename = getFileName(_urlStr);
        String path = FileUtils.SDPATH_AUDIO + newFilename + ".aac";
        LogUtils.e(">>>>:"+path);
        File file = new File(path);
        File f = new File(FileUtils.SDPATH_AUDIO);
        //如果目标文件不存在，则下载
        if(!f.exists()) {
            f.mkdirs();
        }
        try {
            FileUtils.createSDFileDir("");
            // 构造URL
            URL url = new URL(_urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            System.out.println(">>>>长度 :"+contentLength);
            // 输入流
            InputStream is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流
            OutputStream os = new FileOutputStream(path);
            // 开始读取
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            // 完毕，关闭所有链接
            os.close();
            is.close();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onPostExecute(String path) {
        super.onPostExecute(path);
        if (path.isEmpty()) {
            down.error();
        } else {
            down.finish(path);
        }
    }

    public interface onDownInterface {
        void finish(String path);
        void error();
    }
}
