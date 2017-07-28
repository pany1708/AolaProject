private static final HashMap<Integer, Integer> ID_PRICE_MAP = new HashMap<>(); //***

private static final int[][] PRICE_ARR = new int[][]{
		new int[]{14, 14, 29, 13, 14, 29, 159},
		new int[]{15, 15, 15, 18, 22, 16, 14, 99, 25, 26},
		new int[]{19, 20, 20, 40, 20, 45, 20, 39},
		new int[]{18, 17, 16, 17, 22, 18, 18, 25, 29, 100}
	};

  static {
		for (int i = 0; i < CLOTH_IDS.length; i ++){
			for (int j = 0; j < CLOTH_IDS[i].length; j ++){
				ID_PRICE_MAP.put(CLOTH_IDS[i][j], PRICE_ARR[i][j]);
			}
		}
	}

  private static final String CLOTH_FORMAT = "0:%d:1";


  private boolean isLegal(User u, Integer[] ids){
  		if (ids.length > DISCOUNTS.length){
  			return false;
  		}
  		List<Integer> idList = Arrays.asList(ids); //***
  		ArrayList<Integer> existIdList = ClothesManager.instance.getExistedClothId(u.getUserId(), idList);
  		if (existIdList.size() > 0){
  			return false;
  		}
  		HashSet<Integer> set = new HashSet<>(); //***
  		set.addAll(idList);
  		return set.size() == ids.length && !Stream.of(ids).anyMatch(id -> !ID_PRICE_MAP.containsKey(id)); //***
  	}
