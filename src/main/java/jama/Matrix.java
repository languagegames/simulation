package jama;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Matrix implements Cloneable, java.io.Serializable {

	private final double[][] A;
	private final int m, n;

	Matrix (final int m, final int n) {
		this.m = m;
		this.n = n;
		A = new double[m][n];
	}

	public Matrix (final double[][] A) {
		m = A.length;
		n = A[0].length;
		for (int i = 0; i < m; i++) {
			if (A[i].length != n) {
				throw new IllegalArgumentException("All rows must have the same length.");
			}
		}
		this.A = A;
	}

	Matrix (final double[][] A, final int m, final int n) {
		this.A = A;
		this.m = m;
		this.n = n;
	}

	public Matrix removeFirstRow() {
		final double[][] vals = Arrays.copyOfRange(A, 1, m);
		return new Matrix(vals);
	}

	public Matrix vertCat(final Matrix other) {
		final double[][] result = ArrayUtils.addAll(A, other.A);
		return new Matrix(result);
	}

	public Matrix covariance() {
		final double[][] vals = new double[m][n];
		for (int i=0; i<m; i++) {
			for (int j=0; j<n; j++) {
				vals[i][j] = A[i][j] - mean(j);
			}
		}
		final Matrix subtractedMean = new Matrix(vals);
		final Matrix transpose = subtractedMean.transpose();
		return transpose.times(subtractedMean).times(1./(m-1));
	}

	public Matrix mean() {
		final double[][] mean = new double[1][n];
		for (int j=0; j<n; j++) {
			mean[0][j] = mean(j);
		}
		return new Matrix(mean);
	}

	private double mean(final int j) {
		double sum = 0;
		for (int i=0; i<m; i++) {
			sum += A[i][j];
		}
		return sum / m;
	}

	@Override
	public Object clone () {
		final Matrix X = new Matrix(m,n);
		final double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return X;
	}

	double[][] getArray () {
		return A;
	}

	double[][] getArrayCopy () {
		final double[][] C = new double[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j];
			}
		}
		return C;
	}

	public int getRowDimension () {
		return m;
	}

	public int getColumnDimension () {
		return n;
	}

	public double get (final int i, final int j) {
		return A[i][j];
	}

	Matrix getMatrix (final int i0, final int i1, final int j0, final int j1) {
		final Matrix X = new Matrix(i1-i0+1,j1-j0+1);
		final double[][] B = X.getArray();
		try {
			for (int i = i0; i <= i1; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i-i0][j-j0] = A[i][j];
				}
			}
		} catch(final ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	Matrix getMatrix (final int[] r, final int j0, final int j1) {
		final Matrix X = new Matrix(r.length,j1-j0+1);
		final double[][] B = X.getArray();
		try {
			for (int i = 0; i < r.length; i++) {
				for (int j = j0; j <= j1; j++) {
					B[i][j-j0] = A[r[i]][j];
				}
			}
		} catch(final ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Submatrix indices");
		}
		return X;
	}

	public Matrix transpose () {
		final Matrix X = new Matrix(n,m);
		final double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[j][i] = A[i][j];
			}
		}
		return X;
	}

	public Matrix minus (final Matrix B) {
		checkMatrixDimensions(B);
		final Matrix X = new Matrix(m,n);
		final double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = A[i][j] - B.A[i][j];
			}
		}
		return X;
	}

	public Matrix times (final double s) {
		final Matrix X = new Matrix(m,n);
		final double[][] C = X.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				C[i][j] = s*A[i][j];
			}
		}
		return X;
	}

	public Matrix times (final Matrix B) {
		if (B.m != n) {
			throw new IllegalArgumentException("Matrix inner dimensions must agree.");
		}
		final Matrix X = new Matrix(m,B.n);
		final double[][] C = X.getArray();
		final double[] Bcolj = new double[n];
		for (int j = 0; j < B.n; j++) {
			for (int k = 0; k < n; k++) {
				Bcolj[k] = B.A[k][j];
			}
			for (int i = 0; i < m; i++) {
				final double[] Arowi = A[i];
				double s = 0;
				for (int k = 0; k < n; k++) {
					s += Arowi[k]*Bcolj[k];
				}
				C[i][j] = s;
			}
		}
		return X;
	}

	private Matrix solve (final Matrix B) {
		return (m == n ? (new LUDecomposition(this)).solve(B) :
			(new QRDecomposition(this)).solve(B));
	}

	public Matrix inverse () {
		return solve(identity(m,m));
	}

	public double det () {
		return new LUDecomposition(this).det();
	}

	private static Matrix identity (final int m, final int n) {
		final Matrix A = new Matrix(m,n);
		final double[][] X = A.getArray();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				X[i][j] = (i == j ? 1.0 : 0.0);
			}
		}
		return A;
	}

	private void checkMatrixDimensions (final Matrix B) {
		if (B.m != m || B.n != n) {
			throw new IllegalArgumentException("Matrix dimensions must agree.");
		}
	}

	private static final long serialVersionUID = 1;

	@Override
	public String toString() {
		return Arrays.deepToString(A);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
