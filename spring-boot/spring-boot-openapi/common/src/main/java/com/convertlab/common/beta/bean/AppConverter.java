package com.convertlab.common.beta.bean;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AppConverter<A, B> {
    final Function<A, B> a2b;
    final Function<B, A> b2a;

    public AppConverter(Function<A, B> a2b) {
        this.a2b = a2b;
        this.b2a = null;
    }

    public AppConverter(Function<A, B> a2b, Function<B, A> b2a) {
        this.a2b = a2b;
        this.b2a = b2a;
    }

    public B converterA2B(final A a) {
        return a2b.apply(a);
    }

    public List<B> converterA2Bs(final List<A> as) {
        return as.stream().map(this::converterA2B).collect(Collectors.toList());
    }

    public A converterB2A(final B b) {
        return b2a.apply(b);
    }

    public List<A> converterB2As(final List<B> bs) {
        return bs.stream().map(this::converterB2A).collect(Collectors.toList());
    }

}