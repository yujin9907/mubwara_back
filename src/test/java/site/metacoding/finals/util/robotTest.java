package site.metacoding.finals.util;

class Solution {
    int lookFor = 1; // 1북 2동 3남 4서

    public int solution(int[][] office, int r, int c, String[] move) {
        int answer = 0;
        int locationValue = office[r][c];
        int officeSize = office.length;

        if (locationValue != -1) {
            answer += locationValue;
        }

        for (int i = 0; i < move.length; i++) {

            for (int j = 0; j < officeSize; j++) {
                for (int q = 0; q < officeSize; q++) {
                    System.out.println(office[j][r]);
                }
            }

            if (move[i].equals("go")) {
                if (lookFor == 1) {
                    if (office[r - 1][c] == -1 || r == 0) {
                        continue;
                    } else {
                        r--;
                        answer += locationValue;
                        office[r][c] = 0;
                    }
                }
                if (lookFor == 2) {
                    if (office[r][c + 1] == -1 || c == officeSize) {
                        continue;
                    } else {
                        c++;
                        answer += locationValue;
                    }
                }
                if (lookFor == 3) {
                    if (office[r + 1][c] == -1 || r == officeSize) {
                        continue;
                    } else {
                        r++;
                        answer += locationValue;
                    }
                }
                if (lookFor == 4) {
                    if (office[r][c - 1] == -1 || c == 0) {
                        continue;
                    } else {
                        c--;
                        answer += locationValue;
                    }
                }
            } else {
                turn(move[i]);
            }
        }

        return answer;
    }

    private void turn(String look) {
        if (look.equals("right")) {
            lookFor++;
            if (lookFor > 4) {
                lookFor = 1;
            }
        }
        if (look.equals("left")) {
            lookFor--;
            if (lookFor < 0) {
                lookFor = 4;
            }
        }
    }
}