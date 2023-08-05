package com.hang.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BeanCopyUtils
 * @Description 泛型
 * @Author QiuLiHang
 * @DATE 2023/8/4 18:51
 * @Version 1.0
 */

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    /**
     * 减少了自己创建的步骤
     * @param source 要拷贝属性的源对象
     * @param clazz  目标对象的类类型，用于创建目标对象实例(字节码对象)
     * @return       目标对象实例，其属性已经拷贝了源对象的属性
     * @param <V>
     */
    public static <V> V copyBean(Object source,Class<V> clazz) {
        //创建目标对象
        V result = null;
        try {
            // 该方法首先使用Java的反射机制创建一个目标对象
            result = clazz.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    /**
     *
     * @param list  源对象列表，其中每个元素都是源对象
     * @param clazz 目标对象的类类型，用于创建目标对象实例
     * @return      包含目标对象的新列表
     * @param <O>
     * @param <V>
     */
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
