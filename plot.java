import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class plot {

    public static void main(String[] args) throws Exception {
        /*plot graph=new plot(1000,800,new int[]{255,255,255});
        graph.scale(-100,-100,900,700);
        graph.show("111","111","111");*/


        draw_levelset();
        gradient_descent(new double[]{10,10},0.01);
        gradient_descent_optimal(new double[]{10,10});
        Newton_descent(new double[]{10,10});
        gradient_descent_LAB(new double[]{1,1,1,  1,1,1,   1,1,1,  1,1,1},0.001);
        /**/

        conjugate_descent(new double[]{1,1,1,  1,1,1,   1,1,1,  1,1,1});
    }



    public static double[][] gradient_descent(double[] startx, double a) throws IOException {
        double tolerance=0.001;
        double[] x=new double[]{startx[0],startx[1]};
        double[] p=new double[2];
        ArrayList<double[]> record=new ArrayList<>();
        record.add(new double[]{x[0],x[1],x[0]*x[0]-x[0]*x[1]+3*x[1]*x[1]+5});
        double y=0;
        double p_sum=0;
        do{
            p[0]=(2*x[0]-x[1]);
            p[1]=(-x[0]+6*x[1]);
            x[0] -= a*p[0];
            x[1] -= a*p[1];
            y=x[0]*x[0]-x[0]*x[1]+3*x[1]*x[1]+5;
            record.add(new double[]{x[0],x[1],y});
            p_sum=Math.sqrt(p[0]*p[0]+p[1]*p[1]);
            System.out.println(x[0]+"___"+x[1]+"  ----->  "+y+"   ____gradient__"+p_sum);
        }while(p_sum>=tolerance);

        record_draw_plot(record,"plot for Gradient descent for f(x1,x2)=x1^2-x1x2+3x2&2+5","iteration/times","f(x1,x2)");
        record_draw_trace(record,"trace for Gradient descent of optimal rate for f(x1,x2)=x1^2-x1x2+3x2&2+5","x","y");

        return record.toArray(new double[record.size()][]);
    }

    public static double[][] gradient_descent_optimal(double[] startx) throws IOException {
        double tolerance=0.001;
        double[] x=new double[]{startx[0],startx[1]};
        double[] p=new double[2];
        ArrayList<double[]> record=new ArrayList<>();
        record.add(new double[]{x[0],x[1],x[0]*x[0]-x[0]*x[1]+3*x[1]*x[1]+5});
        double y=0;
        double p_sum=0;
        double a=0;
        do{
            p[0]=(2*x[0]-x[1]);
            p[1]=(-x[0]+6*x[1]);
            a=(5*x[0]*x[0]-16*x[0]*x[1]+37*x[1]*x[1])/(18*x[0]*x[0]-106*x[0]*x[1]+230*x[1]*x[1]);
            x[0] -= a*p[0];
            x[1] -= a*p[1];
            y=x[0]*x[0]-x[0]*x[1]+3*x[1]*x[1]+5;
            record.add(new double[]{x[0],x[1],y});
            p_sum=Math.sqrt(p[0]*p[0]+p[1]*p[1]);
            System.out.println(x[0]+"___"+x[1]+"  ----->  "+y+"   ____gradient__"+p_sum);
        }while(p_sum>=tolerance);

        record_draw_plot(record,"plot for Gradient descent of optimal rate for f(x1,x2)=x1^2-x1x2+3x2&2+5","iteration/times","f(x1,x2)");
        //
        System.out.println("draw trace");
        record_draw_trace(record,"trace for Gradient descent of optimal rate for f(x1,x2)=x1^2-x1x2+3x2&2+5","x","y");

        return record.toArray(new double[record.size()][]);
    }

    public static double[][] Newton_descent(double[] startx) throws IOException {
        double tolerance=0.001;
        double[] x=new double[]{startx[0],startx[1]};
        double[] p=new double[2];
        ArrayList<double[]> record=new ArrayList<>();
        record.add(new double[]{x[0],x[1],x[0]*x[0]-x[0]*x[1]+3*x[1]*x[1]+5});
        double y=0;
        double p_sum=0;
        double a=0;
        do{
            p[0]=x[0];
            p[1]=x[1];
            x[0] -= p[0];
            x[1] -= p[1];
            y=x[0]*x[0]-x[0]*x[1]+3*x[1]*x[1]+5;
            record.add(new double[]{x[0],x[1],y});
            p_sum=Math.sqrt(p[0]*p[0]+p[1]*p[1]);
            System.out.println(x[0]+"___"+x[1]+"  ----->  "+y+"   ____gradient__"+p_sum);
        }while(p_sum>=tolerance);

        record_draw_plot(record,"plot for Newton descent for f(x1,x2)=x1^2-x1x2+3x2&2+5","iteration/times","f(x1,x2)");

        record_draw_trace(record,"trace for Newton descent for f(x1,x2)=x1^2-x1x2+3x2&2+5","x","y");

        return record.toArray(new double[record.size()][]);
    }


    public static double[][] gradient_descent_LAB(double[] startx, double a) throws IOException {
        double tolerance=0.1;
        double[] x=startx.clone();
        double[] record_single=null;
        double[] p=null;
        ArrayList<double[]> record=new ArrayList<>();
        double y=0;

        // add first one
        record_single=new double[x.length+1];
        for (int i=0,il=x.length;i<il;i++ ) {
            record_single[i]=x[i];
        }
        y=LAB(x);
        record_single[x.length]=y;
        record.add(record_single);

        double p_sum=0;
        do{
            p=LAB_gradient(x);
            record_single=new double[x.length+1];
            for (int i=0,il=p.length;i<il;i++ ) {
                x[i] -= a*p[i];
                record_single[i]=x[i];
            }
            y=LAB(x);
            record_single[x.length]=y;
            record.add(record_single);

            // p_sum
            p_sum=0;
            for (double p_i : p) {
                p_sum += p_i*p_i;
            }
            p_sum=Math.sqrt(p_sum);

            // show
            for (double p_i : p) {
                System.out.print(p_i+"___");
            }
            System.out.println();
            System.out.println("  ----->  "+y+"   ____gradient__"+p_sum);
        }while(p_sum>=tolerance);

        record_draw_plot(record,"plot for gradient descent for L(A,B)","iteration/times","L(A,B)");

        return record.toArray(new double[record.size()][]);
    }
    public static double[] LAB_gradient(double[] AB){
        double[] gradient=new double[AB.length];
        /*gradient[0]=6*AB[0]+4*AB[1]+14*AB[2]+2*AB[9];
        gradient[1]=4*AB[0]+24*AB[1]+20*AB[2]+12*AB[9];
        gradient[2]=14*AB[0]+20*AB[1]+38*AB[2]+10*AB[9];
        gradient[3]=14+6*AB[3]+4*AB[4]+14*AB[6]+2*AB[10];
        gradient[4]=20+4*AB[3]+24*AB[4]+20*AB[6]+12*AB[10];
        gradient[5]=38+14*AB[3]+20*AB[4]+38*AB[6]+10*AB[10];
        gradient[6]=6*AB[6]+4*AB[7]+14*AB[8]+2*AB[11];
        gradient[7]=-16+4*AB[6]+24*AB[7]+20*AB[8]+12*AB[11];
        gradient[8]=-8+14*AB[6]+20*AB[7]+38*AB[8]+10*AB[11];
        gradient[9]=2*AB[0]+12*AB[1]+10*AB[2]+6*AB[9];
        gradient[9]=10+2*AB[3]+12*AB[4]+10*AB[5]+6*AB[10];
        gradient[9]=-8+2*AB[6]+12*AB[7]+10*AB[8]+6*AB[11];*/


        double origin=LAB(AB);
        double dat=0.0000001;
        for (int i=0;i<AB.length ;i++ ) {
            AB[i] += dat;
            gradient[i]= (LAB(AB)-origin)/dat;
            AB[i] -= dat;
        } /**/

        return gradient;
    }

    public static double LAB(double[] AB){
        double sum=0;
        sum += LAB_single(AB,new double[]{1,2,3},new double[]{-1,-3,1});
        sum += LAB_single(AB,new double[]{1,2,3},new double[]{1,-3,1});
        sum += LAB_single(AB,new double[]{-1,2,-1},new double[]{0,1,2});
        return sum;
    }
    public static double LAB_single(double[] AB,double[] x,double[] y){
        double sum=0;
        double term=0;
        System.out.println("Start !!!");
        for (int i=0;i<AB.length ;i++ ) {
            System.out.print(AB[i]+"___");
        }
        System.out.println();
        for (int i=0;i<x.length ;i++ ) {
            System.out.print(x[i]+"___");
        }
        System.out.println();
        for (int i=0;i<y.length ;i++ ) {
            System.out.print(y[i]+"___");
        }
        System.out.println();
        System.out.println("End !!!");
        term=AB[0]*x[0]+AB[1]*x[1]+AB[2]*x[2]+AB[9]-y[0];
        System.out.println("term___"+term*term);
        sum +=term*term;
        term=AB[3]*x[0]+AB[4]*x[1]+AB[5]*x[2]+AB[10]-y[1];
        System.out.println("term___"+term*term);
        sum +=term*term;
        term=AB[6]*x[0]+AB[7]*x[1]+AB[8]*x[2]+AB[11]-y[2];
        System.out.println("term___"+term*term);
        sum +=term*term;
        return sum;
    }

    public static double[][] conjugate_descent(double[] startx) throws Exception {
        double[][] Quadra={
                {6,4,14, 0,0,0, 0,0,0, 2,0,0},
                {4,24,20, 0,0,0, 0,0,0, 12,0,0},
                {14,20,38,0,0,0, 0,0,0, 10,0,0},
                {0,0,0,  6,4,14, 0,0,0, 0,2,0},
                {0,0,0,  4,24,20, 0,0,0, 0,12,0},
                {0,0,0,  14,20,38, 0,0,0, 0,10,0},
                {0,0,0, 0,0,0, 6,4,14, 0,0,2},
                {0,0,0, 0,0,0, 4,24,20, 0,0,12},
                {0,0,0, 0,0,0, 14,20,38, 0,0,10},
                {2,12,10, 0,0,0, 0,0,0, 6,0,0},
                {0,0,0, 2,12,10, 0,0,0, 0,6,0},
                {0,0,0,  0,0,0,  2,12,10, 0,0,6}};

        double tolerance=0.1;
        double[] x=startx.clone();
        double[] record_single=null;
        double[] p=null;
        ArrayList<double[]> record=new ArrayList<>();
        double y=0;

        // add first one
        record_single=new double[x.length+1];
        for (int i=0,il=x.length;i<il;i++ ) {
            record_single[i]=x[i];
        }
        y=LAB(x);
        record_single[x.length]=y;
        record.add(record_single);

        // first step
        p=LAB_gradient(x);

        double a;
        double p_sum;
        do{

            a=-Matrix.vector_dot(LAB_gradient(x),p)/Matrix.vector_dot(Matrix.vectormatrix(p,Quadra),p);

            record_single=new double[x.length+1];
            for (int i=0,il=p.length;i<il;i++ ) {
                x[i] -= a*p[i];
                record_single[i]=x[i];
            }
            System.out.println("calculate!");
            y=LAB(x);
            record_single[x.length]=y;
            record.add(record_single);

            // p_sum
            p_sum=0;
            for (double p_i : p) {
                p_sum += p_i*p_i;
            }
            p_sum=Math.sqrt(p_sum);

            // show
            for (double p_i : p) {
                System.out.print(p_i+"___");
            }
            System.out.println();
            System.out.println("  ----->  "+y+"   ____gradient__"+p_sum);

            // next p
            if (p_sum>=tolerance) {
                p=fit_vector(Matrix.vectormatrix(p,Quadra));
            }
        }while(p_sum>=tolerance);


        //record_draw_plot(record,"plot for Gradient descent for f(x1,x2)=x1^2-x1x2+3x2&2+5","iteration/times","f(x1,x2)");
        //record_draw_trace(record,"trace for Gradient descent of optimal rate for f(x1,x2)=x1^2-x1x2+3x2&2+5","x","y");

        return record.toArray(new double[record.size()][]);
    }

    public static double[] conjugated_vector(double[] pre_vector,double[][] matrix) throws Exception {
        double[] coefficient=Matrix.vectormatrix(pre_vector,matrix);
        return fit_vector(coefficient);
    }
    // some vector that fitting the linear equation
    public static double[] fit_vector(double[] coefficient) throws Exception {
        double[] res=new double[coefficient.length];
        double max=0;
        for (int i=0;i<coefficient.length ;i++ ) {
            if(max<Math.abs(coefficient[i]))
                max=Math.abs(coefficient[i]);
        }
        // last one whose coefficient >0
        int last=-1;
        //System.out.println(max);
        for (int i=0;i<coefficient.length ;i++ ) {
            //System.out.print(coefficient[i]+"___"+coefficient[i]/max+" , ");
            if(Math.abs(coefficient[i]/max)<0.00001){
                res[i]=0;
            }else{
                last=i;
            }
        }
        System.out.println();
        if (last==-1) {
            throw new Exception("error");
        }
        // set value
        double sum=0;
        for (int i=0;i<last ;i++ ) {
            if(coefficient[i]/max>0.000000001){
                res[i]=1;
                sum += coefficient[i];
            }
        }
        // the last one's value
        res[last]=-sum/coefficient[last];
        /*for (int i=0;i<coefficient.length ;i++ ) {
            System.out.print(res[i]+"___");
        }
        System.out.println();*/
        return res;
    }









    // special for learning draw plot from record !
    public static void record_draw_plot(ArrayList<double[]> record,String title,String x_name,String y_name) throws IOException {
        double[] x=new double[record.size()];
        double[] y=new double[record.size()];
        int record_l=record.get(0).length;
        for (int i=0,il=record.size();i<il;i++) {
            x[i]=i;
            y[i]=record.get(i)[record_l-1];
        }

        plot graph=new plot(800,800,new int[]{255,255,255});
        graph.scale_fromdata(x,y);
        int[][] data=graph.data_xy(x,y);
        graph.graph_ployline(data,1);
        graph.show(title,x_name,y_name);
    }


    // special for learning draw trace from record !
    public static void record_draw_trace(ArrayList<double[]> record,String title,String x_name,String y_name) throws IOException {
        double[] x=new double[record.size()];
        double[] y=new double[record.size()];
        int record_l=record.get(0).length;
        for (int i=0,il=record.size();i<il;i++) {
            x[i]=record.get(i)[0];
            y[i]=record.get(i)[1];
        }

        plot graph=new plot(800,800,new int[]{255,255,255});
        graph.scale_fromdata(x,y);
        int[][] data=graph.data_xy(x,y);
        graph.graph_traceline(data);
        graph.show(title,x_name,y_name);
    }

    // special for learning draw plot from xy coordinator !
    public static void xy_draw_plot(double[][] xy,String title,String x_name,String y_name) throws IOException {
        int l=xy[0].length;
        double[] x=xy[0];
        double[] y=xy[1];

        plot graph=new plot(800,800,new int[]{255,255,255});
        graph.scale_fromdata(x,y);
        int[][] data=graph.data_xy(x,y);
        graph.graph_ployline(data,1);
        graph.show(title,x_name,y_name);
    }














    public static void draw_levelset() throws IOException {
        // 1
        plot graph=new plot(800,800,new int[]{255,255,255});
        graph.scale(-10,-10,10,10);
        graph.draw_levelset_1();
        graph.show("plot for f1(x1,x2)=(x1-x2)^2","x","y");

        // 2
        graph=new plot(800,800,new int[]{255,255,255});
        graph.scale(-10,-10,10,10);
        graph.draw_levelset_2();
        graph.show("plot for f1(x1,x2)=x1^2-3*x2^2","x","y");


        // 3
        for (int i=0;i<=10 ;i+=2 ) {
            graph=new plot(800,800,new int[]{255,255,255});
            graph.scale(-10,-10,10,10);
            graph.draw_levelset_3(i);
            graph.show("plot for f1(x1,x2)=x1^2-3*x2^2    when x3=+/-"+i,"x","y");
        }
    }


    public void draw_levelset_1() throws IOException {
        double tolerance = 0.001;

        double[] point;
        double x1, x2;
        double f;
        double error;
        for (int res=0;res<=400;res +=2) {
            for (int i = 0; i < g_w; i++) {
                for (int j = 0; j < g_h; j++) {
                    //System.out.println(i+"___"+j);
                    point = xy_data(new int[]{i, j});
                    x1 = point[0];
                    x2 = point[1];
                    f = (x1 - x2) * (x1 - x2);
                    error=Math.abs(f-res);
                    if(error<tolerance)
                    /*error = Math.abs(f - (int) f);
                    if (Math.min(error, 1 - error) < tolerance)*/
                        graph_pointxy(i, j, 1, new int[]{255, 0, 0});
                }
            }
        }
    }

    public void draw_levelset_2() throws IOException {
        double tolerance = 0.2;

        double[] point;
        double x1, x2;
        double f;
        double error;
        for (int res=-300;res<=100;res+=10)
        for (int i = 0; i < g_w; i++) {
            for (int j = 0; j < g_h; j++) {
                //System.out.println(i+"___"+j);
                point = xy_data(new int[]{i, j});
                x1 = point[0];
                x2 = point[1];
                f = x1*x1-3*x2*x2;
                error=Math.abs(f-res);
                if(error<tolerance)
                /*error = Math.abs(f - (int)f);
                if (Math.min(error,1-error) < tolerance)*/
                    graph_pointxy(i, j, 1, new int[]{255, 0, 0});
            }
        }
    }

    public void draw_levelset_3(int x3) throws IOException {
        double tolerance = 0.2;

        double[] point;
        double x1, x2;
        double f;
        double error;
        int[] num=new int[]{0,3,6,10,20,30,40,50,60,70,80,90,100,150,200,300,400,500,600,700,800,900,1000};
        for (int k=0,res=num[k];k<num.length-1;k++,res=num[k])
            for (int i = 0; i < g_w; i++) {
                for (int j = 0; j < g_h; j++) {
                    //System.out.println(i+"___"+j);
                    point = xy_data(new int[]{i, j});
                    x1 = point[0];
                    x2 = point[1];
                    f = x1*x1+5*x2*x2+3*x3*x3;
                    error=Math.abs(f-res);
                    if(error<tolerance)
                /*error = Math.abs(f - (int)f);
                if (Math.min(error,1-error) < tolerance)*/
                        graph_pointxy(i, j, 1, new int[]{255, 0, 0});
                }
            }
    }


    public int g_w = 0;
    public int g_h = 0;
    public int[][][] graph=null;

    public plot(int w, int h, int[] backgd) {
        g_w = w;
        g_h = h;
        graph = new int[g_w][g_h][3];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                for (int k = 0; k < 3; k++) {
                    graph[i][j][k] = backgd[k];
                }
            }
        }
    }

    public double x_1;
    public double y_1;
    public double x_2;
    public double y_2;
    public double datx;
    public double daty;
    public void scale(double x1,double y1,double x2,double y2){
        x_1=x1;
        y_1=y1;
        x_2=x2;
        y_2=y2;
        datx = x_2 - x_1;
        daty = y_2 - y_1;
        if(x1<=0 && x2>=0){ // y-axis
            graph_line_relative(2,new int[]{0,0,0},0,y_1,0,y_2);
        }
        if(y1<=0 && y2>=0){ // x-axis
            graph_line_relative(2,new int[]{0,0,0},x_1,0,x_2,0);
        }
    }

    public void scale_fromdata(double[] x, double[] y){
        int l = x.length;
        double max_x = x[l - 1];
        double min_x = x[0];
        double max_y = y[0];
        double min_y = y[0];
        for (int i = 0; i < l; i++) {
            if (max_y < y[i])
                max_y = y[i];
            if (min_y > y[i])
                min_y = y[i];

            if (max_x < x[i])
                max_x = x[i];
            if (min_x > x[i])
                min_x = x[i];
        }
        double datx=(max_x-min_x)/20;
        double daty=(max_y-min_y)/20;
        //System.out.println(min_x+"___"+min_y+"___"+max_x+"___"+max_y);
        scale(min_x-datx,min_y-daty,max_x+datx,max_y+daty);
        //System.out.println(x_1+"___"+y_1+"___"+x_2+"___"+y_2);
    }




    // basic
    // point
    public void graph_pointxy(int x, int y, int r, int[] pointcolor) {
        int w = graph.length;
        int h = graph[0].length;
        for (int i = x - r; i <= x + r; i++) {
            if (i >= 0 && i < w)
                for (int j = y - r; j <= y + r; j++) {
                    if (j >= 0 && j < h)
                        for (int k = 0; k < 3; k++) {
                            graph[i][j][k] = pointcolor[k];
                        }
                }
        }
    }
    // line
    public void graph_line(int x1, int y1, int x2, int y2, int r, int[] linecolor) {
        //System.out.println("drawing the line___"+x1+"__"+y1+"___"+x2+"___"+y2);
        int datx = x2 - x1;
        int daty = y2 - y1;
        if (datx == 0 && daty == 0) { // single point
            graph_pointxy(x1, y1, r, linecolor);
        } else if (Math.abs(datx) >= Math.abs(daty)) { // according to x
            if (datx > 0) {
                for (int i = x1; i <= x2; i++) {
                    //System.out.println(i+"___"+(y1+i*(daty)/datx));
                    graph_pointxy(i, y1 + (i - x1) * (daty) / datx, r, linecolor);
                }
            } else
                graph_line(x2, y2, x1, y1, r, linecolor);
        } else { // according to y
            if (daty > 0) {
                for (int j = y1; j <= y2; j++) {
                    graph_pointxy(x1 + (j - y1) * datx / daty, j, r, linecolor);
                }
            } else
                graph_line(x2, y2, x1, y1, r, linecolor);
        }
    }

    public void graph_ployline(int[][] xy,int type) {
        graph_polyline(xy, 3, 1, new int[]{255, 0, 0}, new int[]{0, 255, 0},type);
    }
    public void graph_traceline(int[][] xy) {
        graph_polyline(xy, 3, 1, new int[]{255, 0, 0}, new int[]{0, 255, 0},2);
    }
    public void graph_traceline(int[][] xy,int type) {
        graph_polyline(xy, 3, 1, new int[]{255, 0, 0}, new int[]{0, 255, 0},type);
    }
    public void graph_polyline(int[][] xy, int r_point, int r_line, int[] color_point, int[] color_line) {
        graph_polyline(xy,r_point,r_line,color_point,color_line,1);
    }
    public void graph_polyline(int[][] xy, int r_point, int r_line, int[] color_point, int[] color_line,int type) {
        int[] x = xy[0];
        int[] y = xy[1];
        int l = x.length;
        // point
        double sqrt2=Math.sqrt(2)/2*20;
        for (int i = 0; i < l; i++) {
            graph_pointxy(x[i], y[i], r_point, color_point);
        }

        // line
        if(type>=1)
        for (int i = 0; i + 1 < l; i++) {
            graph_line(x[i], y[i], x[i + 1], y[i + 1], r_line, color_line);
        }

        // arrow
        if(type >=2)
        for (int i = 0; i < l; i++) {
            if (i!=0) {
                double datx=x[i]-x[i-1];
                double daty=y[i]-y[i-1];
                double sum=Math.sqrt(datx*datx+daty*daty);
                double sin=daty/sum;
                double cos=datx/sum;
                int sin_new=(int)((-sin+cos)*sqrt2);
                int cos_new=(int)((-cos-sin)*sqrt2);
                graph_line(x[i], y[i], x[i]+cos_new, y[i]+sin_new, r_line, color_line);
                sin_new=(int)((-sin-cos)*sqrt2);
                cos_new=(int)((-cos+sin)*sqrt2);
                graph_line(x[i], y[i], x[i]+cos_new, y[i]+sin_new, r_line, color_line);
            }
        }
    }







        // data---> xy
    public int[][] data_xy(double[] x, double[] y) {
        int l = x.length;
        int[][] xy = new int[2][l];
        for (int i = 0; i < l; i++) {
            xy[0][i] = (int) ((x[i] - x_1) * g_w / datx + 0); // x
            xy[1][i] = (int) ((y[i] - y_1) * g_h / daty + 0); // y
        }
        return xy;
    }
    public int[] data_xy(double x,double y){
        return new int[]{(int) ((x - x_1) * g_w / datx + 0), (int) ((y - y_1) * g_h / daty + 0)};
    }

    // data ---> xy ---> image
    public void graph_line_relative(int r, int[] linecolor, double xt1, double yt1, double xt2, double yt2) {
        int[] point1 = data_xy(xt1, yt1);
        int[] point2 = data_xy(xt2, yt2);
        graph_line(point1[0], point1[1], point2[0], point2[1], r, linecolor);
    }

    // xy ---> data
    public double[] xy_data(int[] data) {
        return new double[]{data[0] * datx / g_w + x_1, data[1] * daty / g_h + y_1};
    }














    // add frame/border and revert it to show properly
    public static BufferedImage img=null;
    public static Graphics graphics=null;
    public void show(String title,String x,String y) throws IOException {
        int left=50;
        int right=50;
        int up=50;
        int down=50;
        int[][][] color=graph_frame(graph,left,right,down,up);
        colorarr_img(color);
        // other string, words
        Graphics g=img.getGraphics();
        int fontsize=20;
        g.setFont(new Font("Arial", Font.BOLD, fontsize));
        g.setColor(Color.black);
        // title
        g.drawString(title,(color.length-(int)(title.length()*fontsize/1.8))/2,up/2);
        // x-axis
        g.drawString(x,color.length-right-(int)(x.length()*fontsize/1.8)/2,color[0].length-down/2);
        g.drawString(((int)x_2)+"",color.length-right-20,color[0].length-down/2+20);
        g.drawString(((int)x_1)+"",left,color[0].length-down/2+20);
        g.drawString(((int)(x_1+x_2)/2)+"",left+graph.length/2,color[0].length-down/2+20);
        // y-axis
        g.drawString(y,left-(int)(y.length()*fontsize/1.8)/2,up/2);
        g.drawString(((int)y_2)+"",left-30,up/2+20);
        g.drawString(((int)y_1)+"",left-30,color[0].length-down);
        g.drawString(((int)(y_1+y_2)/2)+"",left-30,up+graph[0].length/2);

        img_path_openshow();
    }

    // last step !!!
    public static int[][][] graph_frame(int[][][] color, int left, int right, int down, int up) {
        int w = color.length;
        int h = color[0].length;
        int[][][] color_new = new int[w + left + right][h + up + down][];
        // graph
        for (int i = left, il = w + left; i < il; i++) {
            for (int j = up, jl = up + h; j < jl; j++) {
                //System.out.println((i-left)+"___"+(h-(j-up)));
                color_new[i][j] = color[i - left][h - 1 - (j - up)];
            }
        }

        // other
        // left
        int[] backgd = new int[]{180, 180, 180};
        for (int i = 0; i < left; i++) {
            for (int j = 0, jl = h + up + down; j < jl; j++) {
                color_new[i][j] = backgd;
            }
        }
        // right
        for (int i = w + left, il = w + left + right; i < il; i++) {
            for (int j = 0, jl = h + up + down; j < jl; j++) {
                color_new[i][j] = backgd;
            }
        }
        // up and down
        for (int i = left, il = left + w; i < il; i++) {
            for (int j = 0; j < up; j++) {
                color_new[i][j] = backgd;
            }
            for (int j = up + h, jl = up + h + down; j < jl; j++) {
                color_new[i][j] = backgd;
            }
        }
        return color_new;
    }

    public static String plotpath=null;
    public static void img_path_openshow() throws IOException {
        UUID uuid = UUID.randomUUID();
        //if (plotpath==null)
            plotpath  = uuid.toString() + ".jpg"; // imagedir + "\\" +
        try {
            ImageIO.write(img, "bmp", new File(plotpath));// jpeg may lose some information; bmp
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler " + plotpath);
    }

    public static void colorarr_img(int[][][] color) throws IOException {
        pixelarr_img(colorarr_pixelarr(color));
    }
    public static void pixelarr_img(int[][] pixel) {
        int lx = pixel.length;
        int ly = pixel[0].length;

        img = new BufferedImage(pixel.length,pixel[0].length, BufferedImage.TYPE_INT_BGR);
        for (int i = 0; i < lx; i++)
            for (int j = 0; j < ly; j++) {
                img.setRGB(i, j, pixel[i][j]);
            }
    }

    public static int[][] colorarr_pixelarr(int[][][] color) {
        int lx = color.length;
        int ly = color[0].length;
        int[][] pixel = new int[lx][ly];
        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                //System.out.println(i+"__"+j+"__"+color[i][j][0]+"__"+color[i][j][1]+"__"+color[i][j][2]);
                Color c = new Color(color[i][j][0], color[i][j][1], color[i][j][2]);
                pixel[i][j] = c.getRGB();
            }
        }

        return pixel;
    }
}
