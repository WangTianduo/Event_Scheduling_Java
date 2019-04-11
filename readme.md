A repo for learning java web and developing scheduler algorithm use java

Add event function:
users (Prof, student) can submit a proposal and admin will determine whether grant
# 11/04/2019
- 完成教室范围限定
- 可查询每个 sclass 的 prof and studentG
- 明天计划：写自动生成 ics 的 python 脚本
# 01/04/2019
- 实现自动化评分
- 添加评分标准：different session， different day
- 实现 prof conflict check （只检查了上部分cohort的情况， 没有检查上部分时间的情况）
- 接下来尝试进化
# 31/03/2019
- 检查student group conflicts：return int (num of conflict course)
- 添加 common slot 和 周五周三下午没课
- 尝试解决无法全部放置sClass的问题 （可以改变weekday了）
- 完成chromosome 部分搭建，算法试运行成功
- 评分标准是 conflict number， 期望结果为 0 （目前只考虑 studentG 的 conflict）
- 新想到的idea：学生日历中添加 考试（包括quiz）提醒
# 30/03/2019
- 需要 database 那里直接给sClass， 每个sClass对应一个 profArray & studentGroup array
- 修复了lecture上三遍的bug
- studentGroup 有自己的 classList
- 每个 sClass 在 scheduling 完事后都知道自己的 时间和教室
- 明天任务：如何检查存在conflict
# 29/03/2019
- 重构了input2D，使之变成input3D，session 与 cohort 分立
- 添加随机教室功能，也支持指定教室功能
- 添加 RoomList Class，在保留list的基础上，添加 lecstart, labstart，清晰划分教室类型
- 明后两天的任务集中于分析 prof & student group 会产生的 conflict
# 27/03/2019
- 写完了 calendar 里面 randomInit 方法， 可以成功跑，但不确定逻辑是否正确
- 这是一个最最基础的版本
- 每个sClass设置了初始教室，一班在一号教室上课，以此类推
- 还需要做的是：根据gClass 种类的不同，挑选合适的教室， 如lecture
- 在生成weekday的时候使用随机算法，
- 但生成timeslot是用遍历以及贪心算法