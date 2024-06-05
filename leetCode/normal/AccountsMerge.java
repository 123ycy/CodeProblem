package normal;

import java.util.*;

/**
 * @ClassName: AccountsMerge
 * @Description: 721. 账户合并
 * @Author: zhaooo
 * @Date: 2022/7/7/14:57
 */
public class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, Integer> emailToIndex = new HashMap<>();
        Map<String, String> emailToName = new HashMap<>();
        int emailsCount = 0;
        // email映射
        for (List<String> account : accounts) {
            String name = account.get(0);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                if (!emailToIndex.containsKey(email)) {
                    emailToIndex.put(email, emailsCount++);
                    emailToName.put(email, name);
                }
            }
        }
        UnionFind uf = new UnionFind(emailsCount);
        for (List<String> account : accounts) {
            String firstEmail = account.get(1);
            int firstIndex = emailToIndex.get(firstEmail);
            int size = account.size();
            // 同一个account的所有Email合并，如果其他account有相同的email，则两个account会合并
            for (int i = 2; i < size; i++) {
                String nextEmail = account.get(i);
                int nextIndex = emailToIndex.get(nextEmail);
                uf.union(firstIndex, nextIndex);
            }
        }
        // 获取每个root的所有Email
        Map<Integer, List<String>> indexToEmails = new HashMap<>();
        for (String email : emailToIndex.keySet()) {
            int index = uf.find(emailToIndex.get(email));
            List<String> account = indexToEmails.getOrDefault(index, new ArrayList<>());
            account.add(email);
            indexToEmails.put(index, account);
        }

        // 排序并添加姓名
        List<List<String>> merged = new ArrayList<>();
        for (List<String> emails : indexToEmails.values()) {
            Collections.sort(emails);
            String name = emailToName.get(emails.get(0));
            List<String> account = new ArrayList<>();
            account.add(name);
            account.addAll(emails);
            merged.add(account);
        }
        return merged;
    }

    //并查集
    class UnionFind {
        int[] father;
        int[] rank;

        public UnionFind(int n) {
            father = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                father[i] = i;
            }
        }

        public int find(int x) {
            return x == father[x] ? x : (father[x] = find(father[x]));
        }

        public void union(int i, int j) {
            int x = find(i), y = find(j);
            if (rank[x] <= rank[y]) {
                father[x] = y;
            } else {
                father[y] = x;
            }
            if (rank[x] == rank[y] && x != y) {
                rank[y]++;
            }
        }
    }
}