1. 对应数据库Item
ItemManager.instance.getUserItemQuantity(u.getUserId(), ITEM_ID);

2. 前端可以知道Items的数量

3. 道具的消耗： ItemManager
int result = ItemManager.instance.validDecItem(userId, MaterialParam.createMaterialParam(ITEM_KEY_ID, Material.TYPE_ITEM, 1)).getResult();
if (result != MaterialResult.SUCCESS) {
	return result;
}
ItemManager.instance.decrItem(userId, MaterialParam.createMaterialParam(ITEM_KEY_ID, Material.TYPE_ITEM, 1));
