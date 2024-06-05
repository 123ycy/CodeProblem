package normal;

import java.util.List;

/**
 * @ClassName: KeysAndRooms
 * @Description: 841. 钥匙和房间
 * @Author: zhaooo
 * @Date: 2023/04/13 19:04
 */
public class KeysAndRooms {
    int count = 0;

    /**
     * dfs
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int len = rooms.size();
        boolean[] visited = new boolean[len];
        dfs(rooms, visited, 0);
        return count == len;
    }

    void dfs(List<List<Integer>> rooms, boolean[] visited, int i) {
        List<Integer> list = rooms.get(i);
        visited[i] = true;
        count++;
        for (Integer k : list) {
            if (visited[k]) {
                continue;
            }
            dfs(rooms, visited, k);
        }
    }
}
