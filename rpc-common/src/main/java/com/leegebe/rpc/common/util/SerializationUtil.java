package com.leegebe.rpc.common.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * 序列化工具类
 * Protostuff序列化过程：
 * 使用LinkedBuffer分配一块默认大小的buffer空间；
 * 通过对象的类构建对应的schema；
 * 使用给定的schema将对象序列化为一个byte数组，并返回
 * Protostuff反序列化过程：
 * 使用objenesis实例化一个类的对象；
 * 通过对象的类构建对应的schema；
 * 使用给定的schema将byte数组和对象合并，并返回
 */
public class SerializationUtil {

    private SerializationUtil(){

    }

    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj){
        Class<T> cls = (Class<T>)obj.getClass();
        LinkedBuffer linkedBuffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try{
            Schema<T> schema = (Schema<T>)RuntimeSchema.createFrom(obj.getClass());
            return ProtostuffIOUtil.toByteArray(obj, schema, linkedBuffer);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage(), e);
        }finally {
            linkedBuffer.clear();
        }
    }


}
