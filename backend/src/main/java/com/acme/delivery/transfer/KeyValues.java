package com.acme.delivery.transfer;

import lombok.Value;

@Value
public class KeyValues<K,V,L> {
    K key;
    V firstValue;
    L secondValue;
}