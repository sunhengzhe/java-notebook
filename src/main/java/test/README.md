# Test

## Test Double

[TestDouble](https://martinfowler.com/bliki/TestDouble.html)

> Test Double is a generic term for any case where you replace a production object for testing purpose. There are various kinds of double list : Dummy、Fake、Stub、Mock and Spy.

### Stub

Stub 只是返回一个规定的值，而不会去涉及到系统的任何改变。[mockito](./mockito) 可以用于创建 stub。
