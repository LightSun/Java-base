package com.heaven7.java.math.column;

import com.heaven7.java.base.anno.Nullable;
import com.heaven7.java.base.util.*;
import com.heaven7.java.sort.IndexComparator;
import com.heaven7.java.visitor.Visitor;
import com.heaven7.java.visitor.Visitor1;

import java.lang.reflect.Type;
import java.util.*;

public interface IColumn {

    int TYPE_STRING = 1;
    int TYPE_INT = 2;
    int TYPE_FLOAT = 3;
    int TYPE_BYTE = 4;
    int TYPE_BOOLEAN = 5;

    int TYPE_LIST = 0;

    default String getString(int index, String defVal){
        Object o = get(index);
        return o != null ? o.toString() : defVal;
    }

    default int getInt(int index, int defVal){
        Object o = get(index);
        return o instanceof Integer ? (Integer) o: defVal;
    }
    default IColumn asInt(){
        return asInt(Integer.MIN_VALUE);
    }
    default IColumn asFloat(){
        return asFloat(Float.NaN);
    }
    default IColumn sort(){
        return sort(null);
    }
    default Object last(){
        return get(size() - 1);
    }
    default Object first(){
        return get(0);
    }
    int size();
    /**
     * make array.length to target and size too.
     * @param size the target size
     */
    void setSize(int size);

    Object get(int index);
    void set(int index, String val);
    Object set(int index, Object val);

    List<String> toListString();

    void clear();

    Object removeAt(int index);

    void addAll(List<String> list);

    void add(String s);

    boolean add(int index, Object obj);

    void set0(int index, Object val);
    void removeAt0(int index);

    @SuppressWarnings({"rawtypes"})
    default boolean addAll(int index, List list){
        if(list.isEmpty()){
            return false;
        }
        int moveCount = size() - index;
        setSize(size() + list.size());
        //move
        for (int i = moveCount - 1; i >= 0 ; i++) {
            set0(index + 1 + i, get(index + i));
        }
        //set
        for (int i = 0; i < list.size(); i++) {
            set0(index + i, list.get(i));
        }
        return true;
    }

    default int indexOf(Object o){
        int size = size();
        if(o == null){
            for (int i = 0; i < size; i++) {
                if(get(i) == null){
                    return i;
                }
            }
        }else{
            for (int i = 0; i < size; i++) {
                if(o.equals(get(i))){
                    return i;
                }
            }
        }
        return -1;
    }

    default int lastIndexOf(Object o){
        int size = size();
        if(o == null){
            for (int i = size - 1; i >= 0; i--) {
                if(get(i) == null){
                    return i;
                }
            }
        }else{
            for (int i = size - 1; i >= 0; i--) {
                if(o.equals(get(i))){
                    return i;
                }
            }
        }
        return -1;
    }

    default boolean remove(Object o){
        int index = indexOf(o);
        if(index >= 0){
            removeAt0(index);
        }
        return index >= 0;
    }

    default boolean add(Object s){
        int size = size();
        setSize(size + 1);
        set0(size, s);
        return true;
    }

    default void add(IColumn other){
        int size = size();
        setSize(size + other.size());
        for (int i = 0; i < other.size(); i++) {
            set0(size + i, other.get(i));
        }
    }

    long computeSize(boolean deep);

    void swap(int index1, int index2);

    @SuppressWarnings("rawtypes")
    IColumn sort(@Nullable Comparator cmp);

    IColumn sortIndex(@Nullable IndexComparator<Object> cmp);

    default IColumn sortIndex(@Nullable Comparator<Object> cmp){
        if(cmp == null){
            return sortIndex((IndexComparator<Object>)null);
        }
        return sortIndex(new IndexComparator<Object>() {
            @Override
            public int compare(int index1, int index2, Object o1, Object o2) {
                return cmp.compare(o1, o2);
            }
        });
    }

    /**
     * the element type as int
     * @return the type int
     */
    int type();

    /**
     * the type class. often is class.
     * @return the type
     */
    Type typeClass();

    /**
     * prepare data for target size . not change the current size.
     * @param size the expect size
     */
    void prepareSize(int size);

    @SuppressWarnings("rawtypes")
    int binarySearch(Object o, @Nullable Comparator cmp);
    //------------------------------------
    IColumn copy();
    IColumn asInt(int defVal);
    IColumn asFloat(float defVal);

    /**
     * sub column
     * @param start the start index. include
     * @param end the end index, exclude
     * @return the sub column
     */
    IColumn subColumn(int start, int end);

    IColumn unique();

    //---------------------------------------
    @SuppressWarnings("rawtypes")
    default List asList(){
        return new ColumnAsList(this);
    }

    default boolean contains(Object o){
        int s = size();
        if(o == null){
            for (int i = 0; i < s; i++) {
                if(get(i) == null){
                    return true;
                }
            }
        }else{
            for (int i = 0; i < s; i++) {
                if(o.equals(get(i))){
                    return true;
                }
            }
        }
        return false;
    }
    default int containsCount(Object o){
        int s = size();
        int c = 0;
        if(o == null){
            for (int i = 0; i < s; i++) {
                if(get(i) == null){
                    c ++;
                }
            }
        }else{
            for (int i = 0; i < s; i++) {
                if(o.equals(get(i))){
                    c ++;
                }
            }
        }
        return c;
    }
    default boolean isNa(Visitor<Object, Boolean> pre){
        int s = size();
        for (int i = 0; i < s; i++) {
            if(!Predicates.isTrue(pre.visit(get(i)))){
                return false;
            }
        }
        return true;
    }
    default int sum(){
        int sum = 0;
        int s = size();
        Object val;
        for (int i = 0; i < s; i++) {
            val = get(i);
            if(val instanceof Number){
                sum += ((Number) val).intValue();
            }
        }
        return sum;
    }
    default int sum(Visitor<Object, Integer> sumVisitor){
        int sum = 0;
        int s = size();
        Integer val;
        for (int i = 0; i < s; i++) {
            val = sumVisitor.visit(get(i));
            if(val != null){
                sum += val;
            }
        }
        return sum;
    }
    default IColumn alignSize(int expectSize, Object defVal){
        int oldSize = size();
        setSize(expectSize);
        if(oldSize < expectSize){
            int diff = expectSize - oldSize;
            for (int i = 0 ; i < diff ; i ++){
                set0(i + oldSize, defVal);
            }
        }
        return this;
    }
    default IColumn tileTo(int len){
        int oldSize = size();
        setSize(len);
        if(oldSize < len){
            int diff = len - oldSize;
            int nextIndex = 0;
            for (int i = 0 ; i < diff ; i ++){
                set0(i + oldSize, get(nextIndex));
                if(nextIndex == oldSize - 1){
                    nextIndex = 0;
                }else{
                    nextIndex ++;
                }
            }
        }
        return this;
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    default void copyTo(List list){
        int size = size();
        for (int i = 0; i < size; i++) {
            list.add(get(i));
        }
    }
    default boolean boolAll(Visitor<Object, Boolean> pre){
        int s = size();
        for (int i = 0; i < s; i++) {
            if(!Predicates.isTrue(pre.visit(get(i)))){
                return false;
            }
        }
        return true;
    }
    default boolean boolAny(Visitor<Object, Boolean> pre){
        int s = size();
        for (int i = 0; i < s; i++) {
            if(Predicates.isTrue(pre.visit(get(i)))){
                return true;
            }
        }
        return false;
    }
    default Object max(Comparator<Object> cmp){
        Object max = null;
        Object val;
        int s = size();
        for (int i = 0; i < s; i++) {
            val = get(i);
            if(cmp.compare(val, max) > 0){
                max = val;
            }
        }
        return max;
    }
    //like R's which
    default IColumn which(Visitor<Object, Boolean> pre){
        return which(pre, null);
    }
    //like R's which
    default IColumn which(Visitor<Object, Boolean> pre, @Nullable List<Integer> dropIndexes){
        List<Integer> list = new ArrayList<>();
        int s = size();
        for (int i = 0; i < s; i++) {
            if(Predicates.isTrue(pre.visit(get(i)))){
                list.add(i);
            }else if(dropIndexes != null){
                dropIndexes.add(i);
            }
        }
        return new ListColumnImpl<Integer>(list);
    }
    //like R's match
    default IColumn match(Visitor<Object, Boolean> pre){
        List<Integer> list = new ArrayList<>();
        int s = size();
        for (int i = 0; i < s; i++) {
            if(Predicates.isTrue(pre.visit(get(i)))){
                list.add(i);
            }else{
                list.add(null);
            }
        }
        return new ListColumnImpl<Integer>(list);
    }
    //good impl
    @SuppressWarnings({"rawtypes"})
    default IColumn match(IColumn column, @Nullable Comparator cmp){
        column = column.copy().sort(cmp);
        List<Integer> list = new ArrayList<>();
        int s = size();
        for (int i = 0; i < s; i++) {
            if(column.binarySearch(get(i), cmp) >=0 ){
                list.add(i);
            }else{
                list.add(null);
            }
        }
        return new ListColumnImpl<Integer>(list);
    }
    //like R's grepl
    default IColumn grepl(String regex){
        byte[] result = new byte[size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (StringUtils.rGrepl(regex, getString(i, "")) ? 1 : 0);
        }
        return new ByteColumnImpl(result);
    }
    //like R's gsub
    default IColumn gsub(String regex, String replacement){
        String[] arr = new String[size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = StringUtils.rGsub(regex, replacement, getString(i, ""));
        }
        return new StringColumnImpl(arr);
    }
    //like R's strsplit
    default List<IColumn> strsplit(String regex, boolean row_bind){
        List<IColumn> list = new ArrayList<>();
        int size = size();
        int maxC = 0;
        for (int i = 0; i < size; i++) {
            IColumn column = ColumnFactory.strsplit(getString(i, ""), regex);
            list.add(column);
            maxC = Math.max(maxC, column.size());
        }
        if(!row_bind){
            List<IColumn> ret = new ArrayList<>();
            for (int i = 0; i < maxC; i++) {
                String[] arr = new String[size];
                for (int j = 0; j < size; j++) {
                    arr[j] = list.get(j).tileTo(maxC).getString(i, "");
                }
                ret.add(new StringColumnImpl(arr));
            }
            return ret;
        }
        return list;
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    default int compareElements(int index1, int index2, @Nullable Comparator<Object> cmp){
        Object o1 = get(index1);
        Object o2 = get(index2);
        if(cmp != null){
            return cmp.compare(o1, o2);
        }
        if(o1 == null){
            if(o2 == null){
                return 0;
            }else{
                return -1;//null is less than
            }
        }
        return ((Comparable)o1).compareTo(o2);
    }
    /**
     * order and return the index column. note every column.size() must be the same
     * @param cmp the comparator of current column
     * @param columns the other columns
     * @param _cmps the other comparators, null for nature order by Comparable
     * @return the order index as column.
     */
    //like R's order
    @SuppressWarnings({"unchecked", "rawtypes"})
    default IColumn order(Comparator<Object> cmp, @Nullable List<IColumn> columns, @Nullable List<Comparator<Object>> _cmps){

        if(!Predicates.isEmpty(columns)){
            final List<Comparator<Object>> cmps = _cmps != null ? _cmps: CollectionUtils.produce(columns.size(), null);
            Throwables.checkArgument(columns.size() == cmps.size(), "size must eq");
            final int extSize = columns.size();
            IndexComparator<Object> cmp1 = new IndexComparator<Object>() {
                @Override
                public int compare(int index1, int index2, Object o1, Object o2) {
                    int ret = cmp != null ? cmp.compare(o1, o2) : ((Comparable) o1).compareTo(o2);
                    if (ret == 0) {
                        int result;
                        for (int i = 0; i < extSize; i++) {
                            result = columns.get(i).compareElements(index1, index2, cmps.get(i));
                            if(result != 0){
                                return result;
                            }
                        }
                        return 0;
                    }else{
                        return ret;
                    }
                }
            };
            return sortIndex(cmp1);
        }else{
            return sortIndex(cmp);
        }
    }

    default <T> IColumn map(Visitor<Object, T> visitor){
        List<T> list = new ArrayList<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            list.add(visitor.visit(get(i)));
        }
        return new ListColumnImpl<>(list);
    }
    default <Key, Value> IPairColumn map2mapPair(Visitor<Object, Key> kv, Visitor<Object, Value> vv){
        Set<Key> keys = new HashSet<>();
        List<Pair<Key, Value>> list = new ArrayList<>();
        int size = size();
        Object obj;
        Key key;
        for (int i = 0; i < size; i++) {
            obj = get(i);
            key = kv.visit(obj);
            if(keys.contains(key)){
                continue;
            }
            keys.add(key);
            list.add(new Pair<>(key, vv.visit(obj)));
        }
        return new PairColumnImpl<>(list);
    }
    default <Key, Value> IMapColumns map2mapColumn(Visitor<Object, Key> kv, Visitor<Object, Value> vv){
        List<Key> keys = new ArrayList<>();
        List<Value> values = new ArrayList<>();
        int size = size();
        Object obj;
        Key key;
        for (int i = 0; i < size; i++) {
            obj = get(i);
            key = kv.visit(obj);
            if(keys.contains(key)){
                continue;
            }
            keys.add(key);
            values.add(vv.visit(obj));
        }
        return new MapColumnsImpl(new ListColumnImpl<>(keys), new ListColumnImpl<>(values));
    }
    default <Key, Value> Map<Key,Value> map2map(Visitor<Object, Key> kv, Visitor<Object, Value> vv, Comparator<? super Key> cmp){
        Map<Key,Value> map = cmp == null ? new HashMap<>() : new TreeMap<>(cmp);
        int size = size();
        Object obj;
        Key key;
        for (int i = 0; i < size; i++) {
            obj = get(i);
            key = kv.visit(obj);
            if(key != null){
                if(map.containsKey(key)){
                    continue;
                }
                map.put(key, vv.visit(obj));
            }
        }
        return map;
    }

    /**
     * filter elements.
     * @param pre the predicate visitor
     * @param cb the callback .return true if need break next loop of filter
     * @return this.
     */
    default IColumn filter(Visitor<Object, Boolean> pre,@Nullable Visitor1<Integer, Object, Boolean> cb){
        int size = size();
        if(cb == null){
            for (int i = size - 1; i >=0 ; i--) {
                if(Predicates.isTrue(pre.visit(get(i)))){
                    removeAt0(i);
                }
            }
        }else{
            Object obj;
            for (int i = size - 1; i >=0 ; i--) {
                obj = get(i);
                if(Predicates.isTrue(pre.visit(obj))){
                    removeAt0(i);
                    if(Predicates.isTrue(cb.visit(i, obj))){
                        break;
                    }
                }
            }
        }
        return this;
    }
    default IColumn applyIndexResult(List<Integer> indexes){
        List<Object> list = new ArrayList<>();
        for(Integer idx : indexes){
            list.add(get(idx));
        }
        return new ListColumnImpl<>(list);
    }
    default IColumn computeIndex(Visitor1<Integer, Object, Integer> pre){
        List<Integer> list = new ArrayList<>();
        int size = size();
        Integer index;
        for (int i = size - 1; i >=0 ; i--) {
            index = pre.visit(i, get(i));
            if(index != null){
                list.add(index);
            }
        }
        int[] indexes = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            indexes[i] = list.get(i);
        }
        return new IntColumnImpl(indexes);
    }

    interface IMapColumns {
        IColumn keys();
        IColumn values();
        Object getValue(Object key);
        Object getKey(Object value);
        Object put(Object k, Object v);
        int size();
        @SuppressWarnings("rawtypes")
        Map toMap(Comparator<Object> key_cmp);
    }
    interface IPairColumn extends IColumn, IMapColumns {
        @SuppressWarnings("rawtypes")
        Pair getPair(int index);
    }
}

