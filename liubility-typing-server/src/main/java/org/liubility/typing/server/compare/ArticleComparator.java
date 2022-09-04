package org.liubility.typing.server.compare;

import org.liubility.typing.server.code.libs.WordLib;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: JDragon
 * @Data:2022/9/3 15:03
 * @Description:
 */
public class ArticleComparator {

    static class DataType {
        int distance;
        int prevX;
        int prevY;

        public DataType(int distance, int prevX, int prevY) {
            this.distance = distance;
            this.prevX = prevX;
            this.prevY = prevY;
        }

        public int getDistance() {
            return distance;
        }

        public int getPrevX() {
            return prevX;
        }

        public int getPrevY() {
            return prevY;
        }
    }

    private ArrayList<int[]> getMatch(String origin, String typed) {
        int lenOrigin = origin.length();
        int lenTyped = typed.length();
        DataType[][] data = new DataType[lenOrigin + 1][lenTyped + 1];
        data[0][0] = new DataType(0, -1, -1); // previous (-1, -1) means start point
        for (int j = 1; j < lenTyped + 1; j++) {
            data[0][j] = new DataType(j, 0, j - 1); // origin empty, type is number of differences
        }
        for (int i = 1; i < lenOrigin + 1; i++) {
            data[i][0] = new DataType(i, i - 1, 0); // type is empty, origin is number of differences
            for (int j = 1; j < lenTyped + 1; j++) {
                int m = i - 1;
                int n = j - 1;
                if (origin.charAt(m) == typed.charAt(n)) {
                    data[i][j] = new DataType(data[i - 1][j - 1].getDistance(), i - 1, j - 1);
                } else {
                    int distanceLess = data[i - 1][j].getDistance(); // if typed less
                    int distanceWrong = data[i - 1][j - 1].getDistance(); // if typed wrong
                    int distanceMore = data[i][j - 1].getDistance(); // if typed more
                    if (distanceWrong <= distanceLess && distanceWrong <= distanceMore) {
                        data[i][j] = new DataType(distanceWrong + 1, i - 1, j - 1);
                    } else if (distanceLess <= distanceWrong && distanceLess <= distanceMore) {
                        data[i][j] = new DataType(distanceLess + 1, i - 1, j);
                    } else {
                        data[i][j] = new DataType(distanceMore + 1, i, j - 1);
                    }
                }
            }
        }
        int curX = lenOrigin;
        int curY = lenTyped;
        int[] resOrigin = new int[lenOrigin];
        int[] resTyped = new int[lenTyped];
        ArrayList<int[]> a = new ArrayList<>();
        while (curX != 0 || curY != 0) {
            int prevX = data[curX][curY].getPrevX();
            int prevY = data[curX][curY].getPrevY();
            if (data[prevX][prevY].getDistance() == data[curX][curY].getDistance() - 1) {
                if (prevX < curX && prevY < curY) {  //Wrong word
                    resOrigin[curX - 1] = 3;
                    resTyped[curY - 1] = 3;
                } else if (prevX < curX) {    //more word
                    resOrigin[curX - 1] = 1;
                } else {                    //less word
                    resTyped[curY - 1] = 1;

                }
            }
            curX = prevX;
            curY = prevY;
        }
        a.add(resOrigin);
        a.add(resTyped);
        return a;
    }

    /**
     * @param origin        原文
     * @param typed         对比文章
     * @param ignoreSymbols 是否忽略符号
     * @param symbolWordLib 符号库
     */
    public List<ComparisonItem> comparison(String origin, String typed, boolean ignoreSymbols, WordLib symbolWordLib) {
        ArticleComparator am = new ArticleComparator();
        List<Integer> originSymbolSign = new ArrayList<>();
        ArrayList<int[]> strMatch;
        char[] originChars;
        char[] typedChars;
        if (ignoreSymbols) {
            StringBuilder newOrigin = new StringBuilder();
            StringBuilder newTyped = new StringBuilder();


            for (int i = 0; i < origin.length(); i++) {
                String originChar = String.valueOf(origin.charAt(i));
                if (symbolWordLib.getCode(originChar) == null) {
                    newOrigin.append(originChar);
                } else originSymbolSign.add(i);
            }
            for (int i = 0; i < typed.length(); i++) {
                String typedChar = String.valueOf(typed.charAt(i));
                if (symbolWordLib.getCode(typedChar) == null) {
                    newTyped.append(typedChar);
                }
            }
            strMatch = am.getMatch(newOrigin.toString(), newTyped.toString());
            originChars = newOrigin.toString().toCharArray();
            typedChars = newTyped.toString().toCharArray();
        } else {
            strMatch = am.getMatch(origin, typed);
            originChars = origin.toCharArray();
            typedChars = typed.toCharArray();
        }
        int[] orgins = strMatch.get(0);
        int[] typeds = strMatch.get(1);

        List<ComparisonItem> comparisonItemList = new ArrayList<>();
        int x = 0;
        int y = 0;
        int symbolNum = 0;
        while (x != originChars.length || y != typedChars.length) {
            if (ignoreSymbols && originSymbolSign.contains(x + symbolNum)) {
                comparisonItemList.add(new ComparisonItem(String.valueOf(origin.charAt(x + symbolNum)), 5));//忽略的符号
                symbolNum++;
                continue;
            }
            if (x != originChars.length && orgins[x] == 1) {
                comparisonItemList.add(new ComparisonItem(String.valueOf(originChars[x]), 1));//少字
                x++;
            } else if (y != typedChars.length && typeds[y] == 1) {
                comparisonItemList.add(new ComparisonItem(String.valueOf(typedChars[y]), 2));//多字
                y++;
            } else if (x != originChars.length && orgins[x] == 3) {
                comparisonItemList.add(new ComparisonItem(String.valueOf(originChars[x]), 4));//错原字
                comparisonItemList.add(new ComparisonItem(String.valueOf(typedChars[y]), 3));//错字
                x++;
                y++;
            } else {
                comparisonItemList.add(new ComparisonItem(String.valueOf(originChars[x]), 0));//正确字
                x++;
                y++;
            }
        }
        return comparisonItemList;
    }

}
