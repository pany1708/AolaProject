    /**
	 *
	 * @param index
	 * @param func -1=没有奖励可领，0=现在有奖励可领，>0=在cd中
	 * @return
	 */
	public static MessageBoxInformer registerBonusInformer(int index, Function<User, Integer> func){
		MessageBoxInformer result = new MessageBoxInformer(index, func, cmd2);
		AbstractInformer pre = MAP_BONUS_INFORMER.put(index, result);
		if(pre != null){
			Logger.errorf("duplicate message informer found! index=%s,class=%s", index, pre.statusGetter.getClass());
			throw new RuntimeException("duplicate informer register to MessageBox with same index: " + index);
		}
		return result;
	}

	/**
	 *
	 * @param index
	 * @param func -1=活动不能再玩了，0=活动cd结束，可进入活动，>0=在cd中，cd过后才能进入活动
	 * @return
	 */
	public static MessageBoxInformer registerCDInformer(int index, Function<User, Integer> func){
		MessageBoxInformer result = new MessageBoxInformer(index, func, cmd3);
		AbstractInformer pre = MAP_CD_INFORMER.put(index, result);
		if(pre != null){
			Logger.errorf("duplicate message informer found! index=%s,class=%s", index, pre.statusGetter.getClass());
			throw new RuntimeException("duplicate informer register to MessageBox with same index: " + index);
		}
		return result;
	}

	private static abstract class AbstractInformer{
		protected final int id;
		protected final Function<User, Integer> statusGetter;

		public AbstractInformer(int id, Function<User, Integer> statusGetter) {
			super();
			this.id = id;
			this.statusGetter = statusGetter;
		}

		public abstract void onStatusChanged(User u);
	}

	public static class MessageBoxInformer extends AbstractInformer{

		private final String cmd;

		private MessageBoxInformer(int id, Function<User, Integer> statusGetter, String cmd) {
			super(id, statusGetter);
			this.cmd = cmd;
		}

		public final void onStatusChanged(User u){
			ActionscriptObject res = ServerHelper.createResponseObj(cmd);
			res.putInt("id", id);
			res.putInt("s", statusGetter.apply(u));
			ExtensionHelper.instance().sendXtResponse(res, u);
		}

		public final void onBonusOpen(User u){
			ActionscriptObject res = ServerHelper.createResponseObj(cmd);
			res.putInt("id", id);
			res.putInt("s", STATUS_BONUS_READY);
			ExtensionHelper.instance().sendXtResponse(res, u);
		}

		public final void onBonusClosed(User u){
			ActionscriptObject res = ServerHelper.createResponseObj(cmd);
			res.putInt("id", id);
			res.putInt("s", STATUS_BONUS_NONE);
			ExtensionHelper.instance().sendXtResponse(res, u);
		}

		public final void onCDChanged(User u, int newCDInSecs){
			ActionscriptObject res = ServerHelper.createResponseObj(cmd);
			res.putInt("id", id);
			res.putInt("s", newCDInSecs);
			ExtensionHelper.instance().sendXtResponse(res, u);
		}
	}
