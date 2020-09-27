/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Gman {

    public static String maxSubstring(String s) {
        // Write your code here
        int maxIndex = 0;
        String bestStings = "";
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (s.charAt(i) ==)
        }

    }

    public boolean isRobotBounded(String commands) {
        int[][] dir = new int[][] { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        int x = 0;
        int y = 0;

        int indexX = 0;

        for (char i : commands.toCharArray()) {
            if (i == 'L')
                indexX = (indexX + 3) % 4;
            else if (i == 'R')
                indexX = (indexX + 1) % 4;
            else {
                x += dir[indexX][0];
                y += dir[indexX][1];
            }
        }

        return (x == 0 && y == 0) || (indexX != 0);
    }

    public static void main(String[] args) {

    }
}
