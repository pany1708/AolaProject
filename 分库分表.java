1.  IceFireFightDao：

对应的分库分表的sql:
CREATE TABLE `IceFireFight171027{0}` (
  `UserId` INT(10) UNSIGNED NOT NULL,
  `FrUserId` INT(10) UNSIGNED NOT NULL,
   PRIMARY KEY (`UserId`, `FrUserId`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

2. 分库分表的执行工具: tools ——> partsql_executor

3. STDao的例子更有意义:
private static final String SELECT_USER_DATA = "SELECT UserId,MapInfo FROM Act161021SeekTreasure%s WHERE UserId=?;";
private static final String UPDATE_USER_DATA = "REPLACE INTO Act161021SeekTreasure%s(UserId,MapInfo) VALUES (?,?);";

public static STUserData getUserData(User u){
    TableMapOutput tmo = TableMapper.quickMap(u.getUserId(), SELECT_USER_DATA);
    ArrayList<STUserData> dataList = tmo.executeQuery_ObjectList(new Object[]{u.getUserId()}, STUserData.resultBuilder);
    if (dataList.size() > 0){
        return dataList.get(0);
    }
    return new STUserData(u);
}

public static void updateUserData(STUserData data){
    TableMapOutput tmo = TableMapper.quickMap(data.getUserId(), UPDATE_USER_DATA);
    tmo.executeCommand(data.toSqlParams());
}

public static final ResultObjectBuilder<STUserData> resultBuilder = new ResultObjectBuilder<STUserData>() {
    @Override
    public STUserData build(ResultSet rs) throws SQLException {
        return new STUserData(rs.getInt("UserId"), rs.getString("MapInfo"));
    }
};

ResultObjectBuilder决定了tmo.executeQuery_ObjectList返回的list的数据类型.

4. 
