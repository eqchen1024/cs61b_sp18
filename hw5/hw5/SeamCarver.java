package hw5;
import edu.princeton.cs.algs4.Picture;
public class SeamCarver {
    public Picture content;
    public double[][] energyMatrix;
    public int width;
    public int height;
    public SeamCarver(Picture picture) {
        content = picture;
        width = width();
        height = height();
        energyMatrix = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                energyMatrix[i][j] = energy(i,j);
            }
        }
    }
    public Picture picture() {
        return content;
    }

    public int width() {
        return content.width();
    }
    public int height() {
        return content.height();
    }
    public double energy(int x, int y) {

        int up = y == 0 ? height - 1 : y - 1;
        int down = y == height - 1 ? 0 : y + 1;
        int left = x == 0 ? width - 1 : x - 1;
        int right = x == width - 1 ? 0 : x + 1;

        double xDiffRed = (content.get(x, up).getRed() - content.get(x, down).getRed());
        double xDiffBlue = (content.get(x, up).getBlue() - content.get(x, down).getBlue());
        double xDiffGreen = (content.get(x, up).getGreen() - content.get(x, down).getGreen());
        double xGradientRed = xDiffRed * xDiffRed;
        double xGradientBlue = xDiffBlue * xDiffBlue;
        double xGradientGreen = xDiffGreen * xDiffGreen;
        double xGradient = xGradientRed + xGradientBlue + xGradientGreen;

        double yDiffRed = (content.get(left, y).getRed() - content.get(right, y).getRed());
        double yDiffBlue = (content.get(left, y).getBlue() - content.get(right, y).getBlue());
        double yDiffGreen = (content.get(left, y).getGreen() - content.get(right, y).getGreen());
        double yGradientRed = yDiffRed * yDiffRed;
        double yGradientBlue = yDiffBlue * yDiffBlue;
        double yGradientGreen = yDiffGreen * yDiffGreen;
        double yGradient = yGradientRed + yGradientBlue + yGradientGreen;

        return xGradient + yGradient;
    }

    public int[] findHorizontalSeam() {
        int[] res = new int[width];
        double[][] accmulateEnergyMatrix = new double[width][height];
        for (int row = 0; row < height; row ++) {
            accmulateEnergyMatrix[0][row] = energyMatrix[0][row];
        }
        for (int col = 1; col < width; col++) {
            for (int row = 0; row < height; row++) {
                if (row - 1 < 0) {
                    double preTwo = accmulateEnergyMatrix[col - 1][row];
                    double preThree = accmulateEnergyMatrix[col - 1][row + 1];
                    accmulateEnergyMatrix[col][row] = energyMatrix[col][row] + Math.min(preTwo,preThree);
                } else if (row + 1 > height - 1) {
                    double preOne = accmulateEnergyMatrix[col - 1][row - 1];
                    double preTwo = accmulateEnergyMatrix[col - 1][row];
                    accmulateEnergyMatrix[col][row] = energyMatrix[col][row] + Math.min(preOne,preTwo);
                } else {
                    double preOne = accmulateEnergyMatrix[col - 1][row - 1];
                    double preTwo = accmulateEnergyMatrix[col - 1][row];
                    double preThree = accmulateEnergyMatrix[col - 1][row + 1];
                    accmulateEnergyMatrix[col][row] = energyMatrix[col][row] + Math.min(Math.min(preOne,preTwo),preThree);
                }
            }
        }

        int minIndex = -1;
        double minValue = Double.MAX_VALUE;
        for (int row = 0; row < height; row++) {
            double value = accmulateEnergyMatrix[width - 1][row];
//            System.out.println(accmulateEnergyMatrix[width - 1][row]);
            if (value <= minValue) {
                minValue = value;
                minIndex = row;
//                System.out.println(minIndex);
            }
        }

        int pos = width - 1;
        res[pos] = minIndex;
        pos -= 1;
        double optionOne;
        double optionTwo;
        double optionThree;
        for (int col = width - 2; col >= 0; col--) {
//            System.out.println("a");
//            System.out.println(minIndex);
            if (minIndex - 1 < 0) {
                optionOne = Double.MAX_VALUE;
                optionTwo = accmulateEnergyMatrix[col][minIndex];
                optionThree = accmulateEnergyMatrix[col][minIndex + 1];
            } else if (minIndex + 1 > height - 1){
                optionOne = accmulateEnergyMatrix[col][minIndex - 1];
                optionTwo = accmulateEnergyMatrix[col][minIndex];
                optionThree = Double.MAX_VALUE;
            } else {
                optionOne = accmulateEnergyMatrix[col][minIndex - 1];
                optionTwo = accmulateEnergyMatrix[col][minIndex];
                optionThree = accmulateEnergyMatrix[col][minIndex + 1];
            }
            if (optionOne < optionTwo && optionOne < optionThree) {
                minIndex = minIndex - 1;
                res[pos] = minIndex;
            } else if (optionTwo <= optionOne && optionTwo <= optionThree) {
                res[pos] = minIndex;
            } else {
                minIndex = minIndex + 1;
                res[pos] = minIndex;
            }
            pos -= 1;
        }
        return res;
    }
    public int[] findVerticalSeam() {
        // transpose
        double[][] energyMatrixTrans = new double[height][width];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                energyMatrixTrans[row][col] = energyMatrix[col][row];
            }
        }
        energyMatrix = energyMatrixTrans;
        int tmp = width;
        width = height;
        height = tmp;
        int[] res = findHorizontalSeam();

        int tmp2 = width;
        width = height;
        height = tmp2;

        energyMatrix = new double[width][height];
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                energyMatrix[col][row] = energyMatrixTrans[row][col];
            }
        }
        return res;
    }
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(content,seam);
    }
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(content,seam);
    }
}