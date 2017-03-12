package edu.kit.informatik.matchthree.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.kit.informatik.matchthree.MatchThreeBoard;
import edu.kit.informatik.matchthree.MatchThreeGame;
import edu.kit.informatik.matchthree.MaximumDeltaMatcher;
import edu.kit.informatik.matchthree.MoveFactoryImplementation;
import edu.kit.informatik.matchthree.framework.Delta;
import edu.kit.informatik.matchthree.framework.DeterministicStrategy;
import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.RandomStrategy;
import edu.kit.informatik.matchthree.framework.Token;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Game;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;
import edu.kit.informatik.matchthree.framework.interfaces.Move;

public class GameTest {
  private Board board;

  private Set<Delta> deltas;
  private Matcher mat;
  
  private Game game;
  
  @Before
  public void init() {
    Set<Token> tokens = new HashSet<Token>();
    tokens
        .addAll(Arrays.asList(new Token[] { new Token("a"), new Token("b") }));
    board = new MatchThreeBoard(tokens, 5,7);
    board.setFillingStrategy(new RandomStrategy());
    board.setTokenAt(new Position(0,0), new Token("a"));
    board.setTokenAt(new Position(0,1), new Token("a"));
    board.setTokenAt(new Position(0,2), new Token("a"));
    
    deltas = new HashSet<Delta>();
    deltas.add(new Delta(0, 1));
    mat = new MaximumDeltaMatcher(deltas);
    
    game = new MatchThreeGame(board, mat);
  }
  
  @Test
  public void initializeBoardAndStart() {
    //only run test, no asserts!
    System.out.println(game.getScore());
    System.out.println(board.toTokenString());
    game.initializeBoardAndStart();
    System.out.println(board.toTokenString());
    System.out.println(game.getScore());
  }
  
  @Test
  public void acceptMove() {
    //only run test, no asserts!
    Move moveTest = new MoveFactoryImplementation().flipDown(new Position(0,0));
    System.out.println(game.getScore());
    System.out.println(board.toTokenString());
    game.initializeBoardAndStart();
    final int scoreBefore = game.getScore(); 
    System.out.println(board.toTokenString());
    game.acceptMove(moveTest);
    System.out.println(board.toTokenString());
    System.out.println(game.getScore() - scoreBefore);
  }
  
  @Test
  public void baseTest22() {
    //Calculate score (example 22)
    
    Board b = new MatchThreeBoard(Token.set("AXO*"), "O*O;***;O*O;O*O");
    b.setFillingStrategy(new DeterministicStrategy(itr("AOA**"), itr("AXAXA"), itr("A**A*")));
    Matcher m = new MultiMatcher(new MaximumDeltaMatcher(toSet(Delta.dxy(1, 0))), new MaximumDeltaMatcher(toSet(Delta.dxy(0, 1))));
    Game g = new MatchThreeGame(b, m);
    g.initializeBoardAndStart();
    assertEquals(b.toTokenString(), "*A*;*XA;AA*;OX*");
    assertEquals(g.getScore(), 49);
  }

  private static Iterator<Token> itr(String tokenString) {
    return Token.iterator(tokenString);
  }

  private static Set<Delta> toSet(Delta dxy) {
    Set<Delta> ret = new HashSet<Delta>();
    ret.add(dxy);
    return ret;
  }
}