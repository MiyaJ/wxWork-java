package com.ezy.common.utils;

import com.ezy.common.exception.CodeException;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author: zehao.zhang
 * @CreateDate: 2020/06/30 14:53
 * @Desc 校验必填字段
 * @Version: 1.0
 */
public class Checker<T> {

    private final List<Item<T>> checkItemList;

    public Checker(List<Item<T>> checkItemList) {
        this.checkItemList = checkItemList;
    }

    @SafeVarargs
    public static <T> Checker<T> create(Item<T>... item) {
        return new Checker<>(Arrays.asList(item));
    }

    public static <T> Item<T> notNull(String name, Function<T, ?> getter) {
        return (t, result) -> {
            Object value = null;
            try {
                value = getter.apply(t);
            } catch (NullPointerException ignored) {
            }
            if (value == null) {
                result.addNullField(name);
            }
        };
    }

    @SafeVarargs
    public static <T> Item<T> ifMatch(Predicate<T> predicate, Item<T>... items) {
        return (t, result) -> {
            if (predicate.test(t)) {
                for (Item<T> item : items) {
                    item.check(t, result);
                }
            }
        };
    }

    public void checkOrCodeEx(T value) {
        Result<T> result = check(value);
        if (result.isFail()) {
            throw new CodeException(HttpStatus.BAD_REQUEST, "属性检查失败");
        }
    }

    public Result<T> check(T value) {
        Result<T> result = new Result<>(value);
        checkItemList.forEach(item -> item.check(value, result));
        return result;
    }

    public interface Item<T> {
        /**
         * 检查实体，并将错误信息添加到结果中
         *
         * @param t      实体
         * @param result 结果
         */
        void check(T t, Result<T> result);
    }

    @Data
    public static class Result<T> {
        private final T value;
        private final List<String> nullField = new ArrayList<>();

        private Result(T value) {
            this.value = value;
        }

        public void addNullField(String name) {
            nullField.add(name);
        }

        public boolean isSuccess() {
            return nullField.isEmpty();
        }

        public boolean isFail() {
            return !isSuccess();
        }
    }
}