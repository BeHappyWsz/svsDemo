package wsz.redis.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description: 通用工具类
 * @author: WSZ
 * @create: 2019-04-29 09:18
 **/
public class CommonUtils {

    private static ThreadLocal<DateFormat> timeFormat = new ThreadLocal<DateFormat>(){
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private static ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>(){
        protected DateFormat initialValue() {
            return new SimpleDateFormat("/yyyy/MM/dd/");
        }
    };

    public static String getNowDate(){
        return timeFormat.get().format(new Date());
    }

    public static String getDateString(){
        return dateFormat.get().format(new Date());
    }
}