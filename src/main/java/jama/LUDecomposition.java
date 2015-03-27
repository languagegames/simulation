package jama;

class LUDecomposition implements java.io.Serializable {

	private final double[][] LU;
	private final int m, n;
	private int pivsign;
	private final int[] piv;

	LUDecomposition (final Matrix A) {

		LU = A.getArrayCopy();
		m = A.getRowDimension();
		n = A.getColumnDimension();
		piv = new int[m];
		for (int i = 0; i < m; i++) {
			piv[i] = i;
		}
		pivsign = 1;
		double[] LUrowi;
		final double[] LUcolj = new double[m];

		for (int j = 0; j < n; j++) {

			for (int i = 0; i < m; i++) {
				LUcolj[i] = LU[i][j];
			}

			for (int i = 0; i < m; i++) {
				LUrowi = LU[i];

				final int kmax = Math.min(i,j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++) {
					s += LUrowi[k]*LUcolj[k];
				}

				LUrowi[j] = LUcolj[i] -= s;
			}

			int p = j;
			for (int i = j+1; i < m; i++) {
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
					p = i;
				}
			}
			if (p != j) {
				for (int k = 0; k < n; k++) {
					final double t = LU[p][k]; LU[p][k] = LU[j][k]; LU[j][k] = t;
				}
				final int k = piv[p]; piv[p] = piv[j]; piv[j] = k;
				pivsign = -pivsign;
			}

			if (j < m & LU[j][j] != 0.0) {
				for (int i = j+1; i < m; i++) {
					LU[i][j] /= LU[j][j];
				}
			}
		}
	}

	private boolean isNonsingular () {
		for (int j = 0; j < n; j++) {
			if (LU[j][j] == 0) {
				return false;
			}
		}
		return true;
	}

	double det () {
		if (m != n) {
			throw new IllegalArgumentException("Matrix must be square.");
		}
		double d = pivsign;
		for (int j = 0; j < n; j++) {
			d *= LU[j][j];
		}
		return d;
	}

	/** Solve A*X = B
   @param  B   A Matrix with as many rows as A and any number of columns.
   @return     X so that L*U*X = B(piv,:)
   @exception  IllegalArgumentException Matrix row dimensions must agree.
   @exception  RuntimeException  Matrix is singular.
	 */

	Matrix solve (final Matrix B) {
		if (B.getRowDimension() != m) {
			throw new IllegalArgumentException("Matrix row dimensions must agree.");
		}
		if (!isNonsingular()) {
			throw new RuntimeException("Matrix is singular.");
		}

		final int nx = B.getColumnDimension();
		final Matrix Xmat = B.getMatrix(piv,0,nx-1);
		final double[][] X = Xmat.getArray();

		for (int k = 0; k < n; k++) {
			for (int i = k+1; i < n; i++) {
				for (int j = 0; j < nx; j++) {
					X[i][j] -= X[k][j]*LU[i][k];
				}
			}
		}
		for (int k = n-1; k >= 0; k--) {
			for (int j = 0; j < nx; j++) {
				X[k][j] /= LU[k][k];
			}
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < nx; j++) {
					X[i][j] -= X[k][j]*LU[i][k];
				}
			}
		}
		return Xmat;
	}
	private static final long serialVersionUID = 1;
}
