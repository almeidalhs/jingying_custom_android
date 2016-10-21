package com.atman.jixin.utils.face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.atman.jixin.R;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/7/28 13:35
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SmileUtils {

    public static SpannableString getEmotionContent( final Context context, final TextView tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\/+([\u11e00-\u9fa5]{1})";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion2 = "\\/+([A-Z\\u11e00-\\u9fa5]{2})";
        Pattern patternEmotion2 = Pattern.compile(regexEmotion2);
        Matcher matcherEmotion2 = patternEmotion2.matcher(spannableString);

        while (matcherEmotion2.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion2.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion2.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion3 = "\\/+([\u11e00-\u9fa5]{3})";
        Pattern patternEmotion3 = Pattern.compile(regexEmotion3);
        Matcher matcherEmotion3 = patternEmotion3.matcher(spannableString);

        while (matcherEmotion3.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion3.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion3.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }
        return spannableString;
    }

    public static SpannableString getEmotionContent(final Context context, final EditText tv, String source) {
        SpannableString spannableString = new SpannableString(source);
        Resources res = context.getResources();

        String regexEmotion = "\\/+([\u11e00-\u9fa5]{1})";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*11)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion2 = "\\/+([A-Z\\u11e00-\\u9fa5]{2})";
        Pattern patternEmotion2 = Pattern.compile(regexEmotion2);
        Matcher matcherEmotion2 = patternEmotion2.matcher(spannableString);

        while (matcherEmotion2.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion2.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion2.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }

        String regexEmotion3 = "\\/+([\u11e00-\u9fa5]{3})";
        Pattern patternEmotion3 = Pattern.compile(regexEmotion3);
        Matcher matcherEmotion3 = patternEmotion3.matcher(spannableString);

        while (matcherEmotion3.find()) {
            // 获取匹配到的具体字符
            String key = matcherEmotion3.group();
//            LogUtils.e("key:"+key);
            // 匹配字符串的开始位置
            int start = matcherEmotion3.start();
            // 利用表情名字获取到对应的图片
            Integer imgRes = getImgByName(key);
            if (imgRes != null && imgRes!=0) {
                // 压缩表情图片
                int size = (int) tv.getTextSize();
                size = size + (size*2)/10;
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inPreferredConfig = Bitmap.Config.RGB_565;
                Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes, opts);
                Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

                ImageSpan span = new ImageSpan(context, scaleBitmap);
                spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
            }
        }
        return spannableString;
    }

    public static boolean checkFace (Context context, String source) {

        String key = "";

        SpannableString spannableString = new SpannableString(source);
        String regexEmotion = "\\/+([\u11e00-\u9fa5]{1,3})";
        Pattern patternEmotion = Pattern.compile(regexEmotion);
        Matcher matcherEmotion = patternEmotion.matcher(spannableString);

        while (matcherEmotion.find()) {
            key = matcherEmotion.group();
        }

        return key!="";
    }

    private static Integer getImgByName(String key) {
        if (key.equals("[微笑]")) {
            return R.mipmap.face_text_1;
        } else if (key.equals("[调皮]")) {
            return R.mipmap.face_text_2;
        } else if (key.equals("[呲牙]")) {
            return R.mipmap.face_text_3;
        } else if (key.equals("[偷笑]")) {
            return R.mipmap.face_text_4;
        } else if (key.equals("[得意]")) {
            return R.mipmap.face_text_5;
        } else if (key.equals("[开心]")) {
            return R.mipmap.face_text_6;
        } else if (key.equals("[笑疯]")) {
            return R.mipmap.face_text_7;
        } else if (key.equals("[白眼]")) {
            return R.mipmap.face_text_8;
        } else if (key.equals("[鄙视]")) {
            return R.mipmap.face_text_9;
        } else if (key.equals("[傲慢]")) {
            return R.mipmap.face_text_10;
        } else if (key.equals("[发呆]")) {
            return R.mipmap.face_text_11;
        } else if (key.equals("[尴尬]")) {
            return R.mipmap.face_text_12;
        } else if (key.equals("[惊吓]")) {
            return R.mipmap.face_text_13;
        } else if (key.equals("[恐怖]")) {
            return R.mipmap.face_text_14;
        } else if (key.equals("[害羞]")) {
            return R.mipmap.face_text_15;
        } else if (key.equals("[敲打]")) {
            return R.mipmap.face_text_16;
        } else if (key.equals("[黑心]")) {
            return R.mipmap.face_text_17;
        } else if (key.equals("[大哭]")) {
            return R.mipmap.face_text_18;
        } else if (key.equals("[惊呆]")) {
            return R.mipmap.face_text_19;
        } else if (key.equals("[贪婪]")) {
            return R.mipmap.face_text_20;
        } else if (key.equals("[幸福]")) {
            return R.mipmap.face_text_21;
        } else if (key.equals("[惊讶]")) {
            return R.mipmap.face_text_22;
        } else if (key.equals("[喜欢]")) {
            return R.mipmap.face_text_23;
        } else if (key.equals("[无感]")) {
            return R.mipmap.face_text_24;
        } else if (key.equals("[冷汗]")) {
            return R.mipmap.face_text_25;
        } else if (key.equals("[超人]")) {
            return R.mipmap.face_text_26;
        } else if (key.equals("[坚强]")) {
            return R.mipmap.face_text_27;
        } else if (key.equals("[委屈]")) {
            return R.mipmap.face_text_28;
        } else if (key.equals("[愤怒]")) {
            return R.mipmap.face_text_29;
        } else if (key.equals("[坏笑]")) {
            return R.mipmap.face_text_100;
        } else if (key.equals("[色]")) {
            return R.mipmap.face_text_101;
        } else if (key.equals("[大笑]")) {
            return R.mipmap.face_text_102;
        } else if (key.equals("[哭泣]")) {
            return R.mipmap.face_text_103;
        } else if (key.equals("[忍不了]")) {
            return R.mipmap.face_text_104;
        } else if (key.equals("[面瘫]")) {
            return R.mipmap.face_text_105;
        } else if (key.equals("[不懂]")) {
            return R.mipmap.face_text_106;
        } else if (key.equals("[无奈]")) {
            return R.mipmap.face_text_107;
        } else if (key.equals("[不信]")) {
            return R.mipmap.face_text_108;
        } else if (key.equals("[腼腆]")) {
            return R.mipmap.face_text_109;
        } else if (key.equals("[摇滚]")) {
            return R.mipmap.face_text_110;
        } else if (key.equals("[羞耻]")) {
            return R.mipmap.face_text_111;
        } else if (key.equals("[叹息]")) {
            return R.mipmap.face_text_112;
        } else if (key.equals("[笑笑]")) {
            return R.mipmap.face_text_113;
        } else if (key.equals("[惊喜]")) {
            return R.mipmap.face_text_114;
        } else if (key.equals("[魔鬼]")) {
            return R.mipmap.face_text_115;
        } else if (key.equals("[鼻涕]")) {
            return R.mipmap.face_text_116;
        } else if (key.equals("[不开心]")) {
            return R.mipmap.face_text_117;
        } else if (key.equals("[疑问]")) {
            return R.mipmap.face_text_118;
        } else if (key.equals("[邪恶]")) {
            return R.mipmap.face_text_119;
        } else if (key.equals("[爱心]")) {
            return R.mipmap.face_text_120;
        } else {
            return 0;
        }
    }
}
