/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.naxpoct.bank.utility;

/**
 *
 * @author Rashad Suleymanov
 */
public class Entry3<L, K, V,T> {

    private L l;
    private K k;
    private V v;
    private T t;

    public Entry3() {

    }

    public L getL() {
        return l;
    }

    public void setL(L l) {
        this.l = l;
    }

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }


}
