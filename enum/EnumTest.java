public enum EnumTest {
	MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6) {
		@Override
		public boolean isRest() {
			return true;
		}
	};

	private int value;

	private EnumTest(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public boolean isRest() {
		return false;
	}
}


EnumTest.SAT.isRest()  true
EnumTest.FRI.isRest()  false


该类继承了java.lang.Enum<E>
