1.  cd
// cd时间结束,弹出消息盒子
private static final MessageBoxInformer INFORMER = MessageBox.registerBonusInformer(MessageBoxType.BigMouthTalentMachine, u -> {
  int userId = u.getUserId();
  int dbHatch = DB_HATCH.get(userId);
  int cd = CD_HATCH.get(userId);
  if (dbHatch > 0) {
    return cd;
  }
    return MessageBox.STATUS_BONUS_NONE;
});


2. 注意消息盒子的方法和参数都是static的.
