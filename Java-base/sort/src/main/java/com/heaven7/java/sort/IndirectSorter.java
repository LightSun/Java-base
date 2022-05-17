package com.heaven7.java.sort;

import com.heaven7.java.base.anno.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * the indirect sort. help we sort and return the index.
 * @author heaven7
 */
public final class IndirectSorter {

    public static <T> int[] sort(List<T> args, int start, int end, @Nullable Comparator<? super T> cmp) {
        return sort(args, start, end, new IndexComparator<T>() {
            @Override
            public int compare(int index1, int index2, T o1, T o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static <T> int[] sort(T[] args, int start, int end, @Nullable Comparator<? super T> cmp) {
        return sort(args, start, end, new IndexComparator<T>() {
            @Override
            public int compare(int index1, int index2, T o1, T o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static int[] sort(int[] args, int start, int end, @Nullable Comparator<? super Integer> cmp) {
        return sort(args, start, end, new IndexComparator<Integer>() {
            @Override
            public int compare(int index1, int index2, Integer o1, Integer o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static int[] sort(short[] args, int start, int end, @Nullable Comparator<? super Short> cmp) {
        return sort(args, start, end, new IndexComparator<Short>() {
            @Override
            public int compare(int index1, int index2, Short o1, Short o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static int[] sort(byte[] args, int start, int end, @Nullable Comparator<? super Byte> cmp) {
        return sort(args, start, end, new IndexComparator<Byte>() {
            @Override
            public int compare(int index1, int index2, Byte o1, Byte o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static int[] sort(long[] args, int start, int end, @Nullable Comparator<? super Long> cmp) {
        return sort(args, start, end, new IndexComparator<Long>() {
            @Override
            public int compare(int index1, int index2, Long o1, Long o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static int[] sort(float[] args, int start, int end, @Nullable Comparator<? super Float> cmp) {
        return sort(args, start, end, new IndexComparator<Float>() {
            @Override
            public int compare(int index1, int index2, Float o1, Float o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    public static int[] sort(double[] args, int start, int end, @Nullable Comparator<? super Double> cmp) {
        return sort(args, start, end, new IndexComparator<Double>() {
            @Override
            public int compare(int index1, int index2, Double o1, Double o2) {
                return cmp.compare(o1, o2);
            }
        });
    }
    /**
     * sort and return the origin indexes
     *
     * @param args the array to sort
     * @param start the start index, include.
     * @param end the end index. exclude
     * @param cmp  the Comparator, null means T must impl Comparable
     * @param <T>  the element type
     * @return the index array
     */
    @SuppressWarnings("unchecked")
    public static <T> int[] sort(T[] args, int start, int end, @Nullable IndexComparator<? super T> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new ArrayImpl<T>(args, cmp));
        //copy the raw value.
        T[] srcArr = (T[]) Array.newInstance(args.getClass().getComponentType(), c);
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            //set the new value
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static <T> int[] sort(List<T> args, int start, int end, @Nullable IndexComparator<? super T> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new ListImpl<T>(args, cmp));
        //copy the raw value.
        List<T> srcArr = new ArrayList<>(args.subList(start, end));

        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            //set the new value
            args.set(i + start, srcArr.get(retval[i]));
           // args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static int[] sort(int[] args, int start, int end, @Nullable IndexComparator<? super Integer> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new IntImpl(args, cmp));
        int[] srcArr = new int[c];
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static int[] sort(short[] args, int start, int end,@Nullable IndexComparator<? super Short> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new ShortImpl(args, cmp));
        short[] srcArr = new short[c];
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static int[] sort(byte[] args, int start, int end,@Nullable IndexComparator<? super Byte> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new ByteImpl(args, cmp));
        //copy the raw value.
        byte[] srcArr = new byte[c];
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            //set the new value
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static int[] sort(long[] args, int start, int end,@Nullable IndexComparator<? super Long> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new LongImpl(args, cmp));
        //copy the raw value.
        long[] srcArr = new long[c];
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            //set the new value
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static int[] sort(float[] args, int start, int end,@Nullable IndexComparator<? super Float> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new FloatImpl(args, cmp));
        //copy the raw value.
        float[] srcArr = new float[c];
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            //set the new value
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    public static int[] sort(double[] args, int start, int end, @Nullable IndexComparator<? super Double> cmp) {
        final int c = end - start;
        Integer[] originIndexes = new Integer[c];
        int[] retval = new int[c];
        for (int i = 0; i < c; i++) {
            originIndexes[i] = i + start;
        }
        Arrays.sort(originIndexes, new DoubleImpl(args, cmp));
        //copy the raw value.
        double[] srcArr = new double[c];
        System.arraycopy(args, start, srcArr, 0, c);
        for (int i = 0; i < originIndexes.length; i++) {
            retval[i] = originIndexes[i];
            //set the new value
            args[i + start] = srcArr[retval[i]];
        }
        return retval;
    }
    private static class DoubleImpl implements Comparator<Integer>{
        final double[] args;
        final IndexComparator<? super Double> cmp;

        public DoubleImpl(double[] args, IndexComparator<? super Double> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            if (cmp == null) {
                return Double.compare(args[o1], args[o2]);
            }
            return cmp.compare(o1, o2, args[o1], args[o2]);
        }
    }
    private static class FloatImpl implements Comparator<Integer>{
        final float[] args;
        final IndexComparator<? super Float> cmp;

        public FloatImpl(float[] args, IndexComparator<? super Float> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            if (cmp == null) {
                return Float.compare(args[o1], args[o2]);
            }
            return cmp.compare(o1, o2, args[o1], args[o2]);
        }
    }
    private static class LongImpl implements Comparator<Integer>{
        final long[] args;
        final IndexComparator<? super Long> cmp;

        public LongImpl(long[] args, IndexComparator<? super Long> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            if (cmp == null) {
                return Long.compare(args[o1], args[o2]);
            }
            return cmp.compare(o1, o2, args[o1], args[o2]);
        }
    }
    private static class ByteImpl implements Comparator<Integer>{
        final byte[] args;
        final IndexComparator<? super Byte> cmp;

        public ByteImpl(byte[] args, IndexComparator<? super Byte> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            if (cmp == null) {
                return Short.compare(args[o1], args[o2]);
            }
            return cmp.compare(o1, o2, args[o1], args[o2]);
        }
    }
    private static class ShortImpl implements Comparator<Integer>{
        final short[] args;
        final IndexComparator<? super Short> cmp;
        public ShortImpl(short[] args, IndexComparator<? super Short> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            if (cmp == null) {
                return Short.compare(args[o1], args[o2]);
            }
            return cmp.compare(o1, o2, args[o1], args[o2]);
        }
    }
    private static class IntImpl implements Comparator<Integer>{
        final int[] args;
        final IndexComparator<? super Integer> cmp;
        public IntImpl(int[] args, IndexComparator<? super Integer> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @Override
        public int compare(Integer o1, Integer o2) {
            if (cmp == null) {
                return Integer.compare(args[o1], args[o2]);
            }
            return cmp.compare(o1, o2, args[o1], args[o2]);
        }
    }
    private static class ListImpl<T> implements Comparator<Integer> {
        final List<T> args;
        final IndexComparator<? super T> cmp;

        public ListImpl(List<T> args, IndexComparator<? super T> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @SuppressWarnings("unchecked")
        public int compare(Integer in1, Integer in2) {
            if (cmp == null) {
                return ((Comparable<T>) (args.get(in1))).compareTo(args.get(in2));
            }
            return cmp.compare(in1, in2, args.get(in1), args.get(in2));
        }
    }
    private static class ArrayImpl<T> implements Comparator<Integer> {
        final T[] args;
        final IndexComparator<? super T> cmp;

        public ArrayImpl(T[] args, IndexComparator<? super T> cmp) {
            this.args = args;
            this.cmp = cmp;
        }
        @SuppressWarnings("unchecked")
        public int compare(Integer in1, Integer in2) {
            if (cmp == null) {
                return ((Comparable<T>) args[in1]).compareTo(args[in2]);
            }
            return cmp.compare(in1, in2, args[in1], args[in2]);
        }
    }
}

