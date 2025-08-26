def solve():
    n, m = map(int, input().split())
    nums = list(set(map(int, input().split())))
    nums.sort()
    answer = []

    def dfs(cnt: int, result):
        if (cnt == m):
            answer.append(" ".join(list(map(str, result))))
            return

        for num in nums:
            result.append(num)
            dfs(cnt + 1, result)
            result.pop()

    dfs(0, list())
    for a in answer:
        print(a)

solve()