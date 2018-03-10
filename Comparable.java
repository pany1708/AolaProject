1. Comparable:
public class GCHCard implements Comparable<GCHCard> {  // 天赋纸牌屋
	public final int color;//花色，从0-3
	public final int index;//点数，从0-5表示9、10、J、Q、K、A

	public GCHCard(int color, int index){
		this.color = color;
		this.index = index;
	}

	public GCHCard(String infoStr){
		int[] arr = StringUtil.stringToIntArray(infoStr, GCHConfig.COMMA);
		this.color = arr[0];
		this.index = arr[1];
	}

	@Override
	public String toString() {
		return String.format("%d%s%d", color, GCHConfig.COMMA, index);
	}

	@Override
	public int compareTo(GCHCard o) { // 默认asc，升序排序
		return index - o.index;
	}
}


2. [文章](https://www.cnblogs.com/xujian2014/p/5215082.html)

3. Comparator是比较接口: 外部比较器
   Comparable是排序接口: 内部比较器

4. Comparable: 实现了这个接口的类:
  compareTo(Object o);

  这个方法需要验证null,抛出 NullPointerException;
