# 函数式接口 functional interface

只有一个抽象方法的接口称为函数式接口。

如果设计自己的函数式接口，可以使用 `@FunctionalInterface` 注解来标记这个接口。
如果无意中添加了另一个抽象方法，编译器会产生错误，另外 javadoc 会指出这个接口是函数式接口。

## 常用函数式接口

| 函数式接口 | 参数类型 | 返回类型 | 抽象方法名 | 描述 | 其他方法 |
| --- | --- | --- | --- | --- | --- |
| Runnable | 无 | void | run | 作为无参数或者返回值的动作运行 | |
| Supplier<T> | 无 | T | get | 提供一个 T 类型的值 | |
| Consumer<T> | T | void | accept | 处理一个 T 类型的值 | andThen |
| BiConsumer<T, U> | T, U | void | accept | 处理 T 和 U 类型的值 | andThen |
| Function<T, R> | T | R | apply | 有一个 T 类型参数的函数 | compose, andThen, identity |
| BiFunction<T, U, R> | T, U | R | apply | 有 T 和 U 类型参数的函数 | andThen |
| UnaryOperator<T> | T | T | apply | 类型 T 上的一元操作符 | compose, andThen, identity |
| BinaryOperator<T> | T, T | T | apply | 类型 T 上的二元操作符 | andThen, maxBy, minBy |
| Predicate<T> | T | boolean | test | 布尔值函数 | and, or, negate, isEqual |
| BiPredicate<T, U> | T, U | boolean | test | 有两个参数的布尔值函数 | and, or, negate |

对于基本类型，为了减少自动装箱，还有一些基本类型的函数式接口：

| 函数式接口 | 参数类型 |  返回类型 | 抽象方法名 |
| --- | --- | --- | --- |
| BooleanSupplier | none | boolean | getAsBoolean |
| PSupplier | none | p | getAsP |
| PConsumer | p | void | accept |
| ObjPConsumer<T> | T, p | void | accept |
| PFunction<T> | p | T | apply |
| PToQFunction | p | q | applyAsQ |
| ToPFunction<T> | T | p | applyAsP |
| ToPBiFunction<T, U> | T, U | p | applyAsP |
| PUnaryOperator | p | p | applyAsP |
| PBinaryOperator | p, p | p | applyAsP |
| PPredicate | p | boolean | test |

*其中，p, q 为 int, long, double；P, Q 为 Int, Long, Double*

所以可以使用 IntConsumer 而不是 `Consumer<Integer>`。

## 非抽象方法

大多数标准函数式接口都提供了非抽象方法来生成或合并函数。例如，`Predicate.isEqual(a)`
等同于 `a::equals`，不过前者在 a 为 null 时也能正常工作。另外可以使用 Predicate 的默认方法
and、or 和 negate 来合并谓词。如 `Predicate.isEqual(a).or(Predicate.isEqual(b))` 就等同于 `x -> a.equals(x) || b.equals(x)`
