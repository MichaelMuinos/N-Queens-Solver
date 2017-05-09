package model;

import pojo.BoardState;
import pojo.Stat;

public interface LocalSearchAlgorithm {
    Stat performSearch(BoardState initialState);
}
