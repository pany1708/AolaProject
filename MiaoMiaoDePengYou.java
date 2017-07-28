@BeginDate("2017-04-21")
@Name("喵喵的伙伴之约")
public class MiaoMiaoDePengYou extends HolidayResponder {
  //全局的变量
  private static String CMDHEAD = "MMDPY170421";
  private static String ACTIVITY_NAME = "喵喵的伙伴之约";

  //定义规定格式的变量
  private final static Data PROG = DbData.newDataInstance(ACTIVITY_NAME,"进度");  //数据库的Model
  private final static Data HP = DbData.newDataInstance(ACTIVITY_NAME,"血量");
  private final static Data ENTER_TIMES_LOG = DbData.newDataInstance(ACTIVITY_NAME,"统计进入交互次数");

  /**
   *获取相应的服务
   *服务号会在相应的数据库Service表的ID中.
   */
  private final static Service HP_SERVICE = Service.getDefine(7918); //购买血量
  private final static Service PASS_CUR_LEVEL_SERVICE = Service.getDefine(7917);//一键通关
  private final static Service PASS_SERVICE = Service.getDefine(7915); //一键获得
	private static final Service SLOW_DOWN_SERVICE = Service.getDefine(7916); //破坏机关


  //亚比的配置
  //
  private final static int INIT_HP = 99;  //初始血量
	private final static int INCR_HP_PER_HP_SERVICE = 10; //每次血量购买服务增加血量
	private final static Prop<String> PET_BONUS = Props.newProp(ACTIVITY_NAME, "亚比奖励", "102:3255:1");//配置奖励?
	private final static int PROG_GAIN_PET = 15; //关卡数目



/**
 *@comand是什么意思，这类注解是为了暴露接口,还是其他的?
 *
 */
/**
 * @out "prog" - int : 进度，0~14：15关交互进度，15：领取亚比，16-已领取亚比
 * @out "hp" - int : 血量
 */
@Command(value = CMDHEAD + "_panel", name = "取面板")
public void panel(String cmd, ActionscriptObject ao, User u, int fromRoom) {
  ActionscriptObject res = ServerHelper.createResponseObj(cmd); //一个向前端返回数据的对象[xml]
  res.putInt("prog", PROG.get(u.getUserId()));  //向对象中写入"进度"
  Integer hp = HP.getRaw(u.getUserId()); //获取数据库的血量
  if(hp == null) {   //血量判断
    hp = INIT_HP;
    HP.set(u.getUserId(), hp);  //初始时写入
  }
  res.putInt("hp", hp);
  sendResponse(res, u); //向前端返回数据，将res对象返回给指定的用户u
  //示例的数据格式:
  /*
    <dataObj>
     <var n='prog' t='n'>3</var>
     <var n='_cmd' t='s'>MMDPY170421_panel</var>
     <var n='hp' t='n'>62</var>
   </dataObj>
  */


  /**
	 *
	 */
	@Command(value = CMDHEAD + "_enter", name = "进入交互(统计用)")
	public void enter(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		ENTER_TIMES_LOG.incr(u.getUserId(), 1);//向数据库登录记录写入数据,+1操作
		sendResponse(res, u);  //有无必要向前端返回数据.?
	}


  /**
	 * @out "r" - int : 结果
	 */
	@Command(value = CMDHEAD + "_buyHp", name = "购买血量")
	public void buyHp(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		res.putInt("r", handleBuyHp(u));
		sendResponse(res, u);
	}
  //分离逻辑
	private int handleBuyHp(User u) {
		int r = BuyService.instance.buy(u, HP_SERVICE, 1).getResult();
		if(r != MaterialResult.SUCCESS) {
			return r;
		}
		HP.incr(u.getUserId(), INCR_HP_PER_HP_SERVICE);
		return MaterialResult.SUCCESS;
	}


  /**
	 * @in "hp" - int : 本关此时剩余的血量
	 * @out "r" - int : 结果
	 */

   //ActionscriptObject的含义是什么?
   //一键通关
	@Command(value = CMDHEAD + "_passLevel", name = "购买通过本关(不需要再发结算命令)")
	public void passLevel(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		res.putInt("r", handlePassLevel(u, ao.getInt("hp"), res));
		sendResponse(res, u);
	}

	private int handlePassLevel(User u, int hp, ActionscriptObject res) {
		if(hp < 0 || hp > HP.get(u.getUserId())) {  //?
			return -1;
		}
		int r = BuyService.instance.buy(u, PASS_CUR_LEVEL_SERVICE, 1).getResult();
		if(r != MaterialResult.SUCCESS) {
			return r;
		}
		if(PROG.get(u.getUserId()) < PROG_GAIN_PET) {
			PROG.incr(u.getUserId(), 1);
		}
		HP.set(u.getUserId(), hp);
		return MaterialResult.SUCCESS;
	}



  /**
	 * @out "r" - int : 结果
	 * @out "bn" - array :
	 */
	@Command(value = CMDHEAD + "_pass", name = "一键获得")
	public void pass(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		res.putInt("r", handlePass(u, res));
		sendResponse(res, u);
	}

	private int handlePass(User u, ActionscriptObject res) {
		int prog = PROG.get(u.getUserId());
		if(prog >= PROG_GAIN_PET) {
			return -1;
		}
		int r = BuyService.instance.buy(u, PASS_SERVICE, 1).getResult();
		if(r == MaterialResult.SUCCESS) {
			PROG.set(u.getUserId(), PROG_GAIN_PET + 1); //?
			res.putASObjectList("bn", BonusManager.instance.gainBonusWithAoResult(u, PET_BONUS.get(), false));
			AolaExpert.finishAct(u, "喵喵的伙伴之约获得喵喵");
		}
		return r;
	}

  /**
 * @out "r" - int : 结果
 */
@Command(value = CMDHEAD + "_slow", name = "购买减速")
public void slow(String cmd, ActionscriptObject ao, User u, int fromRoom) {
  ActionscriptObject res = ServerHelper.createResponseObj(cmd);
  res.putInt("r", BuyService.instance.buy(u, SLOW_DOWN_SERVICE, 1).getResult());
  sendResponse(res, u);
}

/**
 * @in "pass" - boolean : 是否通过
 * @in "hp" - int : 本关结算时剩余的血量
 * @out "r" - int : 结果
 */
@Command(value = CMDHEAD + "_submit", name = "结算")
public void submit(String cmd, ActionscriptObject ao, User u, int fromRoom) {
  ActionscriptObject res = ServerHelper.createResponseObj(cmd);
  res.putInt("r", handleSubmit(u, ao.getBool("pass"), ao.getInt("hp"), res));
  sendResponse(res, u);
}

private int handleSubmit(User u, boolean pass, int hp, ActionscriptObject res) {
  if(hp < 0 || hp > HP.get(u.getUserId())) {
    return -1;
  }
  if(pass && PROG.get(u.getUserId()) < PROG_GAIN_PET) {
    PROG.incr(u.getUserId(), 1);
  }
  HP.set(u.getUserId(), hp);
  return MaterialResult.SUCCESS;
}

/**
 * @out "r" - int : 结果
 * @out "bn" - array :
 */
@Command(value = CMDHEAD + "_gainPet", name = "领取亚比")
public void gainPet(String cmd, ActionscriptObject ao, User u, int fromRoom) {
  ActionscriptObject res = ServerHelper.createResponseObj(cmd);
  res.putInt("r", handleGainPet(u, res));
  sendResponse(res, u);
}

private int handleGainPet(User u, ActionscriptObject res) {
  if(PROG.get(u.getUserId()) != PROG_GAIN_PET) {
    return -1;
  }
  res.putASObjectList("bn", BonusManager.instance.gainBonusWithAoResult(u, PET_BONUS.get(), false));
  PROG.incr(u.getUserId(), 1);
  AolaExpert.finishAct(u, "喵喵的伙伴之约获得喵喵");
  return MaterialResult.SUCCESS;
}


}
