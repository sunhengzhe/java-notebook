# 动态规划（Dynamic Programming）

> Those who cannot remember the past are condemned to repeat it.

[How should I explain dynamic programming to a 4-year-old?](https://www.quora.com/How-should-I-explain-dynamic-programming-to-a-4-year-old/answer/Jonathan-Paulson)

Dynamic Programming is an algorithmic paradigm that solves a given complex problem by breaking it into subproblems and stores the results of subproblems to avoid computing the same results again.

- breaking it into subproblems
- stores the results of subproblems

解决具备两种属性的问题

1) Overlapping Subproblems
2) Optimal Substructure

两种方法

a) Memoization (Top Down)
b) Tabulation (Bottom Up)

## Dynamic Programming VS. Divide and Conquer

Similarities:

Both paradigms work by combining solutions to sub-problems.

Difference:

Dynamic programming is mainly used when the Overlapping Subproblems property satisfied.

## LeetCode 题

- [10. Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching/)
- [32. Longest Valid Parentheses](https://leetcode.com/problems/longest-valid-parentheses/)
- [115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/)

## 参考

- [LCS（Longest Common Subsequences） 问题](https://www.ics.uci.edu/~eppstein/161/960229.html)
- [0-1 Knapsack Problem | DP-10](https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/)
- [Top 50 Dynamic Programming Practice Problems](https://blog.usejournal.com/top-50-dynamic-programming-practice-problems-4208fed71aa3)
