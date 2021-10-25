import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.function.Consumer;

public class Solution {
    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0, 1, 1, 0}, {0, 0, 0, 1}, {1, 1, 0, 0}, {1, 1, 1, 0}}));
    }

    public static int solution(int[][] map) {
        ArrayList<Integer> begin = new ArrayList<>(Arrays.asList(0, 0));
        ArrayList<Integer> end = new ArrayList<>(Arrays.asList(map.length - 1, map[0].length- 1));
        HashMap<ArrayList<Integer>, Integer> forwardMap = new HashMap<>();
        int forwardResult = explore(map, begin, end, forwardMap);
        System.out.println("forward map: ");
        forwardMap.forEach((key, value) -> System.out.println(key + " : " + value));
        HashMap<ArrayList<Integer>, Integer> backMap = new HashMap<>();
        explore(map, end, begin, backMap);
        System.out.println("back map: ");
        backMap.forEach((key, value) -> System.out.println(key + " : " + value));
        int deleteResult = forwardMap.entrySet().stream()
                .filter(e -> backMap.containsKey(e.getKey()))
                .mapToInt(e -> e.getValue() + backMap.get(e.getKey()) - 1)
                .min()
                .orElse(Integer.MAX_VALUE);
        return Math.min(forwardResult, deleteResult);
    }

    //BFS exploration of the map from map[x[0]][x[1]] to map[y[0]][y[1]].
    //Return value is the shortest path from x to y, or Integer.MAX_VALUE if not possible.
    //Additional effect: adds eligible wall elements mapped to the
    //distance from the origin point.
    //Eligible wall elements are those which border at least two non-wall elements.
    private static int explore(int[][] map,
                               ArrayList<Integer> x,
                               ArrayList<Integer> y,
                               HashMap<ArrayList<Integer>, Integer> wallMap) {
        final boolean[][] visitedMap = new boolean[map.length][];
        for (int i = 0; i < map.length; ++i) {
            visitedMap[i] = new boolean[map[i].length];
            Arrays.fill(visitedMap[i], false);
        }
        Consumer<ArrayList<Integer>> markVisited = list -> visitedMap[list.get(0)][list.get(1)] = true;
        boolean shortestFound = false;
        int distance = Integer.MAX_VALUE;
        final ArrayDeque<Pair<ArrayList<Integer>, Integer>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(x, 1));
        markVisited.accept(x);
        while (!queue.isEmpty()) {
            final Pair<ArrayList<Integer>, Integer> top = queue.remove();

            //Handle solution distance if not yet found
            if (!shortestFound) {
                if (top.getFirst().get(0).intValue() == y.get(0)
                        && top.getFirst().get(1).intValue() == y.get(1)) {
                    shortestFound = true;
                    distance = top.getSecond();
                }
            }

            //Add unvisited empty spaces and handle eligible walls that neighbor top
            Consumer<ArrayList<Integer>> processCoord = coord -> {
                if (!visitedMap[coord.get(0)][coord.get(1)]) {
                    markVisited.accept(coord);
                    //If coord is path
                    if (map[coord.get(0)][coord.get(1)] == 0) {
                        queue.add(new Pair<>(coord, top.getSecond() + 1));
                    } else { //If coord is a wall
                        //Make sure it is eligible for deletion and not already present in map
                        if (isEligible(coord.get(0), coord.get(1), map) && !wallMap.containsKey(coord)) {
                            wallMap.put(coord, top.getSecond() + 1);
                        }
                    }
                }
            };
            if (top.getFirst().get(0) != 0) {
                processCoord.accept(new ArrayList<>(Arrays.asList(top.getFirst().get(0) - 1, top.getFirst().get(1))));
            }
            if (top.getFirst().get(0) != map.length - 1) {
                processCoord.accept(new ArrayList<>(Arrays.asList(top.getFirst().get(0) + 1, top.getFirst().get(1))));
            }
            if (top.getFirst().get(1) != 0) {
                processCoord.accept(new ArrayList<>(Arrays.asList(top.getFirst().get(0), top.getFirst().get(1) - 1)));
            }
            if (top.getFirst().get(1) != map[0].length - 1) {
                processCoord.accept(new ArrayList<>(Arrays.asList(top.getFirst().get(0), top.getFirst().get(1) + 1)));
            }
        }
        return distance;
    }

    private static boolean isEligible(int x, int y, int[][] map) {
        int numPath = 0;
        if (x != 0) {
            numPath += map[x - 1][y] == 0 ? 1 : 0;
        }
        if (x != map.length - 1) {
            numPath += map[x + 1][y] == 0 ? 1 : 0;
        }
        if (y != 0) {
            numPath += map[x][y - 1] == 0 ? 1 : 0;
        }
        if (y != map[0].length - 1) {
            numPath += map[x][y + 1] == 0 ? 1 : 0;
        }
        return numPath >= 2;
    }

    private static class Pair<F, S> {
        private final F first;
        private final S second;

        private Pair(F a, S b) {
            first = a;
            second = b;
        }

        public F getFirst() {
            return first;
        }

        public S getSecond() {
            return second;
        }
    }
}