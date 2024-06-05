package normal;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GenerateParentheses
 * @Description: 22. 括号生成
 * @Author: zhaooo
 * @Date: 2024/02/25 19:24
 */
public class GenerateParentheses {
    List<String> ans = new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        dfs(new StringBuilder("("), n - 1, n);
        return ans;
    }

    // l表示剩于左括号 r表示剩余右括号
    void dfs(StringBuilder sb, int l, int r) {
        // r >= l 保证l先到0或者l,r同时为0 故只需要判断 l < 0
        if (r < l || l < 0) {
            return;
        }
        // 可以优化为 if (r == 0) {
        if (l == 0 && r == 0) {
            ans.add(sb.toString());
            return;
        }
        dfs(new StringBuilder(sb).append("("), l - 1, r);
        dfs(new StringBuilder(sb).append(")"), l, r - 1);
    }
}
