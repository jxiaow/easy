package cn.xwj.easy.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;

/**
 * 集合操作工具类
 * <p>
 * Created by xw on 2017/7/20.
 */

public class CollectionUtil {
    private CollectionUtil() {
    }

    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * @param <E>        泛型
     * @return true 为空，false不为空
     */
    public <E> boolean isEmpty(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 深度复制List集合
     *
     * @param src 需要复制的集合
     * @param <T>
     * @return 返回一个全新的结合
     * @throws IOException            io异常
     * @throws ClassNotFoundException
     */
    @Nullable
    public static <T> T deepCopy(@NonNull T src) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        ByteArrayInputStream byteIn = null;
        ObjectInputStream in = null;
        try {
            out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            in = new ObjectInputStream(byteIn);
            T t = (T) in.readObject();
            in.close();
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeQuietly(in);
            closeQuietly(byteIn);
            closeQuietly(byteOut);
            closeQuietly(out);
        }
        return null;
    }

    /**
     * list去重
     *
     * @param list 需要去重复的list
     * @param <T>  泛型T
     * @return 没有重复的list
     */
    public static <T> List<T> removeDuplicatedElements(List<T> list) {
        HashSet set = new HashSet();
        ListIterator<T> tListIterator = list.listIterator();
        while (tListIterator.hasNext()) {
            T t = tListIterator.next();
            if (!set.contains(t)) {
                set.add(t);
            } else {
                tListIterator.remove();
            }
        }
        return list;
    }


    /**
     * 关闭流
     *
     * @param closeable
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
