/**
 * 得到指定localTime的long值
 */
private static long getTimeMillisecond(LocalTime localTime) {
    LocalDateTime ldt = LocalDateTime.of(LocalDate.now(), localTime);
    return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
}
