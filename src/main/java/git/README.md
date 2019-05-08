# Git

## 远程分支

[Git Branching - Remote Branches](https://git-scm.com/book/id/v2/Git-Branching-Remote-Branches)

Remote branches are references (pointers) to the state of branches in your remote repositories.

### 重要概念：Tracking Branch（跟踪分支）或称为 Upstream branch（上游分支）

[Git: What is a tracking branch?](https://stackoverflow.com/questions/4693588/git-what-is-a-tracking-branch)

跟踪分支是与远程分支有直接关系的本地分支，使用 `git push` 或者 `git pull` 时，实际上会默认关联到对应的远程分支。远程分支命名形如 `<remote>/<branch>`，git clone 默认将 remote 命名为 `origin`

查看 tracking branches：`git branch -vv`

使用 `-u` 或者 `--set-upstream-to ` 修改正在跟踪的分支：`git branch -u <remote>/<branch>`.
 
使用 `git fetch` 拉取远程分支的更新。`git pull` 大多数情况的含义是 `git fetch` + `git merge`。
