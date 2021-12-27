package com.newangels.gen.util;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author: 唐 亮
 * @date: 2021/12/27 23:59
 * @since: 1.0
 */
public class ListToTreeUtil {
    @FunctionalInterface
    public interface XKFunction<T, R> extends Function<T, R>, GetLamadaName {

    }

    @FunctionalInterface
    public interface XKBiConsumer<T, R> extends BiConsumer<T, R>, GetLamadaName {

    }

    @FunctionalInterface
    public interface XKPredicate<T> extends Predicate<T>, GetLamadaName {

    }

    /**
     * System.out.println(isRoot.getLamadaMethodName());
     */
    public interface GetLamadaName extends Serializable {
        String METHOD_NAME = "writeReplace";

        //todo  可用map做个缓存，但只能在循环时好用
        default String getLamadaMethodName() {
            final Class<? extends GetLamadaName> aClass = this.getClass();
            String implMethodName = null;
            try {
                final Method method = aClass.getDeclaredMethod(METHOD_NAME);
                method.setAccessible(true);
                SerializedLambda lamada = (SerializedLambda) method.invoke(this);
                implMethodName = lamada.getImplMethodName();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return implMethodName;
        }
    }

    public static <T> List<T> ListToTree(List<T> source, XKPredicate<T> isRoot, XKFunction<T, ?> idFun, XKFunction<T, ?> pidFun, XKFunction<T, List<T>> getChildFun, XKBiConsumer<T, List<T>> setChildFun) {
        if (Objects.isNull(source) || Objects.isNull(idFun) || Objects.isNull(pidFun) || Objects.isNull(getChildFun)) {
            return new ArrayList<>();
        }
        List<T> ret = new ArrayList<>();
        Map<Object, T> map = new HashMap<>();
        source.forEach(t -> {
            Optional.ofNullable(isRoot).map(r -> {
                if (isRoot.test(t)) {
                    ret.add(t);
                }
                return r;
            }).orElseGet(() -> {
                Optional.ofNullable(pidFun.apply(t)).orElseGet(() -> {
                    ret.add(t);
                    return null;
                });
                return null;
            });
            map.put(idFun.apply(t), t);
        });
        source.forEach(t -> map.computeIfPresent(pidFun.apply(t), (k, v) -> {
            Optional.ofNullable(getChildFun.apply(v)).orElseGet(() -> {
                List<T> list = new ArrayList<>();
                setChildFun.accept(v, list);
                return list;
            }).add(t);
            return v;
        }));
        return ret;
    }

    public static <T> List<T> ListToTree(List<T> source, XKFunction<T, ?> idFun, XKFunction<T, ?> pidFun, XKFunction<T, List<T>> getChildFun, XKBiConsumer<T, List<T>> setChildFun) {
        return ListToTree(source, null, idFun, pidFun, getChildFun, setChildFun);
    }

    private static <T> String getMethodName(GetLamadaName fun) {
        if (fun != null) {
            fun.getLamadaMethodName();
        }
        return null;
    }
}
