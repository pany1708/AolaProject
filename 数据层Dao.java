1.
import com.altratek.altraserver.extensions.db.TableMapper;

public static int getCount(int userId) {
return TableMapper.quickMap(userId, SELECT_COUNT).executeScalarInt(new Object[] { userId });
}

2. 新的写法在:Act170120PetCallCircle
