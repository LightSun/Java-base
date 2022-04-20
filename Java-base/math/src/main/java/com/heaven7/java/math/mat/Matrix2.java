package com.heaven7.java.math.mat;

import com.heaven7.java.base.anno.Nullable;
import com.heaven7.java.visitor.FireIndexedVisitor;
import com.heaven7.java.visitor.PileVisitor;
import com.heaven7.java.visitor.ResultVisitor;
import com.heaven7.java.visitor.Visitors;
import com.heaven7.java.visitor.collection.VisitServices;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Two-dimensional matrix
 *
 * @param <T> the type of element
 */
public class Matrix2<T> {

    private final List<List<T>> values;

    public Matrix2(List<List<T>> values) {
        this.values = values;
    }

    public static Matrix2<Integer> ofIntArrayArray(int[][] data) {
        List<List<Integer>> list = new ArrayList<>();
        final int w = data.length;
        final int h = data[0].length;
        for (int i = 0; i < w; i++) {
            List<Integer> cols = new ArrayList<>();
            for (int j = 0; j < h; j++) {
                cols.add(data[i][j]);
            }
            list.add(cols);
        }
        return new Matrix2<Integer>(list);
    }

    public static Matrix2<Float> ofFloatArrayArray(float[][] data) {
        List<List<Float>> list = new ArrayList<>();
        final int w = data.length;
        final int h = data[0].length;
        for (int i = 0; i < w; i++) {
            List<Float> cols = new ArrayList<>();
            for (int j = 0; j < h; j++) {
                cols.add(data[i][j]);
            }
            list.add(cols);
        }
        return new Matrix2<Float>(list);
    }

    public static Matrix2<Double> ofDoubleArrayArray(double[][] data) {
        List<List<Double>> list = new ArrayList<>();
        final int w = data.length;
        final int h = data[0].length;
        for (int i = 0; i < w; i++) {
            List<Double> cols = new ArrayList<>();
            for (int j = 0; j < h; j++) {
                cols.add(data[i][j]);
            }
            list.add(cols);
        }
        return new Matrix2<Double>(list);
    }

    public static Matrix2<Integer> ofIntArray(int w, int h, int[] arr) {
        if (arr.length != w * h) {
            throw new IllegalArgumentException();
        }
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            List<Integer> cols = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                cols.add(arr[i * w + j]);
            }
            list.add(cols);
        }
        return new Matrix2<>(list);
    }

    public static Matrix2<Float> ofFloatArray(int w, int h, float[] arr) {
        if (arr.length != w * h) {
            throw new IllegalArgumentException();
        }
        List<List<Float>> list = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            List<Float> cols = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                cols.add(arr[i * w + j]);
            }
            list.add(cols);
        }
        return new Matrix2<>(list);
    }

    public static Matrix2<Double> ofDoubleArray(int w, int h, double[] arr) {
        if (arr.length != w * h) {
            throw new IllegalArgumentException();
        }
        List<List<Double>> list = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            List<Double> cols = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                cols.add(arr[i * w + j]);
            }
            list.add(cols);
        }
        return new Matrix2<>(list);
    }

    public static <T> Matrix2<T> ofObjectArray(int w, int h, T[] arr) {
        if (arr.length != w * h) {
            throw new IllegalArgumentException();
        }
        List<List<T>> list = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            List<T> cols = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                cols.add(arr[i * w + j]);
            }
            list.add(cols);
        }
        return new Matrix2<>(list);
    }

    public List<List<T>> getRawValues() {
        return values;
    }

    //row
    public int getRowCount() {
        return values.size();
    }

    //col
    public int getColumnCount() {
        return values.isEmpty() ? 0 : values.get(0).size();
    }

    public int getTotalSize() {
        return getRowCount() * getColumnCount();
    }

    /**
     * clip the mat with target row and column parameters.
     * @param rowStart the row start
     * @param rowEnd the row end
     * @param colStart the col start
     * @param colEnd the col end
     * @return the mat
     * @since 1.2.5
     */
    public Matrix2<T> subMat(int rowStart, int rowEnd,int colStart, int colEnd) {
        final int rc = getRowCount();
        final int cc = getColumnCount();
        if(rowStart < 0){
            rowStart = 0;
        }
        if(rowEnd < 0){
            rowEnd = rc - 1;
        }
        if(colStart < 0){
            colStart = 0;
        }
        if(colEnd < 0){
            colEnd = cc - 1;
        }
        rowStart = Math.min(rowStart, rc - 1);
        rowEnd = Math.min(rowEnd, rc - 1);
        colStart = Math.min(colStart, cc - 1);
        colEnd = Math.min(colEnd, cc - 1);
        //
        List<List<T>> ret = new ArrayList<>();
        for (int i = rowStart; i <= rowEnd; i++) {
            List<T> ts = values.get(i);
            ret.add(ts.subList(colStart, colEnd + 1));
        }
        return new Matrix2<>(ret);
    }
    //================================================================================================================

    /**
     * padding the matrix by target element provider
     * @param left the left padding
     * @param top the top padding
     * @param right the right padding
     * @param bottom the bottom padding
     * @param provider the element provider
     * @return this.
     */
    public Matrix2<T> padding(int left, int top, int right, int bottom,
                              ElementProvider<T> provider){
        //left and right make add columns, top and bottom make add rows
        if(left > 0){
            int w = getRowCount();
           // int h = getColumnCount();
            int size;
            for (int i = 0; i < w; i++) {
                List<T> ts = values.get(i);
                for(int j = 0 ; j < left ; j ++){
                    ts.add(0, provider.provide(i, j, null));
                }
                if(right > 0){
                    size = ts.size();
                    for(int j = 0 ; j < right ; j ++){
                        ts.add(provider.provide(i, size + j, null));
                    }
                }
            }
        }else {
            if (right > 0) {
                int w = getRowCount();
                int size;
                for (int i = 0; i < w; i++) {
                    List<T> ts = values.get(i);
                    size = ts.size();
                    for (int j = 0; j < right; j++) {
                        ts.add(provider.provide(i, size + j, null));
                    }
                }
            }
        }
         //top and bottom
        if(top > 0){
            int h = getColumnCount();
            for(int t = 0 ; t < top ; t ++) {
                List<T> list = new ArrayList<>();
                for (int i = 0; i < h; i++) {
                    list.add(provider.provide(t, i, null));
                }
                values.add(t, list);
            }
        }
        if(bottom > 0) {
            final int h = getColumnCount();
            final int rowCount = values.size();
            for (int b = 0; b < bottom; b++) {
                List<T> list = new ArrayList<>();
                for (int i = 0; i < h; i++) {
                    list.add(provider.provide(rowCount + b, i, null));
                }
                values.add(rowCount + b, list);
            }
        }
        return this;
    }

    /**
     * compute the convolution. this should called after call fill.
     *
     * @param core     the core matrix of convolution
     * @param coreSum  the sum of core convolution
     * @param outW     the out width(row count) of matrix
     * @param outH     the out height(column count) of matrix
     * @param strideX  the stride x of row
     * @param strideY  the stride y of column
     * @param callback the convolution callback
     * @param sum      the sum visitor of result type
     * @param average  the  average callback of result type
     * @param provider the provider of fill gap.
     * @param <C>      the type of core convolution
     * @param <R>      the result type of convolution
     * @return the result of convolution
     */
    public <C, R> Matrix2<R> computeConvolution(Matrix2<C> core, double coreSum, int outW, int outH,
                                                int strideX, int strideY,
                                                ConvolutionCallback<T, C, R> callback, PileVisitor<R> sum,
                                                AverageCallback<R> average, ElementProvider<R> provider) {
        List<List<R>> results = new ArrayList<>();
        for (int i = outW - 1; i >= 0; i--) {
            results.add(new ArrayList<>());
        }
        int w_core = core.getRowCount();
        int h_core = core.getColumnCount();
        int w = getRowCount();
        int h = getColumnCount();

        int wIndex = 0;
        //int hIndex = 0;
        int lastWidthIndex = 0, lastHeightIndex = 0;
        while (true) {
            R total = null;
            for (int i = 0; i < w_core; i++) {
                for (int i2 = 0; i2 < h_core; i2++) {
                    T cur = values.get(i + lastWidthIndex).get(i2 + lastHeightIndex);
                    C factor = core.getRawValues().get(i).get(i2);
                    R result = callback.multiple(cur, factor);
                    if (total == null) {
                        total = result;
                    } else {
                        total = sum.visit(null, total, result);
                    }
                }
            }
            if (coreSum != 0 && coreSum != 1) {
                total = average.average(total, coreSum);
            }
            results.get(wIndex).add(total);
            lastHeightIndex += strideY;
            if (lastHeightIndex >= h - strideY) {
                lastHeightIndex = 0;
                lastWidthIndex += strideX;
                if (lastWidthIndex > w - strideX) {
                    break;
                }
                wIndex++;
            }
        }
        //Filling in the gaps
        VisitServices.from(results).fireWithIndex(new FireIndexedVisitor<List<R>>() {
            @Override
            public Void visit(Object param, List<R> list, int index, int size) {
                if (list.size() < outH) {
                    int count = outH - list.size();
                    for (int i = 0; i < count; i++) {
                        list.add(provider.provide(index, i, null));
                    }
                }
                return null;
            }
        });
        return new Matrix2<>(results);
    }

    public T sum(final PileVisitor<T> pileVisitor) {
        return sum(Visitors.unchangeResultVisitor(), pileVisitor);
    }

    public <R> R sum(ResultVisitor<T, R> transformer, final PileVisitor<R> pileVisitor) {
        return VisitServices.from(values).pile(null, new ResultVisitor<List<T>, R>() {
            @Override
            public R visit(List<T> ts, Object param) {
                return VisitServices.from(ts).map(transformer).pile(pileVisitor);
            }
        }, pileVisitor);
    }

    /**
     * divide chunk by target width and height.
     */
    public List<Matrix2<T>> divideChunk(int width, int height) {
        List<Matrix2<T>> result = new ArrayList<>();

        final int wSize = values.size();
        final int hSize = values.get(0).size();
        int lastWidthIndex = 0, lastHeightIndex = 0;

        while (lastWidthIndex < wSize && lastHeightIndex < hSize) {
            int size1 = Math.min(width, wSize - lastWidthIndex);
            int size2 = Math.min(height, hSize - lastHeightIndex);
            List<List<T>> list = new ArrayList<>();
            for (int i = 0; i < size1; i++) {
                List<T> cols = values.get(i + lastWidthIndex);
                List<T> tmp = new ArrayList<>();
                for (int i2 = 0; i2 < size2; i2++) {
                    T t = cols.get(i2 + lastHeightIndex);
                    tmp.add(t);
                }
                list.add(tmp);
            }
            result.add(new Matrix2<>(list));

            lastWidthIndex += size1;
            //hor：done, start vertical
            if (lastWidthIndex >= wSize) {
                lastWidthIndex = 0;
                lastHeightIndex += size2;
            }
        }
        return result;
    }

    /**
     * compute the variance.
     *
     * @param transformer the transformer
     * @param sum         the sum visitor
     * @param average     the average callback
     * @param variance    the variance visitor
     * @param <R>         the result type.
     * @return the variance value.
     */
    public <R> R variance(ResultVisitor<T, R> transformer, PileVisitor<R> sum,
                          AverageCallback<R> average, PileVisitor<R> variance) {
        return variance(null, transformer, sum, average, variance);
    }

    /**
     * compute the variance.
     *
     * @param param       the extra parameter
     * @param transformer the transformer
     * @param sum         the sum visitor
     * @param average     the average callback
     * @param variance    the variance visitor
     * @param <R>         the result type.
     * @return the variance value.
     */
    public <R> R variance(@Nullable Object param, ResultVisitor<T, R> transformer, PileVisitor<R> sum,
                          AverageCallback<R> average, PileVisitor<R> variance) {
        final int w = getRowCount();
        final int h = getColumnCount();
        R averageVal = average.average(sum(transformer, sum), w * h);

        R total = null;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                R result = variance.visit(param, averageVal,
                        transformer.visit(values.get(j).get(i), param));
                if (total != null) {
                    total = sum.visit(param, total, result);
                } else {
                    total = result;
                }
            }
        }
        return average.average(total, w * h);
    }

    /**
     * AT
     */
    public Matrix2<T> transpose() {
        List<List<T>> list = new ArrayList<>();
        int w = getRowCount();
        int h = getColumnCount();
        for (int i = 0; i < h; i++) {
            List<T> cols = new ArrayList<>();
            for (int j = 0; j < w; j++) {
                cols.add(values.get(j).get(i));
            }
            list.add(cols);
        }
        return new Matrix2<>(list);
    }

    public Matrix2<T> turnLeftRight() {
        ArrayList<List<T>> lists = new ArrayList<>(values);
        Collections.reverse(lists);
        return new Matrix2<>(lists);
    }

    public Matrix2<T> turnTopBottom() {
        ArrayList<List<T>> lists = new ArrayList<>();
        for (List<T> list : values) {
            ArrayList<T> list1 = new ArrayList<>(list);
            Collections.reverse(list1);
            lists.add(list1);
        }
        return new Matrix2<>(lists);
    }

    public Matrix2<T> turnRight90() {
        return ofObjectArray(getRowCount(), getColumnCount(), toArray(null));
    }

    /**
     * rotate the matrix2 clockwise.
     *
     * @param degree the degree to rotate
     * @return the result matrix2
     */
    public Matrix2<T> rotateClockwise(int degree) {
        if (degree % 90 != 0) {
            throw new IllegalArgumentException();
        }
        degree %= 360;
        if (degree < 0) {
            degree += 360;
        }
        if (degree == 0) {
            return this;
        }
        switch (degree) {
            case 90:
                return turnRight90().turnLeftRight();

            case 180:
                return turnTopBottom().turnLeftRight();

            case 270:
                return turnRight90().turnTopBottom();
        }
        throw new IllegalStateException("can't reach here");
    }

    public String toString() {
        StringWriter sw = new StringWriter();
        dump(sw);
        return sw.toString();
    }

    public void dump(Writer writer) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        final int wSize = values.size();
        // final int hSize = values.get(0).size();
        for (int i = 0; i < wSize; i++) {
            List<T> cols = values.get(i);
            sb.append("Row ").append(i).append(": ").append(cols.toString()).append("\r\n");
        }
        sb.append("]\n");
        try {
            writer.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T[] toArray() {
        return toArray(null);
    }

    /**
     * make Two-dimensional matrix to One-dimensional matrix.
     * for usage. see {@linkplain #turnRight90()}.
     *
     * @param out the out array . can be null
     * @return the One-dimensional matrix.
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(@Nullable T[] out) {
        if (getRowCount() == 0 || getColumnCount() == 0) {
            return null;
        }
        int w = getRowCount();
        int h = getColumnCount();
        if (out == null) {
            out = (T[]) Array.newInstance(values.get(0).get(0).getClass(), w * h);
        }
        //Equivalent to right rotation 90
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                out[i * w + j] = values.get(j).get(i);
            }
        }
        return out;
    }

    /**
     * copy the matrix to new matrix.
     *
     * @return the matrix
     */
    public Matrix2<T> copy() {
        ArrayList<List<T>> lists = new ArrayList<>();
        for (List<T> list : values) {
            lists.add(new ArrayList<>(list));
        }
        return new Matrix2<>(lists);
    }

    /**
     * the average callback
     * @param <T> the element type
     */
    public interface AverageCallback<T> {
        /**
         * compute the average
         *
         * @param total the total value
         * @param count the count to average
         * @return the average result
         */
        T average(T total, double count);
    }

    /**
     * the convolution callback
     *
     * @param <T> the element type of current matrix
     * @param <C> the type of convolution core
     * @param <R> the result type matrix
     */
    public interface ConvolutionCallback<T, C, R> {
        /**
         * compute the convolution .
         *
         * @param t the element from current matrix
         * @param c the element from convolution core
         * @return the result
         */
        R multiple(T t, C c);
    }

    /**
     * the element provider.
     *
     * @param <T> the element type
     */
    public interface ElementProvider<T> {

        /**
         *  provide the element
         * @param wIndex the row index
         * @param hIndex the column index
         * @param param the extra param
         * @return the element
         */
        T provide(int wIndex, int hIndex, Object param);

        /**
         * create element provider from matrix
         *
         * @param mat the matrix
         * @param <T> the element type
         * @return the element provider
         */
        static <T> Matrix2.ElementProvider<T> ofMatrix(Matrix2<T> mat) {
            return new Matrix2.ElementProvider<T>() {
                @Override
                public T provide(int wIndex, int hIndex, Object param) {
                    return mat.getRawValues().get(wIndex).get(hIndex);
                }
            };
        }
        static Matrix2.ElementProvider<Integer> ofIntArrayArray(int[][] mat){
            return new ElementProvider<Integer>() {
                @Override
                public Integer provide(int wIndex, int hIndex, Object param) {
                    return mat[wIndex][hIndex];
                }
            };
        }
        static Matrix2.ElementProvider<Float> ofFloatArrayArray(float[][] mat){
            return new ElementProvider<Float>() {
                @Override
                public Float provide(int wIndex, int hIndex, Object param) {
                    return mat[wIndex][hIndex];
                }
            };
        }
        static Matrix2.ElementProvider<Double> ofDoubleArrayArray(double[][] mat){
            return new ElementProvider<Double>() {
                @Override
                public Double provide(int wIndex, int hIndex, Object param) {
                    return mat[wIndex][hIndex];
                }
            };
        }
    }
}
