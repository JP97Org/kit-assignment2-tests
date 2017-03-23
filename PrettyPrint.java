package edu.kit.informatik.matchthree.tests;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import java.util.HashMap;
import java.util.Map;

public final class PrettyPrint {

    /**
     * Returns a pretty "unicode-image" of the given board using box-drawing characters, e.g.&nbsp; the board 'ABC;DEF;GHI'
     * will print following.
     *
     * ┌───┬───┬───┐
     * │ A │ B │ C │
     * ├───┼───┼───┤
     * │ D │ E │ F │
     * ├───┼───┼───┤
     * │ G │ H │ I │
     * └───┴───┴───┘
     *
     * Do not upload this to the Praktomat! Don't judge me for code style, it's ugly I know.
     *
     * @param board Well, the board to print obviously
     * @return A pretty board :)
     */
    public static String prettyPrint(Board board) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_WHITE = "\u001B[37m";
        
        final String[] COLORS = new String[] {ANSI_BLUE, ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_PURPLE, ANSI_CYAN, ANSI_WHITE};
        
        final String DOT = "\u25cf";

        Map<Token, String> representations = new HashMap<>();
        
        int index = 0;
        for (Token token : board.getAllValidTokens()) {
            if (index < COLORS.length) {
                representations.put(token, COLORS[index] + DOT + ANSI_RESET);
            } else {
                representations.put(token, token.toString());
            }
            index++;
        }
        
        final String TL = "\u250c";               // top left corner
        final String TR = "\u2510";               // top right corner
        final String BR = "\u2518";               // bottom right corner
        final String BL = "\u2514";               // bottom left corner
        final String T0 = "\u252c";               // "T" character
        final String T90 = "\u2524";              // 90° flipped "T"
        final String T180 = "\u2534";             // 180° flipped "T"
        final String T270 = "\u251c";             // 270° flipped "T"
        final String VERT = "\u2500\u2500\u2500"; // vertical bar
        final String HORI = "\u2502";             // horizontal bar
        final String CROS = "\u253c";             // cross

        int width = board.getColumnCount();
        int height = board.getRowCount();
        StringBuilder b = new StringBuilder();

        b.append(TL + VERT);
        for (int i = 1; i < width; i++) {
            b.append(T0 + VERT);
        }
        b.append(TR + "\n");

        for (int y = 0; y < height; y++) {
            b.append(HORI);
            for (int x = 0; x < width; x++) {
                Token t = board.getTokenAt(Position.at(x, y));
                b.append(" ").append(t != null ? representations.get(t) : " ").append(" ");

                if (x < width - 1) {
                    b.append(HORI);
                }
            }
            b.append(HORI + "\n");

            if (y < height - 1) {
                b.append(T270 + VERT);
                for (int h = 1; h < width; h++) {
                    b.append(CROS + VERT);
                }
                b.append(T90 + "\n");
            }
        }

        b.append(BL + VERT);
        for (int i = 1; i < width; i++) {
            b.append(T180 + VERT);
        }
        b.append(BR);
        return b.toString();
    }

    private PrettyPrint() {
    }
}
