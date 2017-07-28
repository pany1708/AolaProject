static {
		BattleHandlerCollector.register(CMDHEAD, new Fight170714EG());
	}

	private static class Fight_BOSS_HMZS170519 extends AbstractBattleHandler {

		@Override
		protected BattleAttr setBattleAttr(ActionscriptObject ao, User u, JoinBattleAttribute jba) {
			int inM = ao.getInt("m");
			if (inM < 0 || inM > MAX_LEVEL) {
				return null;
			}
			int dbBP = DB_BIG_PROG.get(u.getUserId());
			if (dbBP == MAX_BIG_PROG) {
				return null;
			}
			BattleAttr ba = new BattleAttr();
			PM pm = PetHelper.customNewPM(PET_RACE_ID, 1, Math.max(1, inM));
			jba.customSinglePMForBattle(pm);
			ba.newPMMWithPMArray(new int[][] { { BOSS_RACEID_LEVEL[dbBP][0], BOSS_RACEID_LEVEL[dbBP][1] } });
			ba.addParam(inM);
			return ba;
		}

		@Override
		protected void onBattleEnd(User u, BattleResult br) {
			if (br.isWin() || br.isDraw()) {
				DB_BIG_PROG.incr(u.getUserId(), 1); // 进入下一大关
				DB_GAIN_AMOUNT.set(u.getUserId(), br.getInt(0)); // 更新等级
			}
		}

	}
