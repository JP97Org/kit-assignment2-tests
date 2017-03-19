package edu.kit.informatik.matchthree.tests;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

public class PrettyPrint {

  
  /**
   * Returns a pretty "unicode-image" of the given board using box-drawing characters,
   *   e.g. the board 'ABC;DEF;GHI' will print:
   *   
   *   ┌───┬───┬───┐
   *   │ A │ B │ C │
   *   ├───┼───┼───┤
   *   │ D │ E │ F │
   *   ├───┼───┼───┤
   *   │ G │ H │ I │
   *   └───┴───┴───┘
   * 
   * Do not upload this to the Praktomat! Don't judge me for code style, it's ugly I know.
   *   
   * @param board Well, the board to print obviously
   * @return A pretty board :)
   */
  static String prettyPrint(Board board) {
      final String TL   = "\u250c";             // top left corner
      final String TR   = "\u2510";             // top right corner
      final String BR   = "\u2518";             // bottom right corner
      final String BL   = "\u2514";             // bottom left corner
      final String T0   = "\u252c";             // "T" character
      final String T90  = "\u2524";             // 90° flipped "T"
      final String T180 = "\u2534";             // 180° flipped "T"
      final String T270 = "\u251c";             // 270° flipped "T"
      final String VERT = "\u2500\u2500\u2500"; // vertical bar
      final String HORI = "\u2502";             // horizontal bar
      final String CROS = "\u253c";             // cross

      int width  = board.getColumnCount();
      int height = board.getRowCount();
      StringBuilder b = new StringBuilder();

      b.append(TL + VERT); for (int i = 1; i < width; i++) b.append(T0 + VERT); b.append(TR + "\n");

      for (int y = 0; y < height; y++) {
          b.append(HORI);
          for (int x = 0; x < width; x++) {
              Token t = board.getTokenAt(Position.at(x, y));
              b.append(" " + (t != null ? t : " ") + " ");

              if (x < width - 1)
                  b.append(HORI);
          }
          b.append(HORI + "\n");

          if (y < height - 1) {
              b.append(T270 + VERT); for (int h = 1; h < width; h++) b.append(CROS + VERT); b.append(T90 + "\n");
          }
      }

      b.append(BL + VERT); for (int i = 1; i < width; i++) b.append(T180 + VERT); b.append(BR);
      return b.toString();
  }
}
