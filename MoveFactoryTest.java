package edu.kit.informatik.matchthree.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.MoveFactoryImplementation;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.RandomStrategy;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Move;
import edu.kit.informatik.matchthree.framework.interfaces.MoveFactory;

public class MoveFactoryTest {

  private Board board;
  private MoveFactory mov;

  @Before
  public void init() {
    Set<Token> tokens = new HashSet<Token>();
    tokens
        .addAll(Arrays.asList(new Token[] { new Token("a"), new Token("b") }));
    board = new MatchThreeBoard(tokens, 4, 5);
    board.setFillingStrategy(new RandomStrategy());
    board.fillWithTokens();
    mov = new MoveFactoryImplementation();
  }

  @Test
  public void flipRight() {
    final Position pos0 = new Position(2, 4);
    final Token tok0 = board.getTokenAt(pos0);
    final Position pos1 = new Position(3, 4);
    final Token tok1 = board.getTokenAt(pos1);

    final Move moveTo = mov.flipRight(pos0);
    assertEquals(true, moveTo.canBeApplied(board));
    moveTo.apply(board);
    assertEquals(tok1, board.getTokenAt(pos0));
    assertEquals(tok0, board.getTokenAt(pos1));
    final Move moveReverse = moveTo.reverse();
    assertEquals(true, moveReverse.canBeApplied(board));
    moveReverse.apply(board);
    assertEquals(tok1, board.getTokenAt(pos1));
    assertEquals(tok0, board.getTokenAt(pos0));
  }

  @Test
  public void flipDown() {
    final Position pos0 = new Position(3, 2);
    final Token tok0 = board.getTokenAt(pos0);
    final Position pos1 = new Position(3, 3);
    final Token tok1 = board.getTokenAt(pos1);

    final Move moveTo = mov.flipDown(pos0);
    assertEquals(true, moveTo.canBeApplied(board));
    moveTo.apply(board);
    assertEquals(tok1, board.getTokenAt(pos0));
    assertEquals(tok0, board.getTokenAt(pos1));
    final Move moveReverse = moveTo.reverse();
    assertEquals(true, moveReverse.canBeApplied(board));
    moveReverse.apply(board);
    assertEquals(tok1, board.getTokenAt(pos1));
    assertEquals(tok0, board.getTokenAt(pos0));
  }

  @Test
  public void rotateSquareClockwise() {
    final Position pos0 = new Position(0, 0);
    final Token tok0 = board.getTokenAt(pos0);
    final Position pos1 = new Position(1, 0);
    final Token tok1 = board.getTokenAt(pos1);
    final Position pos2 = new Position(1, 1);
    final Token tok2 = board.getTokenAt(pos2);
    final Position pos3 = new Position(0, 1);
    final Token tok3 = board.getTokenAt(pos3);

    final Move moveTo = mov.rotateSquareClockwise(pos0);
    assertEquals(true, moveTo.canBeApplied(board));
    final String boardString = board.toTokenString();
    moveTo.apply(board);
    assertEquals(board.getTokenAt(pos0), tok3);
    assertEquals(board.getTokenAt(pos1), tok0);
    assertEquals(board.getTokenAt(pos2), tok1);
    assertEquals(board.getTokenAt(pos3), tok2);

    final Move moveReverse = moveTo.reverse();
    assertEquals(true, moveReverse.canBeApplied(board));
    moveReverse.apply(board);
    assertEquals(boardString, board.toTokenString());
  }

  @Test
  public void rotateColumnDown() {
    List<Position> columnPos = new ArrayList<Position>();
    List<Token> columnTok = new ArrayList<Token>();
    final int colNr = 1;
    for (int i = 0; i < board.getRowCount(); i++) {
      final Position pos = new Position(colNr, i);
      columnPos.add(pos);
      columnTok.add(board.getTokenAt(pos));
    }

    final String boardString = board.toTokenString();
    final Move moveTo = mov.rotateColumnDown(colNr);
    assertEquals(true, moveTo.canBeApplied(board));
    moveTo.apply(board);
    for (int i = 0; i < columnTok.size(); i++) {
      assertEquals(
          board.getTokenAt(new Position(colNr, (i + 1 + board.getRowCount())
              % board.getRowCount())), columnTok.get(i));
    }
    final Move moveReverse = moveTo.reverse();
    assertEquals(true, moveReverse.canBeApplied(board));
    moveReverse.apply(board);
    assertEquals(boardString, board.toTokenString());
  }
  
  @Test
  public void rotateRowRight() {
    List<Position> rowPos = new ArrayList<Position>();
    List<Token> rowTok = new ArrayList<Token>();
    final int rowNr = 1;
    for (int i = 0; i < board.getColumnCount(); i++) {
      final Position pos = new Position(i, rowNr);
      rowPos.add(pos);
      rowTok.add(board.getTokenAt(pos));
    }

    final String boardString = board.toTokenString();
    final Move moveTo = mov.rotateRowRight(rowNr);
    assertEquals(true, moveTo.canBeApplied(board));
    moveTo.apply(board);
    for (int i = 0; i < rowTok.size(); i++) {
      assertEquals(
          board.getTokenAt(new Position((i + 1 + board.getColumnCount())
              % board.getColumnCount(), rowNr)), rowTok.get(i));
    }
    final Move moveReverse = moveTo.reverse();
    assertEquals(true, moveReverse.canBeApplied(board));
    moveReverse.apply(board);
    assertEquals(boardString, board.toTokenString());
  }

  // TODO sehr viele tests, auch invalide sachen
}