第一种:
　　Map map = new HashMap();
　　Iterator iter = map.entrySet().iterator();
　　while (iter.hasNext()) {
  　　Map.Entry entry = (Map.Entry) iter.next();
  　　Object key = entry.getKey();
  　　Object val = entry.getValue();
　　}
　　效率高,以后一定要使用此种方式！
第二种:
　　Map map = new HashMap();
　　Iterator iter = map.keySet().iterator();
　　while (iter.hasNext()) {
  　　Object key = iter.next();
  　　Object val = map.get(key);
　　}
　　效率低,以后尽量少使用！


for (Entry<PM, PMMaster> entry : hm.entrySet()) {
	PM targetPM = entry.getKey();
	targetPM.changeCurrentHP(-targetPM.getCurrentHP()/2);
	targetPM.setLegendSoul(0);
	targetPM.changePP_AllUsedSkill(0);
}
