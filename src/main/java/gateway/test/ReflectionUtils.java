package gateway.test;

import com.alibaba.fastjson.parser.JSONReaderScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Denim.leihz 2019-09-16 3:23 PM
 */
public class ReflectionUtils {

    private static final String READER_SCANNER_FLAG = "reader_";

    private static final ConcurrentMap<String, Field> cachedFields = new ConcurrentHashMap<>(8);
    private static final ConcurrentMap<String, Method> cachedMethods = new ConcurrentHashMap<>(8);


    public static Object getField(Object instance, String name) {
        try {
            return getField(instance.getClass(), name).get(instance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void setField(Object instance, String name, Object value) {
        try {
            getField(instance.getClass(), name).set(instance, value);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public static Method getMethod(Object objInst, String name, Class<?>... parameterTypes) {
        try {
            return getMethod(objInst.getClass(), name, parameterTypes);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static Object invoke(Method method, Object objInst, Object... args) {
        try {
            return method.invoke(objInst, args);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 优先从缓存中获取 filed 对象.
     */
    private static Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        String key = JSONReaderScanner.class.isAssignableFrom(clazz) ? READER_SCANNER_FLAG + fieldName : fieldName;
        Field field = cachedFields.get(key);
        if (field == null) {
            Field creationFiled = getDeclaredField(clazz, fieldName);

            if (creationFiled == null) {
                throw new NoSuchFieldException("No Filed " + fieldName + " in class " + clazz);
            }
            creationFiled.setAccessible(true);

            cachedFields.putIfAbsent(key, creationFiled);
            field = cachedFields.get(key);
        }

        return field;
    }

    /**
     * 优先从缓存中获取 method 对象.
     */
    private static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        String key = JSONReaderScanner.class.isAssignableFrom(clazz) ? READER_SCANNER_FLAG + methodName : methodName;
        Method method = cachedMethods.get(key);

        if (method == null) {
            Method creationMethod = getDeclaredMethod(clazz, methodName, parameterTypes);
            if (creationMethod == null) {
                throw new NoSuchMethodException("No Method " + methodName + " in class " + clazz);
            }
            creationMethod.setAccessible(true);

            cachedMethods.putIfAbsent(key, creationMethod);
            method = cachedMethods.get(key);
        }

        return method;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param clazz          : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */
    private static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Method method;

        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param clazz     : Class
     * @param fieldName : 父类中的属性名
     * @return 父类中的属性对象
     */
    private static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field;
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }
        return null;
    }
}

