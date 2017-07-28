1. 武器, 护盾, 护靴, 护符
cardList = PetCardService.instance.getBatchUserPetCards(userId,
					StringUtil.intArrayToString(EQUIP_ID_CONFIG[i]));
equipList = PetEquipService.getInstance().getBatchUserPetExistCardsByEquipTypes(userId, EQUIP_ID_CONFIG[i]);
