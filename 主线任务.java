1. 判断是否完成主线任务
UserTasks userTasks = UserTaskCache.instance.getUserTasks(u.getUserId());
if(userTasks == null) {
	return -2;
}
UserTask userTask = userTasks.getUserTask(TASK_ID);
if(userTask == null) {
	return -3;
}
if(!userTask.isFinished()) {
	return -4;
}

2. 对应的Task_Id: 定义在rpg.Task*表中: TaskDefine, TaskStarter.
