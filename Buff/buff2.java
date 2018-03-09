1. 全属性:
攻击
防御
特攻
特防
速度
命中
闪避
暴击

2. 常用的:
190 -> AbilityUpdate 全属性
220 -> HealUp[加血]
240 -> EachRoundAppendBuff
伤害触发一次buf
122 -> BUFF_DYNAMIC_ABILITY 全数值

3. 子Buff:
<buff id='240' skillId='12' x='6' y='1' buffReset='false' relay='true'><buffs><buff id='199' x='2,2,2,2,2,2,2,2'/></buffs></buff>

4. 我方亚比回血量减少50%
<buff id='回血效果变化' y='50' z='true' buffReset='false' />


5. buff:

伤害增加百分比
受伤降低百分比伤害
固定伤害
HpDecrDamageIncr
HealUp[加血]
AbilityUpdate [全属性]
BUFF_DYNAMIC_ABILITY [全数值]
BuffOtk [BOSS绝杀]
EachRoundAppendSpellEx [出场全属性等级]
InitHpModify [出场血量设置]
EachRoundAppendBuff [没回合加的血量]
