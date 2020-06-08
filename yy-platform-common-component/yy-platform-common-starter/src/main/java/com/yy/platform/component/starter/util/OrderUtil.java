package com.yy.platform.component.starter.util;

import cn.hutool.core.util.RandomUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

/**
 * 订单相关工具类
 */
public class OrderUtil {

    public static final String YYYYMMDDHH = "yyyyMMddHH";

    /**
     * 订单编号的生成，暂时简单规则处理（订单创建时间:yyyyMMddHH+5位随机数字+会员ID的前5位)
     * 并发下会出现重复
     * @param memberId
     * @return
     */
    public static String creatOrderSn(Long memberId){
        String dateTimeStr = DateTimeFormatter.ofPattern(YYYYMMDDHH).format(LocalDateTime.now());
        String memberIdStr = String.valueOf(memberId).substring(0,5);
        String randomStr = RandomUtil.randomNumbers(5);
        return dateTimeStr + randomStr + memberIdStr;
    }

    public static void main(String[] args) {
        /*System.out.println(RandomUtil.randomInt());
        System.out.println(RandomUtil.randomNumber());
        System.out.println(RandomUtil.randomNumbers(5));
        System.out.println(DateUtil.today());
        System.out.println(DateTimeFormatter.ofPattern(YYYYMMDDHH).format(LocalDateTime.now()));
        System.out.println(DateUtil.format(new Date(), YYYYMMDDHH));
        System.out.println(DateUtil.toIntSecond(new Date()));*/

        Set<String> number = new HashSet<>();
        for(int i=0; i < 10000; i++){
            String str = RandomUtil.randomNumbers(5);
            if(number.contains(str)){
                System.out.println(str + "相同");
            }else{
                number.add(str);
            }
        }

    }

}
