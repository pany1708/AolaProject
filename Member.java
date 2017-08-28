1. 是否年度会员
private boolean isOYM(User u) {
  UserAnnualVipData avd = AnnualVipManager.ins.getAnnualVipData(u);
  if (avd == null) {
    return false;
  }
  return avd.isOYM();
}


2.
