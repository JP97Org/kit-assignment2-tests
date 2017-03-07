package edu.kit.informatik.matchthree.tests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.MaximumDeltaMatcher;
import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.RandomStrategy;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;

public class MatcherTest {

  private Board board;

  private Set<Delta> deltas;
  private Matcher mat;

  @Before
  public void init() {
    Set<Token> tokens = new HashSet<Token>();
    tokens
        .addAll(Arrays.asList(new Token[] { new Token("a"), new Token("b") }));
    board = new MatchThreeBoard(tokens, "ab;ab;aa");

    deltas = new HashSet<Delta>();
    deltas.add(new Delta(0, 1));
    mat = new MaximumDeltaMatcher(deltas);
  }

  @Test
  public void match() {
    final Position initial = new Position(0, 2);
    Set<Set<Position>> match = mat.match(board, initial);
    Set<Position> expectedPre = new HashSet<Position>();
    expectedPre.add(new Position(0, 0));
    expectedPre.add(new Position(0, 1));
    expectedPre.add(new Position(0, 2));
    Set<Set<Position>> expected = new HashSet<Set<Position>>();
    expected.add(expectedPre);

    assertEquals(expected, match);
  }

  @Test
  public void matchAll() {
    final Set<Position> initial = new HashSet<Position>();
    initial.add(new Position(0, 2));
    initial.add(new Position(1, 0));
    Set<Set<Position>> match = mat.matchAll(board, initial);
    Set<Position> expectedPre = new HashSet<Position>();
    expectedPre.add(new Position(0, 0));
    expectedPre.add(new Position(0, 1));
    expectedPre.add(new Position(0, 2));

    // TODO test so richtig fuer matchAll??
    // beschreibung seltsam ?!
    Set<Position> expectedPre2 = new HashSet<Position>();
    expectedPre2.add(new Position(1, 0));
    expectedPre2.add(new Position(1, 1));
    Set<Set<Position>> expected = new HashSet<Set<Position>>();

    expected.add(expectedPre);
    expected.add(expectedPre2);

    assertEquals(expected, match);
  }

  @Ignore("Runtime_Test")
  @Test
  public void matchAllMegaStresstest() {
    // tests only runtime, not result!
    for (int N = 10; N < 201; N += 10) {
      Set<Token> tokens = new HashSet<Token>();
      tokens.addAll(Arrays
          .asList(new Token[] { new Token("a"), new Token("b") }));
      board = new MatchThreeBoard(tokens, N, N);
      board.setFillingStrategy(new RandomStrategy());
      board.fillWithTokens();

      for (int i = -100; i < 100; i++) {
        if (i != 0) {
          deltas.add(new Delta(i, i));
        }
      }

      mat = new MaximumDeltaMatcher(deltas);
      final long before = System.currentTimeMillis();
      mat.matchAll(board,
          toSet(new Position(N / 2, N / 2), new Position(N / 4, N / 4)));
      System.out.println((System.currentTimeMillis() - before) + "ms for " + N
          + "x" + N);
    }
  }

  // TODO sehr viele tests, auch invalide sachen

  private static <T> Set<T> toSet(T... elems) {
    Set<T> ret = new HashSet<T>();
    ret.addAll(Arrays.asList(elems));
    return ret;
  }
}