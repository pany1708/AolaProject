private static final ActTopCache ACT_TOP = new ActTopCache(CMDHEAD, 3 * 1000L, 0, 5);

static {
    ACT_TOP.setTransformer(info -> {
        String[] infoStrArr = info.getAttachment().split(GCHConfig.SHARP);
        ActionscriptObject ao = new ActionscriptObject();
        ao.putString("dd", infoStrArr[0]);
        ao.putString("nn", infoStrArr[1]);
        ao.putInt("bi", Integer.valueOf(infoStrArr[2]));
        return ao;
    });
}

res.putASObjectList("top", ACT_TOP.getTopAoCache());
