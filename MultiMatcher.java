package edu.kit.informatik.matchthree.tests;

import java.util.HashSet;
import java.util.Set;

import edu.kit.informatik.matchthree.framework.Position;
import edu.kit.informatik.matchthree.framework.interfaces.Board;
import edu.kit.informatik.matchthree.framework.interfaces.Matcher;

public class MultiMatcher implements Matcher {

  private Matcher[] matchers;

  public MultiMatcher(Matcher... matchers) {
    this.matchers = matchers;
  }

  @Override
  public Set<Set<Position>> match(Board board, Position initial) {
    Set<Set<Position>> ret = new HashSet<Set<Position>>();

    for (Matcher matcher : matchers) {
      ret.addAll(matcher.match(board, initial));
    }

    return ret;
  }

  @Override
  public Set<Set<Position>> matchAll(Board board, Set<Position> initial) {
    Set<Set<Position>> ret = new HashSet<Set<Position>>();

    for (Matcher matcher : matchers) {
      ret.addAll(matcher.matchAll(board, initial));
    }

    return ret;
  }

}