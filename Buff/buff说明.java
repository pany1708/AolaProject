1. (环境)技能道具 ——> BUFF环境(BUFFs)——>环境(回合末)——>环境(回合末);

2. Buff属性:
  1) BUFF基本属性
  2) BUFF特有属性
  3) BUFF处理器的参数属性

3. How to
  1) 技能产生Buff
  2) Socket入场附带的Buff

4. 核心架包位置:pbserver——>pmbattle.server——>status包.

5. 宠物上下场都会清除一次身上的buff，加上这个buffReset=false是为了防止socket带过来的buff被清除.

6. 值得研究的buff

清空BUFF

回合末转移PP

DelayBuff [TODO]
