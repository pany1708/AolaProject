UserExtension

	/**
	 * @in "word" - string : 要判断的语句
	 * @out "legal" - bool : true=合法，false=不合法
	 */
	@Command(value = "wordfilter", name = "判断语句是否合法")
	public void handleCmd(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		res.putBool("legal", !WordFilterUtil.isIllegalText(ao.getString("word")));
		sendResponse(res, u);
	}

	/**
	 * @in "word" - String :
	 * @out "word" - String :
	 */
	@Command(value = "applyfilter", name = "将脏话过滤")
