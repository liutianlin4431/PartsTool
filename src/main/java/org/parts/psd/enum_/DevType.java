package org.parts.psd.enum_;

/**
 * 设备类型
 *
 * @author ltl
 */
public enum DevType {
    /**
     *
     */
    P(1000000),

    /**
     * 两卷变
     */
    TWOWDTRANS(10),
    /**
     * 三卷变
     */
    THREEWDTRANS(12),
    /**
     * 发电机
     */
    GENERATOR(14),
    /**
     * 母线
     */
    BUS(15),
    /**
     * 交流线
     */
    ACLINE(16),
    /**
     * 负荷
     */
    LOAD(18),
    /**
     * 电机负荷
     */
    MOTORLOAD(116),
    /**
     * 断路器
     */
    BREAKER(19),
    /**
     * 隔断开关
     */
    SWITCH(20),
    /**
     * 接地刀闸
     */
    GROUNDSWITCH(28),
    /**
     * 直流负荷
     */
    DCLOAD(30),
    /**
     * 连接线
     */
    NORMALLINK(31),
    /**
     * 熔断开关
     */
    FUSEBREAKER(32),
    /**
     * 蓄电池
     */
    BATTERY(33),
    /**
     * 逆变器
     */
    INVERTER(34),
    /**
     * 充电器
     */
    CHARGER(35),
    /**
     * 外部电网
     */
    OUTERGRID(36),
    /**
     * 工程
     */
    PROJECT(81),
    /**
     * 绘图
     */
    GRAPH(82),
    /**
     * 作业
     */
    JOB(88),
    /**
     * 工作组
     */
    USER(97),
    /**
     * 工作组
     */
    WORKGROUP(98),
    /**
     * 拓扑表
     */
    NODE(99),
    /**
     * 外部电网接入母线
     */
    INTERBUS(107),
    /**
     * 煤资源
     */
    COALRESOURCES(201),
    /**
     * 转换发电机
     */
    CONVERSIONGENERATOR(305),
    /**
     * 电节点
     */
    ELECTRICNODE(601);

    private Integer num;

    DevType(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

}
