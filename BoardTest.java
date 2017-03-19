package edu.kit.informatik.matchthree.tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.RandomStrategy;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;

/**
 * An exemplary test for {@link Board}.
 * <p>
 * Tests whether the constructor of {@link MatchThreeBoard} creates an instance
 * that returns correct values for:
 * <ul>
 * <li>{@link Board#getColumnCount()},</li>
 * <li>{@link Board#getRowCount()},</li>
 * <li>{@link Board#getAllValidTokens()}.</li>
 * </ul>
 * 
 * @author IPD Koziolek
 */
public class BoardTest {
  /**
   * Tests {@link Board} on the instance created by
   * {@code new MatchThreeBoard(Token.set("AB"), 2, 3);}.
   */
  @Test
  public void testAB23() {
    Board board = new MatchThreeBoard(Token.set("AB"), 2, 3);

    assertEquals(2, board.getColumnCount());
    assertEquals(3, board.getRowCount());
    assertEquals(Token.set("AB"), board.getAllValidTokens());

    final Token token = new Token("A");
    final Position position = new Position(1, 1);
    board.setTokenAt(position, token);
    assertEquals(token, board.getTokenAt(position));

    assertEquals(true, board.containsPosition(position));
    assertEquals(false, board.containsPosition(new Position(2, 3)));

    final Position pos2 = new Position(0, 1);
    board.setTokenAt(pos2, token);
    Set<Position> removeSet = new HashSet<>();
    Position[] posArr = new Position[] { position, pos2 };
    for (Position posInArr : posArr)
      removeSet.add(posInArr);

    board.removeTokensAt(removeSet);
    assertEquals(null, board.getTokenAt(position));
    assertEquals(null, board.getTokenAt(pos2));
  }

  @Test
  public void moveTokensToBottomAndFill() {
    Set<Token> tokens = new HashSet<Token>();
    tokens.addAll(Arrays.asList(new Token[] { new Token("A"), new Token("+"),
        new Token("*"), new Token("Y") }));
    String tokenStr = "A AA;++  ; *A*;Y  Y";
    Board board = new MatchThreeBoard(tokens, tokenStr);
    final String before = board.toTokenString();
    assertEquals(tokenStr, before);
    board.moveTokensToBottom();
    final String after = board.toTokenString();
    tokenStr = "    ;A  A;++A*;Y*AY";
    assertEquals(tokenStr, after);

    board.setFillingStrategy(new RandomStrategy());
    board.fillWithTokens();
    boolean correct = true;
    for (int o = 0; o < 2; o++) {
      for (int i = 0; i < board.getColumnCount(); i++) {
        final Position pos = new Position(o, i);
        correct &= board.getTokenAt(pos) != null;
        if (!correct) {
          break;
        }
      }
    }
    assertEquals(true, correct);
    assertEquals(new Token("A"), board.getTokenAt(new Position(0,1)));
    assertEquals(new Token("A"), board.getTokenAt(new Position(3,1)));
    assertEquals(tokenStr.substring(10), board.toTokenString().substring(10));
  }
  
  // TODO sehr viele tests, auch invalide sachen
}
