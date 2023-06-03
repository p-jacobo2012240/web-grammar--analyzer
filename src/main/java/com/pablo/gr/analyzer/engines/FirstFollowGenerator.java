package com.pablo.gr.analyzer.engines;
import java.util.*;

/**
 * FirstFollowGenerator it's a factory for generate
 * first function(funcion primera)
 * follow function(funcion siguiente)
 *
 * */
public class FirstFollowGenerator {
    private Map<String, Set<String>> first;
    private Map<String, Set<String>> follow;
    private Map<String, List<List<String>>> productions;

    public FirstFollowGenerator(Map<String, List<List<String>>> productions) {
        this.productions = productions;
        first = new HashMap<>();
        follow = new HashMap<>();
    }

    public Map<String, Set<String>> generateFirst() {
        for (String nonTerminal : productions.keySet()) {
            calculateFirst(nonTerminal);
        }
        return first;
    }

    public Map<String, Set<String>> generateFollow(String startSymbol) {
        for (String nonTerminal : productions.keySet()) {
            follow.put(nonTerminal, new HashSet<>());
        }
        follow.get(startSymbol).add("$");

        for (String nonTerminal : productions.keySet()) {
            calculateFollow(nonTerminal);
        }
        return follow;
    }


    private void calculateFirst(String nonTerminal) {
        if (!first.containsKey(nonTerminal)) {
            first.put(nonTerminal, new HashSet<>());
        }

        List<List<String>> productionList = productions.get(nonTerminal);
        for (List<String> production : productionList) {
            for (String symbol : production) {
                if (!productions.containsKey(symbol) && !symbol.equals(nonTerminal)) {
                    first.get(nonTerminal).add(symbol);
                    break;
                } else if (productions.containsKey(symbol)) {
                    calculateFirst(symbol);
                    Set<String> firstOfSymbol = first.get(symbol);
                    first.get(nonTerminal).addAll(firstOfSymbol);

                    if (!firstOfSymbol.contains("e")) {
                        break;
                    }
                }
            }
        }
    }



    private void calculateFollow(String nonTerminal) {
        for (String key : productions.keySet()) {
            List<List<String>> productionList = productions.get(key);

            for (List<String> production : productionList) {
                int index = 0;
                while (index < production.size()) {
                    if (production.get(index).equals(nonTerminal)) {
                        if (index + 1 < production.size()) {
                            String nextSymbol = production.get(index + 1);

                            if (!productions.containsKey(nextSymbol)) {
                                follow.get(nonTerminal).add(nextSymbol);
                            } else {
                                Set<String> firstOfNext = first.get(nextSymbol);

                                if (firstOfNext.contains("e")) {
                                    follow.get(nonTerminal).addAll(firstOfNext);
                                    follow.get(nonTerminal).remove("e");

                                    if (index + 2 < production.size()) {
                                        String nextNextSymbol = production.get(index + 2);

                                        if (!productions.containsKey(nextNextSymbol)) {
                                            follow.get(nonTerminal).add(nextNextSymbol);
                                        } else {
                                            follow.get(nonTerminal).addAll(first.get(nextNextSymbol));
                                        }
                                    } else {
                                        if (!nonTerminal.equals(key)) {
                                            calculateFollow(key);
                                            follow.get(nonTerminal).addAll(follow.get(key));
                                        }
                                    }
                                } else {
                                    follow.get(nonTerminal).addAll(firstOfNext);
                                }
                            }
                        } else {
                            if (!nonTerminal.equals(key)) {
                                calculateFollow(key);
                                follow.get(nonTerminal).addAll(follow.get(key));
                            }
                        }
                    }
                    index++;
                }
            }
        }
    }
}
