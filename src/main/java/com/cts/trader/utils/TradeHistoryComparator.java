package com.cts.trader.utils;

import net.sf.json.JSONArray;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author lvjiawei
 * @date 2018/6/10
 * @description tradeHistory排序器
 * @version 1.0.0
 **/
public class TradeHistoryComparator implements Comparator<JSONArray> {
    /**
     * 比较顺序
     * @param o1   对象1
     * @param o2   对象2
     * @return int 结果
     */
    @Override
    public int compare(JSONArray o1, JSONArray o2) {
        String time1 = o1.getString(2);
        String time2 = o2.getString(2);

        return -time1.compareTo(time2);
    }

    public static void main(String[] args) throws Exception {
        String str1 = "[\"1.0\", \"100\", \"2017-06-08T18:00:01Z\"]";
        String str2 = "[\"2.0\", \"200\", \"2017-06-08T18:05:01Z\"]";
        JSONArray array1 = JSONArray.fromObject(str1);
        JSONArray array2 = JSONArray.fromObject(str2);

        List<JSONArray> list = new ArrayList<>();
        list.add(array1);
        list.add(array2);

        Collections.sort(list, new TradeHistoryComparator());

        JSONArray array = JSONArray.fromObject(list);
        //System.out.println(list);
        //System.out.println(array);
        //System.out.println(LocalDateTime.now(ZoneId.of("UTC")));

        String strDate = "2018-06-11 18:41:20.868706823 +0800 CST";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSS Z z");
        Date date = simpleDateFormat.parse(strDate);
        SimpleDateFormat simpleDateFormat1  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        System.out.println(simpleDateFormat1.format(date));
    }
}
