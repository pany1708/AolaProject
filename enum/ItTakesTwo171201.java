package com.altratek.altraserver.extensions.holiday.act2017.act20171201;

import java.util.ArrayList;
import java.util.function.Consumer;

import com.altratek.altraserver.domain.User;
import com.altratek.altraserver.extensions.Command;
import com.altratek.altraserver.extensions.annotation.BeginDate;
import com.altratek.altraserver.extensions.bonus.BonusManager;
import com.altratek.altraserver.extensions.holiday.HolidayResponder;
import com.altratek.altraserver.extensions.holiday.data.Data;
import com.altratek.altraserver.extensions.holiday.data.DbData;
import com.altratek.altraserver.extensions.holiday.data.McData;
import com.altratek.altraserver.extensions.holiday.former.top.ActTopCache;
import com.altratek.altraserver.extensions.holiday.former.top.IActTopTransformer;
import com.altratek.altraserver.extensions.log.Name;
import com.altratek.altraserver.extensions.material.BuyService;
import com.altratek.altraserver.extensions.material.data.Material;
import com.altratek.altraserver.extensions.material.data.MaterialParam;
import com.altratek.altraserver.extensions.material.data.MaterialResult;
import com.altratek.altraserver.extensions.pet.PetHelper;
import com.altratek.altraserver.extensions.pm.data.NewPMParam;
import com.altratek.altraserver.extensions.pm.domain.PM;
import com.altratek.altraserver.extensions.util.BitUtil;
import com.altratek.altraserver.extensions.util.Logger;
import com.altratek.altraserver.extensions.util.RandomUtil;
import com.altratek.altraserver.extensions.util.ServerHelper;
import com.altratek.altraserver.lib.ActionscriptObject;
import com.altratek.altraserver.logger.ServerLogger;
import com.aobi.common.utils.StringUtils;
import com.aola.app.dto.activitytop.ActTopInfo;

/**
 * @author weijuntao
 */

@Name("双十一对对碰专场161111")
@BeginDate("2020-01-01")
public class ItTakesTwo171201 extends HolidayResponder {

	private static final String CMDHEAD = "ITT-161111";
	private static final String ACTIVITY = "双十一对对碰专场161111";

	private static final Data MC_OPEN_BOX = McData.newInstance(ACTIVITY, "开启魔盒次数");
	private static final Data DB_FLAG_PET3 = DbData.newDataInstance(ACTIVITY, "3折神宠购买情况（按位）");
	private static final Data DB_FLAG_PET5 = DbData.newDataInstance(ACTIVITY, "5折神宠购买情况（按位）");
	private static final Data DB_ITEM_FLAG = DbData.newDataInstance(ACTIVITY, "道具购买情况（按位）");
	private static final Data DB_GIFT_PROGRESS_FLAG = DbData.newDataInstance(ACTIVITY, "赠品领取进度");
	private static final Data DB_EXP_FLAG1 = DbData.newDataInstance(ACTIVITY, "1星币经验晶豆购买情况（按位）");
	private static final Data DB_EXP_FLAG11 = DbData.newDataInstance(ACTIVITY, "11星币经验晶豆购买情况（按位）");

	private static final Data[] DB_FLAG_PET = { DB_FLAG_PET3, DB_FLAG_PET5 };

	private static final ActTopCache TOP1 = new ActTopCache(CMDHEAD + "_1", 3 * 1000L, 0, 5);
	private static final ActTopCache TOP2 = new ActTopCache(CMDHEAD + "_2", 3 * 1000L, 0, 5);
	private static final ActTopCache TOP3 = new ActTopCache(CMDHEAD + "_3", 3 * 1000L, 0, 5);
	private static final ActTopCache TOP4 = new ActTopCache(CMDHEAD + "_4", 3 * 1000L, 0, 5);
	private static final ActTopCache[] TOPS = { TOP1, TOP2, TOP3, TOP4 };

	private enum PerfectOption {
		完美学习力(PerfectOption::perfectEffort),
		满级(PerfectOption::perfectFullLevel),
		王者无敌(PerfectOption::perfectWZWD),
        // 天下无双(newPMParam -> {
        // 	newPMParam.initIndBAType = 3;
        //     newPMParam.strIndBA = NewPMParam.getPerfectWZWDIndBa();
        // })
		;

		final Consumer<NewPMParam> perfectParamFunction;

		private PerfectOption(Consumer<NewPMParam> f) {
			this.perfectParamFunction = f;
		}

		private static void perfectWZWD(NewPMParam newPMParam) {
			newPMParam.initIndBAType = 3;
			newPMParam.strIndBA = NewPMParam.getPerfectWZWDIndBa();
		}

		private static void perfectFullLevel(NewPMParam newPMParam) {
			newPMParam.level = 100;
		}

		private static void perfectEffort(NewPMParam newPMParam) {
			newPMParam.initIndBAType = 3;
			if (newPMParam.strIndBA.isEmpty()) {
				newPMParam.strIndBA = "35#35#35#35#35#35";
			}
			newPMParam.setEffBaseAbility(NewPMParam.getPerfectEffBa(newPMParam.raceId));
		}
	}

	private static final int[] BOX_PET_RACE_ID = {
		2378, 2379, 2380, 2381, 2429, 2649, 2406, 2415, 2542, 2533, 3139, 2904, 2874,
		2982, 2916, 2950, 2955, 2837, 2927, 2879, 3117, 2860, 2846, 2892, 3023, 3070
	};
	private static final int[] RATES_OPEN_BOX = {
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 2, 2
	};

	private static final PerfectOption[][] PERFECT_OPTIONS = {
		{ PerfectOption.完美学习力 },
		{ PerfectOption.满级 },
		{ PerfectOption.王者无敌 },
		{ PerfectOption.完美学习力, PerfectOption.满级 },
		{ PerfectOption.完美学习力, PerfectOption.王者无敌 },
		{ PerfectOption.满级, PerfectOption.王者无敌 },
		{ PerfectOption.完美学习力, PerfectOption.满级, PerfectOption.王者无敌 }
	};

	private static final int[] RATES_PERFECT = { 75, 10, 7, 3, 2, 2, 1 };

	private static final int[][] PET_RACES = {
		{ 3065, 3030, 2960, 3052, 2814, 3016, 2964, 3068, 3058 },
		{ 3115, 3114, 3144, 3028, 3104, 3079, 3064, 3029, 3110 }
	};
	private static final int[][] COST_PET = {
		{ 179, 179, 149, 59, 179, 158, 158, 179, 149 },
		{ 500, 500, 249, 500, 249, 500, 500, 500, 249 }
	};

	private static final String[] BONUS_ITEM = {
		"2:177:1",
		"2:1865:1",
		"2:1660:1",
		"8:433-1&437-1&525-1&521-1&963-1&939-1:6:1",
		"2:2484:1",
		"2:2321:1;2:2320:3",
		"2:1240:28;2:1243:7",
		"14:71:4;14:72:4"
	};
	private static final int[] COST_PRICE_ITEM = { 8, 20, 30, 298, 399, 198, 148, 148 };

	private static final String[] BONUS_ITEM_GIFT = {
		"2:2631:1",
		"2:1437:1",
		"2:2315:1",
		"8:433-1&437-1&525-1&521-1&963-1&939-1:6:1"
	};

	private static final String[] BONUS_EXP1 = {
		"101:0:30000",
		"98:0:200",
		"101:0:30000;98:0:200"
	};
	private static final String[] BONUS_EXP11 = {
		"101:0:330000",
		"98:0:2000",
		"101:0:330000;98:0:2000"
	};

	static {
		IActTopTransformer transformer = info -> {
			String[] strs = StringUtils.split(info.getAttachment(), "|");
			ActionscriptObject ao = new ActionscriptObject();
			ao.putString("dd", strs[0]);
			ao.putString("nn", strs[1]);
			ao.putString("pn", strs[2]);
			return ao;
		};
		for (ActTopCache topCache : TOPS) {
			topCache.setTransformer(transformer);
		}
	}

	private static void submitTop(ActTopCache top, User u, String petName) {
		String info = String.format("%s|%s|%s", u.getName(), ServerHelper.getUserNickName(u), petName);
		top.submit(u.getUserId(), info);
	}

	private static final int MAX_DAILY_OPEN_BOX = 3;

	private static final int SERVICE_PET = 7042;
	private static final int SERVICE_ITEM = 7043;
	private static final int SERVICE_EXP = 7044;
	private static final int SERVICE_OPEN_BOX = 7080;

	/**
	 * 神宠：
	 *
	 * @out "lot" - int : 剩余开启魔盒次数
	 * @out "f11" - int : 3折神宠购买情况（按位）
	 * @out "f12" - int : 5折神宠购买情况（按位）
	 * @out "top1" - Array<ASObj> : 幸运走字（完美学习力、满级、王者无敌）{ "dd" - String : 多多号
	 *      "nn" - String : 昵称 "pn" - String : 亚比名 }
	 * @out "top2" - Array<ASObj> : 幸运走字（完美学习力、满级）{ "dd" - String : 多多号 "nn" -
	 *      String : 昵称 "pn" - String : 亚比名 }
	 * @out "top3" - Array<ASObj> : 幸运走字（满级、王者无敌）{ "dd" - String : 多多号 "nn" -
	 *      String : 昵称 "pn" - String : 亚比名 }
	 * @out "top4" - Array<ASObj> : 幸运走字（完美学习力、王者无敌）{ "dd" - String : 多多号 "nn" -
	 *      String : 昵称 "pn" - String : 亚比名 }
	 *
	 *      道具：
	 * @out "f2" - int : 道具购买情况（按位）
	 * @out "p2" - int : 赠品领取进度
	 *
	 *      经验晶豆：
	 * @out "f31" - int : 1星币购买情况（按位）
	 * @out "f32" - int : 11星币购买情况（按位）
	 */
	@Command(value = CMDHEAD + "_p", name = "面板信息")
	public void panel(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		int userId = u.getUserId();
		res.putInt("lot", getLeftOpenBoxTimes(userId));
		int[] dbArr = DbData.getMultiDataArray(userId, DB_FLAG_PET3, DB_FLAG_PET5, DB_ITEM_FLAG, DB_GIFT_PROGRESS_FLAG,
				DB_EXP_FLAG1, DB_EXP_FLAG11);
		res.putInt("f11", dbArr[0]);
		res.putInt("f12", dbArr[1]);
		res.putInt("f2", dbArr[2]);
		res.putInt("p2", dbArr[3]);
		res.putInt("f31", dbArr[4]);
		res.putInt("f32", dbArr[5]);
		for (int i = 0; i < TOPS.length; i++) {
			res.putASObjectList(String.format("top%d", i + 1), TOPS[i].getTopAoCache());
		}
		sendResponse(res, u);
	}

	private int getLeftOpenBoxTimes(int userId) {
		return MAX_DAILY_OPEN_BOX - MC_OPEN_BOX.get(userId);
	}

	/**
	 * @out "r" - int : 1,成功
	 * @out "bn" - Array<ASObj> : 奖励
	 * @out "pf" - int : 完美情况（按位：完美学习力、满级、王者无敌）
	 */
	@Command(value = CMDHEAD + "_ob", name = "开启魔盒")
	public void openBox(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		int userId = u.getUserId();
		if (getLeftOpenBoxTimes(userId) <= 0) {
			return;
		}
		int result = BuyService.instance
				.buy(u, MaterialParam.createMaterialParam(SERVICE_OPEN_BOX, Material.TYPE_SERVICE, 1)).getResult();
		if (result == MaterialResult.SUCCESS) {
			int index = RandomUtil.randomIndex(RATES_OPEN_BOX);
			PerfectResult pr = randomPerfect(u, BOX_PET_RACE_ID[index]);
			result = pr.result;
			if (result == MaterialResult.SUCCESS) {
				res.putASObjectList("bn", pr.bnList);
				res.putInt("pf", pr.perfectFlag);
				MC_OPEN_BOX.incr(userId, 1);
			} else {
				Logger.error(
						String.format("userId=%d,在双十一对对碰专场161111中购买神宠%d成功，但赠送亚比失败！", userId, BOX_PET_RACE_ID[index]));
			}
		}
		res.putInt("r", result);
		sendResponse(res, u);
	}

	private PerfectResult randomPerfect(User u, int raceId) {
		int index = RandomUtil.randomIndex(RATES_PERFECT);
		NewPMParam newPMParam = new NewPMParam(raceId, 1, 2);
		int perfectFlag = 0;
		for (PerfectOption perfectOption : PERFECT_OPTIONS[index]) {
			perfectOption.perfectParamFunction.accept(newPMParam);
			perfectFlag = BitUtil.set(perfectFlag, perfectOption.ordinal());
		}
		PM pm = PetHelper.custom2NewPMWithDynamicRaceId_ExPM(u, newPMParam);
		if (pm != null) {
			ArrayList<ActionscriptObject> bnList = new ArrayList<>();
			ActionscriptObject ao = new ActionscriptObject();
			ao.putInt("type", 114);
			ao.putInt("id", newPMParam.raceId);
			ao.putInt("num", newPMParam.level);
			bnList.add(ao);
			String petName = pm.getName();
			switch (perfectFlag) {
			case 7:
				submitTop(TOPS[0], u, petName);
				break;
			case 3:
				submitTop(TOPS[1], u, petName);
				break;
			case 6:
				submitTop(TOPS[2], u, petName);
				break;
			case 5:
				submitTop(TOPS[3], u, petName);
				break;
			default:
				break;
			}
			return new PerfectResult(1, bnList, perfectFlag);
		} else {
			return new PerfectResult(-1);
		}
	}

	/**
	 * @in "t" - int : 类型：0：3折，1：5折
	 * @in "i" - int : 序号 >= 0
	 * @out "r" - int : 1,成功
	 * @out "bn" - Array<ASObj> : 奖励
	 * @out "pf" - int : 完美情况（按位：完美学习力、满级、王者无敌）
	 */
	@Command(value = CMDHEAD + "_bp", name = "购买神宠")
	public void buyGodPet(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		int userId = u.getUserId();
		int type = ao.getInt("t");
		int index = ao.getInt("i");
		if (type < 0 || type > 1) {
			return;
		}
		if (index < 0 || index >= PET_RACES[type].length) {
			return;
		}
		int flag = DB_FLAG_PET[type].get(userId);
		if (BitUtil.isFlagSet(flag, index)) {
			res.putInt("r", -1);
			sendResponse(res, u);
			return;
		}
		int result = BuyService.instance
				.buy(u, MaterialParam.createMaterialParam(SERVICE_PET, Material.TYPE_SERVICE, COST_PET[type][index]))
				.getResult();
		if (result == MaterialResult.SUCCESS) {
			PerfectResult pr = randomPerfect(u, PET_RACES[type][index]);
			result = pr.result;
			if (result == 1) {
				res.putASObjectList("bn", pr.bnList);
				res.putInt("pf", pr.perfectFlag);
				DB_FLAG_PET[type].set(userId, BitUtil.set(flag, index));
			} else {
				ServerLogger.error(
						String.format("userId=%d,在双十一对对碰专场161111中购买神宠%d成功，但赠送亚比失败！", userId, PET_RACES[type][index]));
			}
		}
		res.putInt("r", result);
		sendResponse(res, u);
	}

	/**
	 * @in "i" - int : 道具序号>=0
	 * @out "r" - int : 1,成功
	 * @out "bn" - Array<ASObj> : 奖励
	 */
	@Command(value = CMDHEAD + "_bi", name = "购买道具")
	public void buyItem(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		int userId = u.getUserId();
		int index = ao.getInt("i");
		if (index < 0 || index > BONUS_ITEM.length) {
			return;
		}
		int flag = DB_ITEM_FLAG.get(userId);
		if (BitUtil.isFlagSet(flag, index)) {
			return;
		}
		int result = BuyService.instance
				.buy(u, MaterialParam.createMaterialParam(SERVICE_ITEM, Material.TYPE_SERVICE, COST_PRICE_ITEM[index]))
				.getResult();
		if (result == MaterialResult.SUCCESS) {
			res.putASObjectList("bn", BonusManager.addBonusWithoutValidation(u, BONUS_ITEM[index], 1));
			DB_ITEM_FLAG.set(userId, BitUtil.set(flag, index));
		}
		res.putInt("r", result);
		sendResponse(res, u);
	}

	/**
	 * @in "i" - int : 赠品序号0-3
	 * @out "r" - int : 1,成功
	 * @out "bn" - Array<ASObj> : 奖励
	 */
	@Command(value = CMDHEAD + "_gig", name = "领取道具赠品")
	public void getItemGift(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		int userId = u.getUserId();
		int index = ao.getInt("i");
		if (index < 0 || index > 3) {
			return;
		}
		int[] dbArr = DbData.getMultiDataArray(userId, DB_ITEM_FLAG, DB_GIFT_PROGRESS_FLAG);
		if (BitUtil.isFlagSet(dbArr[1], index)) {
			return;
		}
		if (Integer.bitCount(dbArr[1]) >= Integer.bitCount(dbArr[0])) {
			return;
		}
		DB_GIFT_PROGRESS_FLAG.set(userId, BitUtil.set(dbArr[1], index));
		res.putInt("r", 1);
		res.putASObjectList("bn", BonusManager.addBonusWithoutValidation(u, BONUS_ITEM_GIFT[index], 1));
		sendResponse(res, u);
	}

	/**
	 * @in "i" - int : 0-2
	 * @out "r" - int : 购买结果返回,MaterialResult
	 * @out "bn" - Array<ASObj> : 奖励
	 */
	@Command(value = CMDHEAD + "_b1", name = "1星币购")
	public void buy1(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		expBuy(ao, u, res, DB_EXP_FLAG1, 1, BONUS_EXP1);
		sendResponse(res, u);
	}

	private void expBuy(ActionscriptObject ao, User u, ActionscriptObject res, Data flagStorer, int xbCount,
			String[] bonus) {
		int userId = u.getUserId();
		int index = ao.getInt("i");
		if (index < 0 || index > 2) {
			return;
		}
		int flag = flagStorer.get(userId);
		if (BitUtil.isFlagSet(flag, index)) {
			return;
		}
		if (index == 2 && flag != 3) {
			return;
		}
		int result = BuyService.instance
				.buy(u, MaterialParam.createMaterialParam(SERVICE_EXP, Material.TYPE_SERVICE, xbCount)).getResult();
		if (result == MaterialResult.SUCCESS) {
			res.putASObjectList("bn", BonusManager.addBonusWithoutValidation(u, bonus[index], 1));
			flagStorer.set(userId, BitUtil.set(flag, index));
		}
		res.putInt("r", result);
	}

	/**
	 * @in "i" - int : 0-2
	 * @out "r" - int : 购买结果返回,MaterialResult
	 * @out "bn" - Array<ASObj> : 奖励
	 */
	@Command(value = CMDHEAD + "_b11", name = "11星币购")
	public void buy11(String cmd, ActionscriptObject ao, User u, int fromRoom) {
		ActionscriptObject res = ServerHelper.createResponseObj(cmd);
		expBuy(ao, u, res, DB_EXP_FLAG11, 11, BONUS_EXP11);
		sendResponse(res, u);
	}

	private static class PerfectResult {
		int result;
		ArrayList<ActionscriptObject> bnList;
		int perfectFlag;

		public PerfectResult(int result, ArrayList<ActionscriptObject> bnList, int perfectFlag) {
			super();
			this.result = result;
			this.bnList = bnList;
			this.perfectFlag = perfectFlag;
		}

		public PerfectResult(int result) {
			this(result, null, 0);
		}
	}
}
