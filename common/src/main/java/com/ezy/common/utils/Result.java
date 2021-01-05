package com.ezy.common.utils;

import com.ezy.common.exception.CodeException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @Author: zehao.zhang
 * @CreateDate: 2020/07/02 15:23
 * @Desc
 * @Version: 1.0
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Result<T, M> {

	private static final Result BLANK_SUCCESS =
			new Result<>(true, null, null);

	@Getter
	private final boolean success;
	private final T value;
	private final M message;

	public boolean isFail() {
		return !success;
	}

	public <E> Result<E, M> map(Function<? super T, ? extends E> function) {
		if (success) {
			return success(function.apply(value));
		} else {
			return uncheckedCast();
		}
	}

	public <E> Result<E, M> map(Supplier<? extends E> function) {
		if (success) {
			return success(function.get());
		} else {
			return uncheckedCast();
		}
	}

	public Result<Void, M> mapToVoid() {
		if (success) {
			return success();
		} else {
			return uncheckedCast();
		}
	}

	public Result<T, M> filter(Predicate<T> function, M error) {
		if (!success || function.test(value)) {
			return this;
		} else {
			return Result.fail(error);
		}
	}

	public <E> Result<E, M> flatMap(Function<? super T, Result<E, M>> function) {
		if (success) {
			return function.apply(value);
		} else {
			return uncheckedCast();
		}
	}

	public <E> Result<E, M> flatMap(Supplier<Result<E, M>> function) {
		if (success) {
			return function.get();
		} else {
			return uncheckedCast();
		}
	}

	public T orElse(Function<? super M, ? extends T> function) {
		if (success) {
			return value;
		} else {
			return function.apply(message);
		}
	}

	public T orElse(Supplier<? extends T> function) {
		if (success) {
			return value;
		} else {
			return function.get();
		}
	}

	public <E> E combine(Function<? super T, ? extends E> tFunction, Function<? super M, ? extends E> mFunction) {
		if (success) {
			return tFunction.apply(value);
		} else {
			return mFunction.apply(message);
		}
	}

	public <E> Result<T, E> orElseMap(Function<? super M, ? extends E> function) {
		if (success) {
			return uncheckedCast();
		} else {
			return fail(function.apply(message));
		}
	}

	public <E> Result<T, E> orElseMap(Supplier<? extends E> function) {
		if (success) {
			return uncheckedCast();
		} else {
			return fail(function.get());
		}
	}

	public Result<T, M> orElseFlatMap(Function<? super M, Result<T, M>> supplier) {
		if (success) {
			return this;
		} else {
			return supplier.apply(message);
		}
	}

	public Result<T, M> orElseFlatMap(Supplier<Result<T, M>> supplier) {
		if (success) {
			return this;
		} else {
			return supplier.get();
		}
	}

	public <EX extends Throwable> T orElseThrow(Function<M, EX> function) throws EX {
		if (success) {
			return value;
		} else {
			throw function.apply(message);
		}
	}

	public T orElseCodeEx() throws CodeException {
		if (success) {
			return value;
		} else {
			throw new CodeException(Objects.toString(message));
		}
	}

	public T orElseCodeEx(HttpStatus status) throws CodeException {
		if (success) {
			return value;
		} else {
			throw new CodeException(status, Objects.toString(message));
		}
	}

	public T orElseCodeEx(HttpStatus status, Function<M, String> mapping) throws CodeException {
		if (success) {
			return value;
		} else {
			throw new CodeException(status, mapping.apply(message));
		}
	}

	public Optional<T> optional() {
		if (success) {
			return Optional.ofNullable(value);
		} else {
			return Optional.empty();
		}
	}

	public Stream<T> stream() {
		if (success) {
			return Stream.of(value);
		} else {
			return Stream.empty();
		}
	}

	public Result<T, M> ifSuccess(Consumer<? super T> consumer) {
		if (success) {
			consumer.accept(value);
		}
		return this;
	}

	public Result<T, M> ifFall(Consumer<? super M> consumer) {
		if (!success) {
			consumer.accept(message);
		}
		return this;
	}


	/**
	 * 用于内部进行类型调整
	 */
	@SuppressWarnings("unchecked")
	private <T1, EX1> Result<T1, EX1> uncheckedCast() {
		return (Result<T1, EX1>) this;
	}

	public static <T, EX> Result<T, EX> success(T value) {
		return new Result<>(true, value, null);
	}

	@SuppressWarnings("unchecked")
	public static <EX> Result<Void, EX> success() {
		return BLANK_SUCCESS;
	}

	public static <T, EX> Result<T, EX> fail(EX message) {
		return new Result<>(false, null, message);
	}

	public static <EX> Result<Void, EX> create(boolean task, EX message) {
		if (task) {
			return success();
		} else {
			return fail(message);
		}
	}

	public static <T, EX> Result<T, EX> create(boolean task, T value, EX message) {
		if (task) {
			return success(value);
		} else {
			return fail(message);
		}
	}

	public static <T> Result<T, Void> of(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<T> opt) {
		if (opt != null && opt.isPresent()) {
			return Result.success(opt.get());
		} else {
			return Result.fail(null);
		}
	}

	public static <T, M> Result<T, M> of(@SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<T> opt,
										 M message) {
		if (opt != null && opt.isPresent()) {
			return Result.success(opt.get());
		} else {
			return Result.fail(message);
		}
	}
}
