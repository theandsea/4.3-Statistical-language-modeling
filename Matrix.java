public class Matrix {

    public static void main(String[] args) throws Exception {
        double[][] a = new double[][]{{1,2,3,4}, {4, 5, 6, 7}, {7, 2, 9, 10}, {10,11,12,14}};
        double[][] reverse=matrix_reverse(a);
        matrix_printout(reverse);
        double[][] check=matrix_matrix(a,reverse);
        matrix_printout(check);
         check=matrix_matrix(reverse,a);
        matrix_printout(check);
    }

    public static double[] vectormatrix(double[] vector, double[][] matrix) {
        int leng = matrix[0].length;
        double[] res = new double[leng];
        for (int i = 0; i < leng; i++) {
            double sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += vector[j] * matrix[j][i];
            }
            res[i] = sum;
        }
        return res;
    }


    public static double[] matrixvector(double[][] matrix,double[] vector) {
        int leng = matrix.length;
        double[] res = new double[leng];
        for (int i = 0; i < leng; i++) {
            double sum = 0;
            for (int j = 0; j < vector.length; j++) {
                sum += matrix[i][j] * vector[j];
            }
            res[i] = sum;
        }
        return res;
    }


    public static double vector_dot(double[] a, double[] b) {
        int leng = a.length;
        double res;
        double sum = 0;
        for (int j = 0; j < a.length; j++) {
            sum += a[j] * b[j];
        }
        res = sum;
        return res;
    }

    public static double[][] vector_cross(double[] a,double[] b){
        int xl=a.length;
        int yl=b.length;
        double[][] res=new double[xl][yl];
        for (int i=0;i<xl;i++ ) {
            for (int j=0;j<yl ;j++ ) {
                res[i][j]=a[i]*b[j];
            }
        }
        return res;
    }

    public static double[][] matrix_matrix(double[][] a, double[][] b) throws Exception {
        int a_h = a.length;
        int a_l = a[0].length;
        int b_h = b.length;
        int b_l = b[0].length;
        if (a_l != b_h)
            throw new Exception("error size");
        double[][] product = new double[a_h][b_l];
        for (int i = 0; i < a_h; i++) {
            for (int j = 0; j < b_l; j++) {
                double sum = 0;
                for (int k = 0; k < a_l; k++) {
                    sum += a[i][k] * b[k][j];
                }
                product[i][j] = sum;
            }
        }
        return product;
    }

    public static double matrix_determinant(double[][] a) throws Exception {
        int l = a.length;
        if (l == 1) {
            return a[0][0];
        } else if (l == 2) {
            return a[0][0] * a[1][1] - a[1][0] * a[0][1];
        } else {
            double[][] son = new double[l - 1][l - 1];
            double sum = 0;
            // gaussian wipe out:



            /*if(l==11 || l==50) {
                    System.out.println("=================new determine===============");
                    System.out.println(i);
                }*/

            for (int i = 0; i < l; i++) {
                // generate son matrix

                int dat = 0;
                for (int ii = 0; ii < son.length; ii++) {
                    if (ii == i)
                        dat = 1;
                    for (int jj = 0; jj < son[ii].length; jj++) {
                        son[ii][jj] = a[ii + dat][jj + 1];
                    }
                }

                // add it to the sum
                double determinant=matrix_determinant(son);
                if (i % 2 == 0)
                    sum += a[i][0] * determinant;
                else
                    sum -= a[i][0] * determinant;
                //System.out.println(a[i][0]+"___"+matrix_determinant(son));
            }
            return sum;
        }
    }

    public static double[][] matrix_reverse(double[][] a) throws Exception {
        int l=a.length;
        if(a[0].length!=l)
            throw new Exception("error");
        double[][] reverse=new double[l][l];
        double denominator=matrix_determinant(a);
        if(Math.abs(denominator)<0.000000000001)
            throw new Exception("cannot inverted____"+denominator);
        double[][] son=new double[l-1][l-1];
        for (int i=0;i<l;i++ ) {
            for (int j=0;j<l ;j++ ) {
                // generate son matrix
                for (int ii = 0, datii = 0; ii < l - 1; ii++) {
                    if (ii == i)
                        datii = 1;
                    for (int jj = 0, datjj = 0; jj < l - 1; jj++) {
                        if (jj == j)
                            datjj = 1;
                        son[ii][jj] = a[ii + datii][jj + datjj];
                    }
                }

                // add to the reverse
                if (i % 2 == 0 ^ j % 2 == 0)
                    reverse[i][j] = -matrix_determinant(son) / denominator;
                else
                    reverse[i][j] = matrix_determinant(son) / denominator;
            }
        }

        double[][] trans=new double[l][l];
        for (int i=0;i<l ;i++ ) {
            for (int j=0;j<l ;j++ ) {
                trans[i][j]=reverse[j][i];
            }
        }

        return trans;
    }





    public static double[] vector_add(double[] a,double[] b,double alpha){
        int l=a.length;
        double[] res=new double[l];
        for (int i=0;i<l ;i++ ) {
            res[i]=a[i]+b[i]*alpha;
        }
        return res;
    }
    public static double[][] matrix_add(double[][] a,double[][] b,double alpha){
        int l=a.length;
        int w=a[0].length;
        double[][] res=new double[l][w];
        for (int i=0;i<l ;i++ ) {
            for (int j=0;j<w ;j++ ) {
                res[i][j]=a[i][j]+b[i][j]*alpha;
            }
        }
        return res;
    }
    public static double[] vector_times(double[] a,double alpha){
        int l=a.length;
        double[] res=new double[l];
        for (int i=0;i<l ;i++ ) {
            res[i]=a[i]*alpha;
        }
        return res;
    }
    public static double[][] matrix_times(double[][] a,double alpha){
        int l=a.length;
        int w=a[0].length;
        double[][] res=new double[l][w];
        for (int i=0;i<l ;i++ ) {
            for (int j=0;j<w ;j++ ) {
                res[i][j]=a[i][j]*alpha;
            }
        }
        return res;
    }
    public static double[] vector_average(double[][] a){
        int l=a[0].length;
        int T=a.length;
        double[] res=new double[l];
        for (int i=0;i<l ;i++ ) {
            res[i]=0;
            for (int t=0;t<T ;t++ ) {
                res[i] += a[t][i];
            }
            res[i] /= T;
        }
        return res;
    }
    public static double[][] matrix_average(double[][][] a){
        int T=a.length;
        int l=a[0].length;
        int w=a[0][0].length;
        double[][] res=new double[l][w];
        for (int i=0;i<l ;i++ ) {
            for (int j=0;j<w ;j++ ) {
                res[i][j]=0;
                for (int t=0;t<T;t++ ) {
                    res[i][j] +=a[t][i][j];
                }
                res[i][j] /=T;
            }
        }
        return res;
    }

    public static double[][] matrix_newunit(int l){
        double[][] res=new double[l][l];
        for (int i=0;i<l ;i++ ) {
            for (int j=0;j<l ;j++ ) {
                if(i==j)
                    res[i][j]=1;
                else
                    res[i][j]=0;
            }
        }
        return res;
    }

    public static void matrix_printout(double[][] a){
        System.out.println("_____matrix_____");
        for (int i=0;i<a.length ;i++ ) {
            for (int j=0;j<a[i].length ;j++ ) {
                System.out.print(a[i][j]+"\t");
            }
            System.out.println();
        }
    }
    public static void matrix_printout(double[] a){
        System.out.println("_____vector_____");
        for (int i=0;i<a.length ;i++ ) {
            System.out.print(a[i]+"\t\t");
        }
        System.out.println();
    }
}
