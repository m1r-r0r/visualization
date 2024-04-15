package com.github.m1rr0r.visualization.dataStructure;

public interface Transform<T, C extends Column> {
    T transformColumn(C column);
}
