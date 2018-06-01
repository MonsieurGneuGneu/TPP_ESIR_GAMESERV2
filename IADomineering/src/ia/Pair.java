package ia;

public class Pair<K,V> {
    private K k;
    private V v;
    public Pair(K k, V v){
        this.setKey(k);
        this.setValue(v);
    }
    public V getValue() {
        return v;
    }
    public void setValue(V v) {
        this.v = v;
    }
    public K getKey() {
        return k;
    }
    public void setKey(K k) {
        this.k = k;
    }
    
    
}
