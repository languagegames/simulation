package utility.matrix;

class QRDecomposition implements java.io.Serializable {

	private final double[][] QR;
	private final int m, n;
	private final double[] Rdiag;

	QRDecomposition (final Matrix A) {
		QR = A.getArrayCopy();
		m = A.getRowDimension();
		n = A.getColumnDimension();
		Rdiag = new double[n];

		for (int k = 0; k < n; k++) {
			double nrm = 0;
			for (int i = k; i < m; i++) {
				nrm = hypot(nrm,QR[i][k]);
			}

			if (nrm != 0.0) {
				if (QR[k][k] < 0) {
					nrm = -nrm;
				}
				for (int i = k; i < m; i++) {
					QR[i][k] /= nrm;
				}
				QR[k][k] += 1.0;

				for (int j = k+1; j < n; j++) {
					double s = 0.0;
					for (int i = k; i < m; i++) {
						s += QR[i][k]*QR[i][j];
					}
					s = -s/QR[k][k];
					for (int i = k; i < m; i++) {
						QR[i][j] += s*QR[i][k];
					}
				}
			}
			Rdiag[k] = -nrm;
		}
	}

	private double hypot(final double a, final double b) {
		double r;
		if (Math.abs(a) > Math.abs(b)) {
			r = b/a;
			r = Math.abs(a)*Math.sqrt(1+r*r);
		} else if (b != 0) {
			r = a/b;
			r = Math.abs(b)*Math.sqrt(1+r*r);
		} else {
			r = 0.0;
		}
		return r;
	}

	boolean isFullRank () {
		for (int j = 0; j < n; j++) {
			if (Rdiag[j] == 0) {
				return false;
			}
		}
		return true;
	}

	Matrix solve (final Matrix B) {
		if (B.getRowDimension() != m) {
			throw new IllegalArgumentException("Matrix row dimensions must agree.");
		}
		if (!isFullRank()) {
			throw new RuntimeException("Matrix is rank deficient.");
		}

		final int nx = B.getColumnDimension();
		final double[][] X = B.getArrayCopy();

		for (int k = 0; k < n; k++) {
			for (int j = 0; j < nx; j++) {
				double s = 0.0;
				for (int i = k; i < m; i++) {
					s += QR[i][k]*X[i][j];
				}
				s = -s/QR[k][k];
				for (int i = k; i < m; i++) {
					X[i][j] += s*QR[i][k];
				}
			}
		}
		for (int k = n-1; k >= 0; k--) {
			for (int j = 0; j < nx; j++) {
				X[k][j] /= Rdiag[k];
			}
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < nx; j++) {
					X[i][j] -= X[k][j]*QR[i][k];
				}
			}
		}
		return (new Matrix(X,n,nx).getMatrix(0,n-1,0,nx-1));
	}
	private static final long serialVersionUID = 1;
}
