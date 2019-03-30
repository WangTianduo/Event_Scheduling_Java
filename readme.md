A repo for learning java web and developing scheduler algorithm use java

Add event function:
users (Prof, student) can submit a proposal and admin will determine whether grant
# 30/03/2019
- 需要 database 那里直接给sClass， 每个sClass对应一个 profArray & studentGroup array
- 修复了lecture上三遍的bug
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